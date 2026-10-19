package com.parking.springboot_parking.model;

import java.time.LocalDateTime;

// import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
// import lombok.AllArgsConstructor;
import lombok.Getter;
// import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "parkingtransaction")
public class AllocateParkingSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(name = "slot_id")
    private Long slotId;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "pricing_type")
    private String pricingType;

    @Column(name = "floor_id")
    private int floorId;

    @Column(name = "slot_number")
    private int slotNumber;

    @Column(name = "vehicle_number")
    private String vehicleNumber;

    @Column(name = "entry_time")
    private LocalDateTime entryTime;

    @Column(name = "exit_time")
    private LocalDateTime exitTime;

    @Column(name = "total_hrs")
    private String totalHrs;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "status")
    private String status;

    @Column(name = "mode_of_payment")
    private String modeOfPayment;

    public AllocateParkingSlot() {
    }

    public AllocateParkingSlot(Long transactionId, Long slotId, int floorId, int slotNumber, String vehicleNumber,
            LocalDateTime entryTime, LocalDateTime exitTime, String totalHrs, Double amount, String status, String modeOfPayment,
            LocalDateTime date) {
        this.transactionId = transactionId;
        this.slotId = slotId;
        this.floorId = floorId;
        this.slotNumber = slotNumber;
        this.vehicleNumber = vehicleNumber;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.totalHrs = totalHrs;
        this.amount = amount;
        this.status = status;
        this.modeOfPayment = modeOfPayment;
        this.date = date;
    }

}
