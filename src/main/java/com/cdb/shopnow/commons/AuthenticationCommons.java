package com.cdb.shopnow.commons;

import com.cdb.shopnow.dtos.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthenticationCommons {

    private RestTemplate rt;
    public AuthenticationCommons(RestTemplate rt){
        this.rt =rt;
    }
    public UserDto validateToken(String token){
       ResponseEntity<UserDto> userDtoResponseEntity = rt.postForEntity("http://localhost:8181/users/validate/"+token,null, UserDto.class);
       if (userDtoResponseEntity.getBody() == null) {
           return null;
        } else {
            return userDtoResponseEntity.getBody();
        }
    }
}
