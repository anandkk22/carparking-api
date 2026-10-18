package com.parking.springboot_parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;

import com.parking.springboot_parking.model.AllocateParkingSlot;

public interface AllocateParkingSlotRepository extends JpaRepository<AllocateParkingSlot, Long> {

    // @Query(value = "SELECT EXISTS (SELECT 1 FROM vehicles WHERE vehicle_id = :vehicleId)", nativeQuery = true)
    // boolean vehicleExists(@Param("vehicleId") String vehicleId);

}
