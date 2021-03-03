package com.railways.login.service;

import com.railways.login.dto.LoginRequestDTO;

public interface LoginService {
    boolean doLogin(LoginRequestDTO requestDTO);
    void insertNewLogin(LoginRequestDTO requestDTO);
}
