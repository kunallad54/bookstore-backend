package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.dto.UserRegistrationDTO;
import com.bridgelabz.bookstoreapp.service.IUserService;
import com.bridgelabz.bookstoreapp.serviceimplementation.UserServiceImplementation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/registerUser")
    public ResponseEntity<String> registerUser(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO){
        log.info("Inside registerUser controller method");
        return new ResponseEntity<>(userService.registerUser(userRegistrationDTO), HttpStatus.CREATED);
    }
}
