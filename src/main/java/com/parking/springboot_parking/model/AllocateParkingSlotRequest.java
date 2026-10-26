package com.parking.springboot_parking.model;


import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class AllocateParkingSlotRequest {
    private String vehicleNumber;
    private String pricingType;
    private String customerName;
    private String mobileNumber;
    private String address;

    public AllocateParkingSlotRequest() {
    }

    public AllocateParkingSlotRequest(String vehicleNumber, String pricingType, String customerName, String mobileNumber, String address ) {     
        this.vehicleNumber = vehicleNumber;
        this.pricingType = pricingType;
        this.customerName = customerName;
        this.mobileNumber = mobileNumber;
        this.address = address;
    }
}
