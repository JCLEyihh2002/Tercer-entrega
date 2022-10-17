package com.wizeline.maven.learningjava.controller;


import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wizeline.maven.learningjava.LearningJavaApplication;
import com.wizeline.maven.learningjava.model.ResponseModel;
import com.wizeline.maven.learningjava.model.UserModel;
import com.wizeline.maven.learningjava.service.UserService;
import com.wizeline.maven.learningjava.utils.CommonServices;


@RequestMapping("/api")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    CommonServices commonServices;

    private static final Logger LOGGER = Logger.getLogger(LearningJavaApplication.class.getName());
    String msgProcPeticion = "LearningJava - Inicia procesamiento de peticion ...";

    @GetMapping(value = "/login", produces = "application/json")
    public ResponseEntity<ResponseModel> login(@RequestParam String user, @RequestParam String password){
        LOGGER.info(msgProcPeticion);
        ResponseDTO response = new ResponseDTO();
        response = userService.login(user, password);
        LOGGER.info("Login-completed");
        return new ResponseEntity<ResponseModel>(response, HttpStatus.OK);
        
    }

    @PostMapping("/createUser")
    public  ResponseEntity<ResponseModel> createUserAccount(@RequestBody UserModel userModel) {
        LOGGER.info(msgProcPeticion);
        ResponseModel response = new ResponseModel();

        response = createUser(userModel.getUser(), userModel.getPassword());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
    }

    @PostMapping("/createUsers")
    public  ResponseEntity<List<ResponseModel>> createUsersAccount(@RequestBody List<UserModel> userModelList) {
        LOGGER.info(msgProcPeticion);
        AtomicReference<ResponseModel> response = new AtomicReference<>(new ResponseModel());
        List<ResponseModel> responseList = new ArrayList<>();

        userModelList.stream().forEach( userModel -> {
                    String user = userModel.getUser();
                    String password = userModel.getPassword();
                    response.set(createUser(user, password));
                    responseList.add(response.get());
                }
        );

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");

        return new ResponseEntity<List<ResponseModel>>(responseList, responseHeaders, HttpStatus.OK);
    }


    public static Map<String, String> splitQuery(URI uri) {
        Map<String, String> queryPairs = new LinkedHashMap<String, String>();
        String query = uri.getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            queryPairs.put(URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8),
                    URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8));
        }
        return queryPairs;
    }

    private ResponseModel createUser(String user, String password) {
        return userService.createUser(user, password);
    }
}
