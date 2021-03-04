package com.railways.login.service.impl;

import com.railways.login.dto.LoginRequestDTO;
import com.railways.login.entity.Login;
import com.railways.login.entity.Sessions;
import com.railways.login.repository.LoginRepository;
import com.railways.login.repository.SessionRepository;
import com.railways.login.service.LoginService;
import com.railways.login.util.CustomHash;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class LoginServiceIMPL implements LoginService {
    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public void insertNewLogin(LoginRequestDTO requestDTO) {
        Login login = new Login();
        BeanUtils.copyProperties(requestDTO,login);
        loginRepository.save(login);
    }

    @Override
    // TODO: pass UserValidation
    public boolean doLogin(LoginRequestDTO requestDTO) {

        Optional<Login> optional = loginRepository.findById(requestDTO.getUserName());
        if(optional.isPresent()){
            String hashedpassword = DigestUtils.sha256Hex(requestDTO.getPassword());
            Sessions sessions = new Sessions();
            sessions.setUserName(CustomHash.hashString(requestDTO.getUserName()));
            sessions.setLoggedIn(true);
            sessionRepository.save(sessions);
            return (optional.get().getPassword().equals(hashedpassword));
        }
        return false;
    }
}
