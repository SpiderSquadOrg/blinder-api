package com.blinder.api.location.service;

import com.blinder.api.location.model.Location;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;

public interface LocationService {

    Mono<String> getAllCountries();
    Mono<String> getStatesByCountry(String iso2);
    Location addLocation(Location location);

    Location getLocationById(String locationId);

    Location updateLocation(String locationId, Location location);

    void deleteLocation(String locationId);

}
