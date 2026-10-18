package com.parking.springboot_parking.model;


import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class AllocateParkingSlotRequest {
    private String vehicleNumber;
    private String pricingType;

    public AllocateParkingSlotRequest() {
    }

    public AllocateParkingSlotRequest(String vehicleNumber, String pricingType) {     
        this.vehicleNumber = vehicleNumber;
        this.pricingType = pricingType;
    }
}
