package com.blinder.api.user.service;

import com.blinder.api.user.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User getUserById(String id);

    User addUser(User user);
    User register(User user);

    void deleteUserById(String userId);

    User banUserById(String userId);

    User unbanUserById(String userId);

    User blockUserById(String userId);

    User unblockUserById(String userId);

    Optional<User> findByUsername(String username);

    Page<User> getUsers(Integer page, Integer size);

    List<User> getFilteredUsers(User user);

    List<User> getRandomUsers(int howManyUser);

    User updateUserById(String userId, User user);

    Page<User> searchUsers(Integer page, Integer size, String email, String name, String surname, String username, String[] roleNames, String[] genderNames, String ageLowerBound, String ageUpperBound, String region, String country, String city, Boolean isMatched, Boolean isBanned, String sortBy, String sortDirection);

    List<String> getUserImagesByChatInfo(String userId, String chatId, String token) throws JsonProcessingException;

    String getRemainingChatTime(String chatId, String token) throws JsonProcessingException;
}
