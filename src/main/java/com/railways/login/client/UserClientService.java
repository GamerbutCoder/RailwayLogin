package com.railways.login.client;

import com.railways.login.dto.UserDetailsResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-details-client",url = "127.0.0.1:8081")
public interface UserClientService {
    @RequestMapping(method = RequestMethod.POST,path = "/userDetails")
    public UserDetailsResponseDTO getUserDetailsFromDB(@RequestParam("userName") String userName);
}
