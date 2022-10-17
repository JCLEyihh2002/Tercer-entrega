package com.wizeline.maven.learningjava.service;


import com.wizeline.gradle.learningjava.model.ResponseDTO;

public interface UserBO {

    ResponseDTO createUser(String user, String password);
    ResponseDTO login(String user, String password);
}
