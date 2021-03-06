package com.railways.login.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "session-client",url = "127.0.0.1:8082")
public interface ClientService {
    @RequestMapping(method = RequestMethod.POST,path = "/setSession")
    void setSessionInBookAndSearch(@RequestParam("userName") String userName,@RequestParam("sessionId") String sessionId,@RequestParam("isLoggedIn") String isLoggedin);

    @RequestMapping(method = RequestMethod.POST,path = "/deleteSession")
    void deleteSesssionInBookAndSearch(@RequestParam("sessionid") String sessionid);

}
