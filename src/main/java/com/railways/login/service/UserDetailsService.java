package com.railways.login.service;

import com.railways.login.dto.LogoutRequestDTO;
import com.railways.login.dto.UserDetailsResponseDTO;
import org.springframework.http.ResponseEntity;

public interface UserDetailsService {
    ResponseEntity<UserDetailsResponseDTO> fetchUserDetails(LogoutRequestDTO req);
}
