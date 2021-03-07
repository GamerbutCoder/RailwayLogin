package com.railways.login.service.impl;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.railways.login.client.ClientService;
import com.railways.login.dto.LoginRequestDTO;
import com.railways.login.dto.LoginResponseDTO;
import com.railways.login.entity.Login;
import com.railways.login.entity.Sessions;
import com.railways.login.repository.LoginRepository;
import com.railways.login.repository.SessionRepository;
import com.railways.login.service.LoginService;
import com.railways.login.util.CustomHash;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class LoginServiceIMPL implements LoginService {

        public static Properties getPropertiesOfKafka(){
            Properties props = new Properties();
            props.put("bootstrap.servers", "localhost:9092");
            props.put("acks", "all");
            props.put("retries", 0);
            props.put("linger.ms", 1);
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            return props;
        }




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

//                clientService.setSessionInBookAndSearch(requestDTO.getUserName(),sessionID,"true");
                responseDTO.setMessage("SUCCESS");
                responseDTO.setSessionID(sessionID);
                kafkaMethod(requestDTO.getUserName(),sessionID+" "+"true");
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
    private void kafkaMethod(String userName ,String sessionID){
        Producer<String,String> producer = new KafkaProducer<>(getPropertiesOfKafka());
        producer.send(new ProducerRecord<>("sessions-kafka",userName,sessionID));
        producer.close();
    }
}
