package com.railways.login.dto;

import lombok.Data;

@Data
public class UserDetailsResponseDTO {
    private String userName;
    private String fname;
    private String mname;
    private String lname;
    private Long age;
    private String email;
    private Long phoneNumber;
}
