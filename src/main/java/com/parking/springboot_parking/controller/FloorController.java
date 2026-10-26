package com.parking.springboot_parking.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parking.springboot_parking.model.Floor;
import com.parking.springboot_parking.repository.FloorRepository;

@CrossOrigin ("*")
@RestController 
@RequestMapping ("api/v1/floor")
public class FloorController {

    @Autowired 
    private FloorRepository floorRepository;

    @GetMapping ("/all")
    public ResponseEntity<List<Floor>> getAllFloors() {
        List<Floor> floors = floorRepository.findAll();
        return ResponseEntity.ok(floors);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addFloor(@RequestBody Floor floor) {
        List<Floor> floors = floorRepository.findAll();
        boolean floorNumberExists = floors.stream()
                .anyMatch(f -> f.getFloorNumber() == floor.getFloorNumber());

        if (floorNumberExists) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", "floorNumber " + floor.getFloorNumber() + " already exists"));
        }

        boolean descriptionExists = floors.stream()
                .anyMatch(f -> f.getDescription().equalsIgnoreCase(floor.getDescription()));

        if (descriptionExists) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", "description '" + floor.getDescription() + "' already exists"));
        }
        Floor savedFloor = floorRepository.save(floor);
        return ResponseEntity.ok(floorRepository.save(savedFloor));
    }
}
