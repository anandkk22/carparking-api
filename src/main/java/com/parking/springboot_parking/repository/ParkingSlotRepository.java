package com.parking.springboot_parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parking.springboot_parking.model.ParkingSlots;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlots, Long> {

}
