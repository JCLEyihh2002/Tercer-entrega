package com.wizeline.gradle.learningjava.service;

import com.wizeline.gradle.learningjava.model.ResponseDTO;

public interface UserService {

    ResponseDTO createUser(String user, String password);

    ResponseDTO login(String user, String password);

}
