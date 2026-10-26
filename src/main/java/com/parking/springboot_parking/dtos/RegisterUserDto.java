package com.parking.springboot_parking.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter 
@ToString 
public class RegisterUserDto {
    
    private String email;    
    private String password;    
    private String fullName;

    public RegisterUserDto() {
    }

    public RegisterUserDto(String email, String password, String fullName) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }
}
