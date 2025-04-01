package com.example.integration;

import com.example.integration.persistence.UserEntity;
import com.example.integration.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    UserRepository userRepository;

    @Autowired
    public Controller(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public String getUserByName(@RequestParam String name){

        UserEntity user = userRepository.findByName(name);

        return user.toString();
    }

}