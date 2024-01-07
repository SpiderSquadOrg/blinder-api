package com.blinder.api.user.service.impl;

import com.blinder.api.characteristics.model.Characteristics;
import com.blinder.api.characteristics.repository.CharacteristicsRepository;
import com.blinder.api.common.Constants;
import com.blinder.api.common.sort.SortCriteria;
import com.blinder.api.common.sort.SortDirection;
import com.blinder.api.filter.model.Filter;
import com.blinder.api.filter.model.LocationType;
import com.blinder.api.filter.service.FilterService;
import com.blinder.api.location.model.Location;
import com.blinder.api.location.service.LocationService;
import com.blinder.api.user.model.Gender;
import com.blinder.api.user.model.Role;
import com.blinder.api.user.model.User;
import com.blinder.api.user.repository.UserCustomRepository;
import com.blinder.api.user.repository.UserRepository;
import com.blinder.api.user.rules.UserBusinessRules;
import com.blinder.api.user.security.auth.service.UserAuthService;
import com.blinder.api.user.service.RoleService;
import com.blinder.api.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.blinder.api.util.MappingUtils.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserCustomRepository userCustomRepository;
    private final LocationService locationService;
    private final UserAuthService userAuthService;
    private final UserBusinessRules userBusinessRules;
    private final FilterService filterService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final CharacteristicsRepository characteristicsRepository;

    @Override
    public User getUserById(String id) {
        this.userBusinessRules.checkIfUserExists(id);
        return this.userRepository.findById(id).orElseThrow();
    }

    @Override
    public User addUser(User user) {
        this.userBusinessRules.checkIfEmailExists(user.getEmail());
        this.userBusinessRules.checkIfUsernameExists(user.getUsername());
        this.userBusinessRules.checkIfGenderDoesNotExists(user.getGender().getId());
        this.userBusinessRules.checkIfRoleDoesNotExists(user.getRole().getId());
        this.userBusinessRules.checkIfPhoneNumberExists(user.getPhoneNumber());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Location newLocation = this.locationService.addLocation(user.getLocation());
        User newUser = this.userRepository.save(user);
        newLocation.setUser(newUser);
        this.locationService.updateLocation(newLocation.getId(),newLocation);

        Characteristics characteristics = new Characteristics();
        characteristics.setUser(newUser);
        newUser.setCharacteristics(characteristics);
        this.characteristicsRepository.save(characteristics);

        this.filterService.createDefaultFilterForUser(newUser.getId());

        return newUser;
    }

    @Override
    public User register(User user) {
        Role normalUserRole = this.roleService.getRoleByName("normal");
        user.setRole(normalUserRole);
        return this.addUser(user);
    }

    @Override
    public User updateUserById(String userId, User user) {
        this.userBusinessRules.checkIfUserExists(userId);
        this.userBusinessRules.checkIfEmailExists(user.getEmail());
        this.userBusinessRules.checkIfUsernameExists(user.getUsername());
        this.userBusinessRules.checkIfGenderDoesNotExists(user.getGender().getId());
        this.userBusinessRules.checkIfRoleDoesNotExists(user.getRole().getId());


        User userToUpdate = this.userRepository.findById(userId).orElseThrow();

        Set<String> nullPropertyNames = getNullPropertyNames(user);

        BeanUtils.copyProperties(user, userToUpdate, nullPropertyNames.toArray(new String[0]));

        this.userRepository.save(userToUpdate);
        return userToUpdate;
    }

    @Override
    public Page<User> searchUsers(Integer page, Integer size, String email, String name, String surname, String username, String[] roleNames, String[] genderNames, String ageLowerBound, String ageUpperBound, String region, String country, String city, Boolean isMatched, Boolean isBanned, String sortBy, String sortDirection) {
        if(Objects.isNull(page) || Objects.isNull(size)){
            page = 0;
            size = Integer.MAX_VALUE;
        }
        Pageable pageable = PageRequest.of(page, size);
        if (Objects.isNull(sortBy) || sortBy.isEmpty()) {
            sortBy = "createdDate";
        }

        SortDirection so;
        if (Objects.nonNull(sortDirection) && sortDirection.equals("ASCENDING")) {
            so = SortDirection.ASCENDING;
        } else {
            so = SortDirection.DESCENDING;
        }

        SortCriteria sortCriteria = new SortCriteria(sortBy, so);
        return this.userCustomRepository.searchUsersByFilter(email, name, surname, username, roleNames, genderNames, ageLowerBound, ageUpperBound, region, country, city, isMatched, isBanned, pageable, sortCriteria);
    }

    private JsonNode fetchChatMessages(String chatId, String token) throws JsonProcessingException {
        WebClient webClient = WebClient.create("https://api.example.com");

        String response = webClient.get()
                .uri(Constants.CHAT_API_URL+ "/messages/"+chatId )
                .header("Authorization",  token)
                .header("ChatAuthorization",  Constants.CHAT_TOKEN)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        Mono<String> messagesData = Mono.justOrEmpty(response);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = messagesData.block();
        return objectMapper.readTree(jsonString);
    }

    @Override
    public List<String> getUserImagesByChatInfo(String userId, String chatId, String token) throws JsonProcessingException {
        JsonNode jsonNode = fetchChatMessages(chatId, token);

        List<String> userChatIds = new ArrayList<>();
        String dateAsString = null;
        for(JsonNode node : jsonNode){
            JsonNode users = node.get("chat").get("users");
            for (JsonNode userNode : users) {
                userChatIds.add(userNode.asText());
            }
            break;
        }

        for(JsonNode node : jsonNode){
            String senderId = node.get("sender").get("_id").asText();
            userChatIds.remove(senderId);

            if(userChatIds.isEmpty()){
                dateAsString = node.get("createdAt").asText();
                break;
            }
        }

        //If userChatIds is empty, it means both users sent message each other.
        if(userChatIds.isEmpty() && Objects.nonNull(dateAsString)){
            //String date to milliseconds
            Instant instant = Instant.from(DateTimeFormatter.ISO_INSTANT.parse(dateAsString));
            long milliseconds = instant.toEpochMilli();

            Date now = new Date();
            long nowMilliseconds = now.getTime();

            long diff = nowMilliseconds - milliseconds;

            if(diff > Constants.SEVEN_DAYS){
                return this.userRepository.getImagesById(userId);
            }
        }
        return null;
    }

    @Override
    public String getRemainingChatTime(String chatId, String token) throws JsonProcessingException {
        JsonNode jsonNode = fetchChatMessages(chatId, token);

        List<String> userChatIds = new ArrayList<>();
        String dateAsString = null;
        for(JsonNode node : jsonNode){
            JsonNode users = node.get("chat").get("users");
            for (JsonNode userNode : users) {
                userChatIds.add(userNode.asText());
            }
            break;
        }

        for(JsonNode node : jsonNode){
            String senderId = node.get("sender").get("_id").asText();
            userChatIds.remove(senderId);

            if(userChatIds.isEmpty()){
                dateAsString = node.get("createdAt").asText();
                break;
            }
        }

        //If userChatIds is empty, it means both users sent message each other.
        if(userChatIds.isEmpty() && Objects.nonNull(dateAsString)){
            //String date to milliseconds
            Instant instant = Instant.from(DateTimeFormatter.ISO_INSTANT.parse(dateAsString));
            long milliseconds = instant.toEpochMilli();

            Date now = new Date();
            long nowMilliseconds = now.getTime();

            long diff = nowMilliseconds - milliseconds;

            if(diff > Constants.SEVEN_DAYS){
                return "00:00";
            }else{
                long remainingTime = Constants.SEVEN_DAYS - diff;
                long remainingTimeInSeconds = remainingTime / 1000;
                long remainingTimeInMinutes = remainingTimeInSeconds / 60;
                long remainingTimeInHours = remainingTimeInMinutes / 60;

                remainingTimeInMinutes = remainingTimeInMinutes % 60; // get the remaining minutes

                return String.format("%03d:%02d", remainingTimeInHours, remainingTimeInMinutes);
            }
        }
        return null;
    }

    @Override
    public void deleteUserById(String userId) {
        this.userBusinessRules.checkIfUserExists(userId);

        //User userToDelete = this.userRepository.findById(userId).orElseThrow();
        //userToDelete.setLocation(null);

        //this.userRepository.save(userToDelete);
        this.userRepository.deleteById(userId);
    }

    @Override
    public User banUserById(String userId) {
        this.userBusinessRules.checkIfUserExists(userId);
        this.userBusinessRules.checkIfUserIsBanned(userId);

        User user = this.userRepository.findById(userId).orElseThrow();
        user.setBanned(true);
        return this.userRepository.save(user);
    }

    @Override
    public User unbanUserById(String userId) {
        this.userBusinessRules.checkIfUserExists(userId);
        this.userBusinessRules.checkIfUserIsNotBanned(userId);

        User user = this.userRepository.findById(userId).orElseThrow();
        user.setBanned(false);
        return this.userRepository.save(user);
    }

    @Override
    public User blockUserById(String userId) {
        String activeUserId = this.userAuthService.getActiveUser().getId();

        this.userBusinessRules.checkIfUserExists(userId);
        this.userBusinessRules.checkIfUserIsBlocked(activeUserId,userId);

        User user = this.userRepository.findById(activeUserId).orElseThrow();
        User toBeBlockedUser = this.userRepository.findById(userId).orElseThrow();
        user.getBlockedUsers().add(toBeBlockedUser);

        return this.userRepository.save(user);
    }

    @Override
    public User unblockUserById(String userId) {
        String activeUserId = this.userAuthService.getActiveUser().getId();

        this.userBusinessRules.checkIfUserExists(userId);
        this.userBusinessRules.checkIfUserIsNotBlocked(activeUserId,userId);

        User user = this.userRepository.findById(activeUserId).orElseThrow();
        User toBeUnblockedUser = this.userRepository.findById(userId).orElseThrow();
        user.getBlockedUsers().remove(toBeUnblockedUser);

        return this.userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public Page<User> getUsers(Integer page, Integer size) {
        boolean isPageable = Objects.nonNull(page) && Objects.nonNull(size);

        if (!isPageable) {
            page = 0;
            size = Integer.MAX_VALUE;
        }

        return this.userRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public List<User> getFilteredUsers(User user) {
        Filter userFilter = user.getFilter();

        List<String> allowedGenders = userFilter.getGenders().stream().map(Gender::getName).toList();
        int ageLowerBound = userFilter.getAgeLowerBound();
        int ageUpperBound = userFilter.getAgeUpperBound();
        String countryIso2 = userFilter.getCountryIso2();
        String stateIso2 = userFilter.getStateIso2();

        //return userCustomRepository.findFilteredUsers(allowedGenders, ageLowerBound, ageUpperBound, countryIso2, stateIso2);
        //List<User> a = userRepository.findFilteredUsersWithQuery(calculateBirthYear(ageLowerBound), calculateBirthYear(ageUpperBound));
        return userCustomRepository.findFilteredUsers(allowedGenders, ageLowerBound, ageUpperBound, countryIso2, stateIso2);
        /*
        List<User> filteredUsers = userCustomRepository.findFilteredUsers(allowedGenders, ageLowerBound, ageUpperBound, countryIso2, stateIso2);

        // Sonuçları inceleme
        for (User _user : filteredUsers) {
            System.out.println("User ID: " + _user.getId() + ", Name: " + _user.getName() + ", Birth Date: " + _user.getBirthDate());
            // Diğer kullanıcı özellikleri üzerinde de aynı şekilde işlem yapabilirsiniz.
        }

        return userCustomRepository.findFilteredUsers(allowedGenders, ageLowerBound, ageUpperBound, countryIso2, stateIso2);
         */
    }

    @Override
    public List<User> getFilteredUsers(User user, int maxUsers, int numberOfUsers) {
        Filter userFilter = user.getFilter();

        List<String> allowedGenders = userFilter.getGenders().stream().map(Gender::getName).toList();
        int ageLowerBound = userFilter.getAgeLowerBound();
        int ageUpperBound = userFilter.getAgeUpperBound();
        String countryIso2 = userFilter.getCountryIso2();
        String stateIso2 = userFilter.getStateIso2();

        List<User> filteredUsers = userCustomRepository.findFilteredUsers(allowedGenders, ageLowerBound, ageUpperBound, countryIso2, stateIso2, maxUsers);

        Collections.shuffle(filteredUsers);

        int numberOfUsersToReturn = Math.min(numberOfUsers, filteredUsers.size());
        return filteredUsers.subList(0, numberOfUsersToReturn);
    }

    private int calculateBirthYear(int age) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -age);
        return cal.get(Calendar.YEAR);
    }

}
