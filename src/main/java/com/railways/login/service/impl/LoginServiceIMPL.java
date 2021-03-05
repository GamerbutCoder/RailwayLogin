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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.ThreadLocalRandom;



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
            hashedpassword = CustomHash.hashString(hashedpassword);
            boolean ans = (optional.get().getPassword().equals(hashedpassword));
            if(ans){
                Sessions sessions = new Sessions();

                String sessionID = requestDTO.getUserName() + java.time.LocalDate.now().toString() + java.time.LocalTime.now().toString();
                System.out.println("jsut before hashing "+sessionID);
                int randomNum = ThreadLocalRandom.current().nextInt(1, 6);
                for (int i = 0; i < randomNum; i++) {
                    sessionID = CustomHash.hashString(sessionID);
                }

                    sessions.setSessionID(sessionID);
                    sessions.setIsLoggedIn("true");
                    sessions.setUserName(requestDTO.getUserName());
                    sessionRepository.save(sessions);

                clientService.setSessionInBookAndSearch(requestDTO.getUserName(),sessionID,"true");
                responseDTO.setMessage("SUCCESS");
                responseDTO.setSessionID(sessionID);
                return responseDTO;
            }
            else{
                responseDTO.setMessage("FAILED");
                responseDTO.setSessionID("");
            }
        }
        responseDTO.setMessage("FAILED");
        responseDTO.setSessionID("");
        return responseDTO;
    }
}
