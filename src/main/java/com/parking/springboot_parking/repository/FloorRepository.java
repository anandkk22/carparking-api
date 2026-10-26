package com.parking.springboot_parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parking.springboot_parking.model.Floor;

public interface FloorRepository extends JpaRepository<Floor, Long> {

}
