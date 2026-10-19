package com.parking.springboot_parking.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;

import com.parking.springboot_parking.model.AllocateParkingSlot;

public interface AllocateParkingSlotRepository extends JpaRepository<AllocateParkingSlot, Long> {
    List<AllocateParkingSlot> findByEntryTimeBetween(LocalDateTime start, LocalDateTime end);

    // @Query(value = "SELECT EXISTS (SELECT 1 FROM vehicles WHERE vehicle_id = :vehicleId)", nativeQuery = true)
    // boolean vehicleExists(@Param("vehicleId") String vehicleId);

}
