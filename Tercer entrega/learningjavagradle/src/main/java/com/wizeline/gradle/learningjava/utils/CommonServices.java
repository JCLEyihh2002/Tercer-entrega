package com.wizeline.gradle.learningjava.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wizeline.gradle.learningjava.model.ResponseDTO;
import com.wizeline.gradle.learningjava.service.UserService;

@Component
public class CommonServices {

    @Autowired
    UserService userService;

    public ResponseDTO login(String user, String password) {
        //UserService UserService = new UserServiceImpl();
        return userService.login(user, password);
    }

}
