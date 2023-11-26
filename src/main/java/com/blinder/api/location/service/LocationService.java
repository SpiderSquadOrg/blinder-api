package com.blinder.api.location.service;

import com.blinder.api.location.model.Location;
import org.springframework.data.domain.Page;

public interface LocationService {
    Location addLocation(Location location);

    Location getLocationById(String locationId);

    Location updateLocation(String locationId, Location location);

    void deleteLocation(String locationId);
}
