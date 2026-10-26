package com.parking.springboot_parking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter 
@Setter 
@Entity 
@Table (name = "floorsDetails")
public class Floor {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long floorId;

    @Column (name = "floorNumber")
    private int floorNumber;

    @Column (name = "description")
    private String description;

    public Floor() {

    }

    public Floor(Long floorId, int floorNumber, String description) {
        this.floorId = floorId;
        this.floorNumber = floorNumber;
        this.description = description;
    }

}
