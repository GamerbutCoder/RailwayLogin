package com.railways.login.service;

import com.railways.login.dto.LoginRequestDTO;
import com.railways.login.dto.LoginResponseDTO;

public interface LoginService {
    LoginResponseDTO doLogin(LoginRequestDTO requestDTO);
    void insertNewLogin(LoginRequestDTO requestDTO);
}
