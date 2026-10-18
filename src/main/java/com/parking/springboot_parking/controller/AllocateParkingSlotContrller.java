package com.parking.springboot_parking.controller;

import java.util.Optional;
import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parking.springboot_parking.exception.ResourceNotFoundException;
import com.parking.springboot_parking.model.AllocateParkingSlot;
import com.parking.springboot_parking.model.AllocateParkingSlotRequest;
import com.parking.springboot_parking.model.ParkingSlots;
import com.parking.springboot_parking.repository.AllocateParkingSlotRepository;
import com.parking.springboot_parking.repository.ParkingSlotRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/parkingslot")
public class AllocateParkingSlotContrller {

    @Autowired
    private AllocateParkingSlotRepository allocateParkingSlotRepository;

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    @PostMapping("/allocateParking")
    public ResponseEntity<AllocateParkingSlot> addVehicle(@RequestBody AllocateParkingSlotRequest request) {
        if (request.getVehicleNumber() == null || request.getVehicleNumber().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<ParkingSlots> availableSlot = parkingSlotRepository.findAll().stream()
                .filter(slot -> "available".equals(slot.getOccupied()))
                .findFirst();

        if (availableSlot.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(createAllocation(availableSlot.get(), request));
    }

    private AllocateParkingSlot createAllocation(ParkingSlots slot, AllocateParkingSlotRequest request) {
        slot.setOccupied("occupied");
        parkingSlotRepository.save(slot);

        AllocateParkingSlot allocation = new AllocateParkingSlot();
        allocation.setSlotId(slot.getSlotId());
        allocation.setFloorId(slot.getFloorId());
        allocation.setSlotNumber(slot.getSlotNumber());
        allocation.setPricingType(request.getPricingType());
        allocation.setVehicleNumber(request.getVehicleNumber());
        allocation.setEntryTime(LocalDateTime.now().toString());
        allocation.setOccupied(slot.getOccupied());

        return allocateParkingSlotRepository.save(allocation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AllocateParkingSlot> outVehicle(@PathVariable long id) {
        AllocateParkingSlot updatedAllocationPartkingSlot = allocateParkingSlotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));

        Optional<ParkingSlots> outPark = parkingSlotRepository.findAll().stream()
                .filter(slot -> slot.getSlotId() == updatedAllocationPartkingSlot.getSlotId())
                .findFirst();

        outPark.ifPresent(slot -> {
            slot.setOccupied("available");
            parkingSlotRepository.save(slot);
        });

        updatedAllocationPartkingSlot.setOccupied("out");
        updatedAllocationPartkingSlot.setExitTime(LocalDateTime.now().toString());
        Duration duration = Duration.between(
                LocalDateTime.parse(updatedAllocationPartkingSlot.getExitTime()),
                LocalDateTime.parse(updatedAllocationPartkingSlot.getEntryTime())).abs();

        long totalSeconds = duration.getSeconds();

        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long secs = totalSeconds % 60;

        double totalHours = totalSeconds / 3600.0;
        updatedAllocationPartkingSlot.setTotalHrs(
                String.format("%d hours %d minutes %d seconds", hours, minutes, secs));

        if ("hourlybased".equalsIgnoreCase(updatedAllocationPartkingSlot.getPricingType())) {
            double amount = Math.round(totalHours * 40);
            if (amount < 20) {
                amount = 20;
            }
            updatedAllocationPartkingSlot.setAmount(amount);
        } else if ("fixed".equalsIgnoreCase(updatedAllocationPartkingSlot.getPricingType())) {
            double amount = 200;
            updatedAllocationPartkingSlot.setAmount(amount);
        }

        allocateParkingSlotRepository.save(updatedAllocationPartkingSlot);

        return ResponseEntity.ok(updatedAllocationPartkingSlot);
    }

    @PostMapping("/{id}/payment")
    public ResponseEntity<AllocateParkingSlot> makePayment(@PathVariable long id, @RequestParam String paymentMode) {
        AllocateParkingSlot slot = allocateParkingSlotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with id: " + id));

        slot.setModeOfPayment(paymentMode);
        slot.setOccupied("Paymen Done");
        allocateParkingSlotRepository.save(slot);

        return ResponseEntity.ok(slot);

    }
}
