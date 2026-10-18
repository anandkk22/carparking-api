package com.parking.springboot_parking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Column (name = "pricing_type") 
    private String pricingType;

    @Column(name = "floor_id")
    private int floorId;
    
    @Column(name = "slot_number")
    private int slotNumber;

    @Column(name = "vehicle_number")
    private String vehicleNumber;

    @Column(name = "entry_time")
    private String entryTime;

    @Column(name = "exit_time")
    private String exitTime;

    @Column(name = "total_hrs")
    private String totalHrs;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "occupied")
    private String occupied;

    @Column(name = "mode_of_payment")
    private String modeOfPayment;

    public AllocateParkingSlot() {
    }

    public AllocateParkingSlot(Long transactionId, Long slotId, int floorId, int slotNumber, String vehicleNumber, String entryTime,  String exitTime, String totalHrs, Double amount, String occupied, String modeOfPayment) {
        this.transactionId = transactionId;
        this.slotId = slotId;
        this.floorId = floorId;
        this.slotNumber = slotNumber;
        this.vehicleNumber = vehicleNumber;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.totalHrs = totalHrs;
        this.amount = amount;
        this.occupied = occupied;
        this.modeOfPayment = modeOfPayment;

    }

}
