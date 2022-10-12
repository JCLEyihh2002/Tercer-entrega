package com.wizeline.maven.learningjava.service;

import java.util.logging.Logger;

import com.wizeline.maven.learningjava.model.ErrorModel;
import com.wizeline.maven.learningjava.model.ResponseModel;
import com.wizeline.maven.learningjava.repository.UserRepository;
import com.wizeline.maven.learningjava.repository.UserRepositoryImpl;
import com.wizeline.maven.learningjava.utils.Utils;

@Service
public class UserServiceImpl implements UserService {
	
private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());
	
@Override
public ResponseModel createUser(String user, String password) {
	LOGGER.info("Inicia procesamiento en capa de negocio");
	ResponseModel responseDTO;
	String result = "fail"; 
	if (Utils.validateNullValue(user) && Utils.validateNullValue(password)) {
		
		// UserDAO userDao = new UserDAOImpl();
		UserRepository userDao = UserRepositoryImpl.getInstance();//aplicando patron singleton
		
		
		result = userDao.createUser(user, password);
		
		//response.setCode("OK000");
		//response.setStatus(result);
		
		//aplicando patron builder
		 responseDTO = new ResponseModel.ResponseDTOBuilder().code("OK000").status(result).build();	
	}else {
		//response.setCode("OK000");
		//response.setStatus(result);
		//response.setErrors(new ErrorDTO("ER001","Error al crear usuario"));
		
		//aplicando el patron builder
		 responseDTO = new ResponseModel.ResponseDTOBuilder()
				.code("OK000")
				.errors(new ErrorModel("ER001","Error al crear usuario"))
				.status(result)
				.build();	
		
	}
	return responseDTO;
}

@Override
public ResponseModel login(String user, String password) {
	LOGGER.info("Inicia procesamiento en capa de negocio");
	
	String result = "";
	ResponseModel responseDTO;
	if (Utils.validateNullValue(user) && Utils.validateNullValue(password)) {
		
		
        // UserDAO userDao = new UserDAOImpl();
		UserRepository userDao = UserRepositoryImpl.getInstance();//aplicando patron singleton
			
		
		result = userDao.login(user, password);
	}
	if("success".equals(result)) {
		
		//response.setCode("OK000");
		//response.setStatus(result);
		
		//aplicando el patron builder
		 responseDTO = new ResponseModel.ResponseDTOBuilder().code("OK000").status(result).build();			
	} else {
		
	//	response.setCode("ER001");
	//	response.setErrors(new ErrorDTO("ER001",result));
	//	response.setStatus("fail");
		
		//aplicando el patron builder
		 responseDTO = new ResponseModel.ResponseDTOBuilder()
				.code("ER001")
				.errors(new ErrorModel("ER001",result))
				.status("fail")
				.build();	
		
	}
	return responseDTO;
}

}
