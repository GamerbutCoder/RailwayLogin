package com.railways.login.controller;

import com.railways.login.dto.LoginRequestDTO;
import com.railways.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/insert")
    public void insertIntoLogin(@RequestBody LoginRequestDTO requestDTO){
        loginService.insertNewLogin(requestDTO);
    }

    @PostMapping("")
    public boolean doLogin(@RequestBody LoginRequestDTO req){
        return loginService.doLogin(req);
    }

}
