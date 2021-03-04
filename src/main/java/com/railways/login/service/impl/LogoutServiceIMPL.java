package com.railways.login.service.impl;

import com.railways.login.client.ClientService;
import com.railways.login.dto.LogoutRequestDTO;
import com.railways.login.entity.Sessions;
import com.railways.login.repository.SessionRepository;
import com.railways.login.service.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class LogoutServiceIMPL implements LogoutService {
    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ClientService clientService;

    @Override
    public void logoutUser(LogoutRequestDTO requestDTO) {
        Optional<Sessions> optional = sessionRepository.findById(requestDTO.getUserName());
        if(optional.isPresent()){
            try{
                //sessionRepository.deleteSession(requestDTO.getUserName());
                sessionRepository.updateSessionState("false",requestDTO.getUserName());
                clientService.setSessionInBookAndSearch(requestDTO.getUserName(),"false");

            }
            catch (Exception e){
               // e.printStackTrace();
            }

        }
    }
}
