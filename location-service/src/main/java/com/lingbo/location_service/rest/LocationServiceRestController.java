package com.lingbo.location_service.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lingbo.location_service.model.Location;
import com.lingbo.location_service.model.VehicleMovementType;
import com.lingbo.location_service.service.LocationService;


@RestController
public class LocationServiceRestController {
	
	private LocationService locationService;
	
	@Autowired
	public LocationServiceRestController(LocationService locationService) {
		this.locationService = locationService;
	}
	
	@RequestMapping(value = "/init", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void upload(@RequestBody List<Location> locations) {
		locationService.saveCarLocations(locations);
	}
	
	@RequestMapping(value = "/purge", method = RequestMethod.POST)
	public void purge() {
		locationService.deleteAll();
	}
	
	@RequestMapping(value = "/find/movementType/{movementType}", method = RequestMethod.GET)
	public Page<Location> findByMovementType(
			@PathVariable String movementType, 
			@RequestParam(name = "page") int page, 
			@RequestParam(name = "size") int size) {
		return locationService.findByVehicleMovementType(
				VehicleMovementType.valueOf(movementType), 
				new PageRequest(page, size));
	}
	
	@RequestMapping(value = "/find/vin/{vin}", method = RequestMethod.GET)
	public Page<Location> findByVin(
			@PathVariable String vin, 
			@RequestParam(name = "page") int page, 
			@RequestParam(name = "size") int size) {
		return locationService.findByVin(vin, new PageRequest(page, size));
	}
	
}
