package com.in28minutes.springboot.firstwebapplication.Login;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    public boolean validateUser(String userid, String password) {
        return userid.equalsIgnoreCase("in28Minutes") && password.equalsIgnoreCase("dummy");
    }
}
