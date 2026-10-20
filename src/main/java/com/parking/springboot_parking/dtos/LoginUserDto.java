package com.parking.springboot_parking.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter 
@ToString
public class LoginUserDto {
    private String email;    
    private String password;

    public LoginUserDto() {

    }

    public LoginUserDto(String email, String password ) {
        this.email = email;
        this.password = password;
    }
}
