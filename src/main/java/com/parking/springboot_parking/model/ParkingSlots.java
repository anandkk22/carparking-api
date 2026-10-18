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

    @Column(name = "is_occupied")
    private String occupied;

    public ParkingSlots() {
    }

    public ParkingSlots(Long slotId, int floorId, int slotNumber, String occupied) {
        this.slotId = slotId;
        this.floorId = floorId;
        this.slotNumber = slotNumber;
        this.occupied = occupied;
    }

    @Override
    public String toString() {
    return "ParkingSlots{" +
            "slotId=" + slotId +
            ", floorId=" + floorId +
            ", slotNumber=" + slotNumber +
            ", occupied=" + occupied +
            '}';
}
}
