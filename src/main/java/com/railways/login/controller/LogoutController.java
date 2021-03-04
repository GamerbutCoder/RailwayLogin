package com.railways.login.controller;

import com.railways.login.dto.LogoutRequestDTO;
import com.railways.login.service.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/logout")
public class LogoutController {

    @Autowired
    private LogoutService logoutService;

    @PostMapping("")
    public void doLogout(@RequestBody LogoutRequestDTO request){
        logoutService.logoutUser(request);
    }
}
