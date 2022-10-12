package com.wizeline.maven.learningjava.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wizeline.maven.learningjava.model.ResponseModel;
import com.wizeline.maven.learningjava.service.UserService;

@Component
public class CommonServices {

    @Autowired
    UserService userService;

    public ResponseModel login(String user, String password) {
        //UserService UserService = new UserServiceImpl();
        return userService.login(user, password);
    }

}
