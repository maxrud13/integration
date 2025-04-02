package com.example.integration.controller;

import com.example.integration.domain.CustomerVisitEvent;
import com.example.integration.persistence.UserEntity;
import com.example.integration.persistence.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
public class Controller {
    UserRepository userRepository;


    KafkaTemplate<String, String> kafkaTemplate;


    ObjectMapper objectMapper;

    @Value("${max.kafka.topic}")
    private String topicName;

    public Controller(UserRepository userRepository, KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/users")
    public String getUserByName(@RequestParam String name) {

        UserEntity user = userRepository.findByName(name);

        sendKafka();

        return user.toString();
    }

    private void sendKafka() {
        try {
            final CustomerVisitEvent event = CustomerVisitEvent.builder()
                    .customerId(UUID.randomUUID().toString())
                    .dateTime(LocalDateTime.now())
                    .build();

            final String payload = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(topicName, payload);
        }
        catch (Exception e){

        }
    }


}