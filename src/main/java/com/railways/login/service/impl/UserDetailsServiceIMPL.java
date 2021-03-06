package com.railways.login.service.impl;

import com.railways.login.client.UserClientService;
import com.railways.login.dto.LogoutRequestDTO;
import com.railways.login.dto.UserDetailsResponseDTO;
import com.railways.login.repository.SessionRepository;
import com.railways.login.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceIMPL implements UserDetailsService {
    @Autowired
    private UserClientService userClientService;

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public ResponseEntity<UserDetailsResponseDTO> fetchUserDetails(LogoutRequestDTO req) {

        ResponseEntity<UserDetailsResponseDTO> responseEntity;
        String userName = sessionRepository.findUserNameBySessionId(req.getSessionId());
        if( userName == null || userName.length()==0){
            responseEntity = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            return responseEntity;
        }
        else{
            UserDetailsResponseDTO responseDTO = userClientService.getUserDetailsFromDB(userName);
            if(responseDTO!=null){
                responseEntity = new ResponseEntity<>(responseDTO,HttpStatus.OK);
                return responseEntity;
            }
            else{
                responseEntity = new ResponseEntity<>(null, HttpStatus.NO_CONTENT); //this actually shouldn't happen
                return responseEntity;
            }
        }
    }
}
