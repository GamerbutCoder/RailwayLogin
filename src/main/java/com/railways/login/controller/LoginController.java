package com.railways.login.controller;

import com.railways.login.dto.LoginRequestDTO;
import com.railways.login.dto.LoginResponseDTO;
import com.railways.login.dto.LogoutRequestDTO;
import com.railways.login.service.LoginService;
import com.railways.login.service.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

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
    public LoginResponseDTO doLogin(@RequestBody LoginRequestDTO req, HttpServletRequest request){
        //Cookie cookies[] = request.getCookies();
        //System.out.println(cookies[0].getName());
        return loginService.doLogin(req);
    }






}
