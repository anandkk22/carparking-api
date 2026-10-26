package com.parking.springboot_parking.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
    public static final long PER_HOUR = 40;
    public static final long FIXED = 200;
    public static final long FREE = 0;
    public static final long MINIMUM = 20;
    public static final long MINIMUM_AMOUNT = 20;

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
                .sorted(Comparator.comparing(ParkingSlots::getFloorId))
                .filter(slot -> "available".equals(slot.getStatus()))
                .findFirst();

        if (availableSlot.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(createAllocation(availableSlot.get(), request));
    }
    
    @GetMapping("/date/{currentDate}/{endDate}")
    public ResponseEntity<List<AllocateParkingSlot>> getAllParkingStatus(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate currentDate,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        LocalDateTime startOfDay = currentDate.atStartOfDay();
        LocalDateTime endOfDay = endDate.atTime(LocalTime.MAX);

        List<AllocateParkingSlot> report = allocateParkingSlotRepository.findByEntryTimeBetween(startOfDay,
                endOfDay);

        if (report.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(report);
    }   

    @PutMapping("/{id}")
    public ResponseEntity<AllocateParkingSlot> outVehicle(@PathVariable long id) {
        AllocateParkingSlot updatedAllocationPartkingSlot = allocateParkingSlotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));

        Optional<ParkingSlots> isReadyToExit  = parkingSlotRepository.findAll().stream()
                .filter(slot -> slot.getSlotId() == updatedAllocationPartkingSlot.getSlotId())
                .findFirst();

        isReadyToExit.ifPresent(slot -> {
            slot.setStatus("available");
            parkingSlotRepository.save(slot);
        });

        updatedAllocationPartkingSlot.setStatus("Ready to Exit");
        updatedAllocationPartkingSlot.setExitTime(LocalDateTime.now());

        LocalDateTime entryTime = updatedAllocationPartkingSlot.getEntryTime();
        LocalDateTime exitTime = updatedAllocationPartkingSlot.getExitTime();
        Duration duration = Duration.between(entryTime, exitTime).abs();

        long totalSeconds = duration.getSeconds();

        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long secs = totalSeconds % 60;

        double totalHours = totalSeconds / 3600.0;
        updatedAllocationPartkingSlot.setTotalHrs(
                String.format("%d hours %d minutes %d seconds", hours, minutes, secs));

        if ("hourly".equalsIgnoreCase(updatedAllocationPartkingSlot.getPricingType())) {
            double amount = Math.round(totalHours * PER_HOUR);
            if (amount < MINIMUM) {
                amount = MINIMUM_AMOUNT;
            }
            updatedAllocationPartkingSlot.setAmount(amount);
        } else if ("fixed".equalsIgnoreCase(updatedAllocationPartkingSlot.getPricingType())) {
            double amount = FIXED;
            updatedAllocationPartkingSlot.setAmount(amount);
        } else if ("free".equalsIgnoreCase(updatedAllocationPartkingSlot.getPricingType())) {
            double amount = FREE;
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
        slot.setStatus("Exit");
        allocateParkingSlotRepository.save(slot);

        return ResponseEntity.ok(slot);

    }

    @GetMapping ("/parked")
    public ResponseEntity<List<AllocateParkingSlot>> getParkedDetails() {
        List<AllocateParkingSlot> allocateParkingSlots = allocateParkingSlotRepository.findAll();
        return  ResponseEntity.ok(allocateParkingSlots);
    }

    private AllocateParkingSlot createAllocation(ParkingSlots slot, AllocateParkingSlotRequest request) {
        slot.setStatus("occupied");
        parkingSlotRepository.save(slot);

        AllocateParkingSlot allocation = new AllocateParkingSlot();
        allocation.setSlotId(slot.getSlotId());
        allocation.setDate(LocalDateTime.now());
        allocation.setFloorId(slot.getFloorId());
        allocation.setSlotNumber(slot.getSlotNumber());
        allocation.setPricingType(request.getPricingType());
        allocation.setVehicleNumber(request.getVehicleNumber());
        allocation.setEntryTime(LocalDateTime.now());
        allocation.setStatus(slot.getStatus());
        allocation.setCustomerName(request.getCustomerName());
        allocation.setMobileNumber(request.getMobileNumber());
        allocation.setAddress(request.getAddress());

        return allocateParkingSlotRepository.save(allocation);
    }
}
