package com.blinder.api.location.service.impl;

import com.blinder.api.location.model.Location;
import com.blinder.api.location.repository.LocationRepository;
import com.blinder.api.location.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.blinder.api.util.MappingUtils.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

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
