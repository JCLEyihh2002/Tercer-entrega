package com.wizeline.gradle.learningjava.repository;

public interface UserDAO {

    String createUser(String user, String password);
    String login(String user, String password);
}
