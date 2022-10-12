	package com.wizeline.maven.learningjava.service;

import com.wizeline.maven.learningjava.model.ResponseModel;

public interface UserService {
	
	ResponseModel createUser(String user, String password);
	
	ResponseModel login(String user, String password);

}
