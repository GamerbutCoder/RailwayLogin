package com.railways.login.service.impl;

import com.railways.login.client.ClientService;
import com.railways.login.dto.LoginRequestDTO;
import com.railways.login.dto.LoginResponseDTO;
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

    @Autowired
    private ClientService clientService;

    @Override
    public void insertNewLogin(LoginRequestDTO requestDTO) {
        Login login = new Login();
        BeanUtils.copyProperties(requestDTO,login);
        loginRepository.save(login);
    }

    @Override
    // TODO: pass UserValidation
    public LoginResponseDTO doLogin(LoginRequestDTO requestDTO) {
        LoginResponseDTO responseDTO = new LoginResponseDTO();
        Optional<Login> optional = loginRepository.findById(requestDTO.getUserName());
        if(optional.isPresent()){
            String hashedpassword = CustomHash.hashString(requestDTO.getPassword());

            boolean ans = (optional.get().getPassword().equals(hashedpassword));
            if(ans){
                Sessions sessions = new Sessions();
                String userName = CustomHash.hashString(requestDTO.getUserName());
                Optional<Sessions> optional1 = sessionRepository.findById(userName);
                if(optional1.isPresent()){
//                    try{
//                        sessionRepository.updateSessionState("true",userName);
//                    }
//                    catch (Exception e){
//                        //e.printStackTrace();
                   // }
                    //This shouldn't happen
                }
                else{
                    sessions.setIsLoggedIn("true");
                    sessions.setUserName(userName);
                    sessionRepository.save(sessions);
                }
                clientService.setSessionInBookAndSearch(userName,"true");
                responseDTO.setMessage("SUCCESS");
                responseDTO.setUserName(userName);
                return responseDTO;
            }
            else{
                responseDTO.setMessage("FAILED");
                responseDTO.setUserName("");
            }
        }
        responseDTO.setMessage("FAILED");
        responseDTO.setUserName("");
        return responseDTO;
    }
}
