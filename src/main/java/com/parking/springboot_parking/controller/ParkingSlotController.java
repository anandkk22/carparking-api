package com.parking.springboot_parking.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parking.springboot_parking.model.ParkingSlots;
import com.parking.springboot_parking.repository.ParkingSlotRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/parkingslot")
public class ParkingSlotController {

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    @GetMapping("/all")
    public ResponseEntity<List<ParkingSlots>> getAllParkingSlots() {
        List<ParkingSlots> slots = parkingSlotRepository.findAll();
        return ResponseEntity.ok(slots);
    }

    @GetMapping("/{floor_id}")
    public ResponseEntity<List<ParkingSlots>> getParkingSlotsByFloorId(@PathVariable long floor_id) {
        List<ParkingSlots> slots = parkingSlotRepository.findAll().stream()
                .filter(slot -> slot.getFloorId() == floor_id)
                .toList();
        return ResponseEntity.ok(slots);
    }

    @GetMapping("{floor_id}/{slot_number}")
    public ResponseEntity<ParkingSlots> getParkingSlotByFloorIdAndSlotNumber(@PathVariable long floor_id, @PathVariable long slot_number) {
        return parkingSlotRepository.findAll().stream()
            .filter(slot -> slot.getFloorId() == floor_id && slot.getSlotNumber() == slot_number)
            .findFirst()
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> addSlots(@RequestBody ParkingSlots parkingSlots) {
        List<ParkingSlots> slots = parkingSlotRepository.findAll().stream()
                .filter(slot -> slot.getFloorId() == parkingSlots.getFloorId())
                .toList();
        boolean parkingSlotExists = slots.stream()
                .anyMatch(s -> s.getSlotNumber() == parkingSlots.getSlotNumber());

        if (parkingSlotExists) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", "Slot " + parkingSlots.getSlotNumber() + " already exists"));
        }

        return ResponseEntity.ok(parkingSlotRepository.save(parkingSlots));
    }

}
