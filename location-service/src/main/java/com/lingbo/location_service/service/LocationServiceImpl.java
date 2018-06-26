package com.lingbo.location_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lingbo.location_service.domain.Location;
import com.lingbo.location_service.domain.LocationRepository;
import com.lingbo.location_service.domain.VehicleMovementType;

@Service
public class LocationServiceImpl implements LocationService {
	
	private LocationRepository locationRepository;
	
	@Autowired
	public LocationServiceImpl(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}
	
	public List<Location> saveCarLocations(List<Location> carLocations) {
		return locationRepository.save(carLocations);
	}

	public void deleteAll() {
		locationRepository.deleteAll();
	}

	public Page<Location> findByVehicleMovementType(VehicleMovementType movementType, Pageable pageable) {
		return locationRepository.findByVehicleMovementType(movementType, pageable);
	}

	public Page<Location> findByVin(String vin, Pageable pageable) {
		return locationRepository.findByCarInfoVin(vin, pageable);
	}
	
}
