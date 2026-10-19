package com.parking.springboot_parking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
@Entity
@Table(name = "parkingslots")
public class ParkingSlots {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long slotId;

    @Column(name = "floor_id")
    private int floorId;

    @Column(name = "slot_number")
    private int slotNumber;

    @Column(name = "status")
    private String status;

    public ParkingSlots() {
    }

    public ParkingSlots(Long slotId, int floorId, int slotNumber, String status) {
        this.slotId = slotId;
        this.floorId = floorId;
        this.slotNumber = slotNumber;
        this.status = status;
    }

    @Override
    public String toString() {
    return "ParkingSlots{" +
            "slotId=" + slotId +
            ", floorId=" + floorId +
            ", slotNumber=" + slotNumber +
            ", status=" + status +
            '}';
}
}
