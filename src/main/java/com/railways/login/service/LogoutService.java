package com.railways.login.service;

import com.railways.login.dto.LogoutRequestDTO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


public interface LogoutService {
    void logoutUser(LogoutRequestDTO request);
}
