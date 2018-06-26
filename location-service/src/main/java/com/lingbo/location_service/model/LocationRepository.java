package com.lingbo.location_service.model;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface LocationRepository extends JpaRepository<Location, Long> {
	
	Page<Location> findByVehicleMovementType(@Param("movementType") VehicleMovementType movementType, Pageable pageable);
	Page<Location> findByCarInfoVin(@Param("vin") String vin, Pageable pageable);
	
}
