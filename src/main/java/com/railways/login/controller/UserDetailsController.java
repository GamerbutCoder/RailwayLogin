package com.railways.login.controller;

import com.railways.login.dto.LogoutRequestDTO;
import com.railways.login.dto.UserDetailsResponseDTO;
import com.railways.login.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class UserDetailsController {
    @Autowired
    private UserDetailsService userDetailsService;

    private
    @PostMapping("/userDetails")
    ResponseEntity<UserDetailsResponseDTO> getUserDetails(@RequestBody LogoutRequestDTO request){
        return userDetailsService.fetchUserDetails(request);
    }

}
