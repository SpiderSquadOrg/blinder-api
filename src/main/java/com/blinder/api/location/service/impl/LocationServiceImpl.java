package com.blinder.api.location.service.impl;

import com.blinder.api.exception.NotExistsException;
import com.blinder.api.location.model.Location;
import com.blinder.api.location.repository.LocationRepository;
import com.blinder.api.location.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Set;

import static com.blinder.api.util.MappingUtils.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    @Override
    public Mono<String> getAllCountries() {
        WebClient webClient = WebClient.create("https://api.example.com");

        String response = webClient.get()
                .uri("https://api.countrystatecity.in/v1/countries")
                .header("X-CSCAPI-KEY", "NWRsOExDZUU4ZVJid0N4RG5lUGFhcHEwS0pwWTFtMDdPTnZIamdIVQ==")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return Mono.just(response);
    }

    @Override
    public Mono<String> getStatesByCountry(String iso2) {
        WebClient webClient = WebClient.create("https://api.example.com");

        String response;

        try{
            response = webClient.get()
                    .uri("https://api.countrystatecity.in/v1/countries/" + iso2 + "/states")
                    .header("X-CSCAPI-KEY", "NWRsOExDZUU4ZVJid0N4RG5lUGFhcHEwS0pwWTFtMDdPTnZIamdIVQ==")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        }
        catch (Exception e){
            throw new NotExistsException("Entered country does not exist");
        }

        return Mono.justOrEmpty(response);
    }

    @Override
    public Mono<String> getStateById(String ciso, String siso) {
        WebClient webClient = WebClient.create("https://api.example.com");

        String response;

        try{
            response = webClient.get()
                    .uri("https://api.countrystatecity.in/v1/countries/" + ciso + "/states/" + siso)
                    .header("X-CSCAPI-KEY", "NWRsOExDZUU4ZVJid0N4RG5lUGFhcHEwS0pwWTFtMDdPTnZIamdIVQ==")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        }
        catch (Exception e){
            throw new NotExistsException("Entered state or country does not exist");
        }

        return Mono.justOrEmpty(response);
    }

    @Override
    public Mono<String> getCountryById(String ciso) {
        WebClient webClient = WebClient.create("https://api.example.com");

        String response;

        try{
            response = webClient.get()
                    .uri("https://api.countrystatecity.in/v1/countries/" + ciso )
                    .header("X-CSCAPI-KEY", "NWRsOExDZUU4ZVJid0N4RG5lUGFhcHEwS0pwWTFtMDdPTnZIamdIVQ==")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        }
        catch (Exception e){
            throw new NotExistsException("Entered country does not exist");
        }

        return Mono.justOrEmpty(response);
    }

    @Override
    public Location addLocation(Location location) {
        return this.locationRepository.save(location);
    }

    @Override
    public Location getLocationById(String locationId) {
        return this.locationRepository.findById(locationId).orElseThrow();
    }

    @Override
    public Location updateLocation(String locationId, Location location) {
        Location locationToUpdate = this.locationRepository.findById(locationId).orElseThrow();

        Set<String> nullPropertyNames = getNullPropertyNames(location);

        BeanUtils.copyProperties(location, locationToUpdate, nullPropertyNames.toArray(new String[0]));

        this.locationRepository.save(locationToUpdate);
        return locationToUpdate;
    }

    @Override
    public void deleteLocation(String locationId) {
        this.locationRepository.deleteById(locationId);
    }
}