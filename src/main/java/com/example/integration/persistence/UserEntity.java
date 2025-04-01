package com.example.integration.persistence;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users", schema = "USER_APP")
public class UserEntity {
    @Id
    private Integer userID;
    private String name;
    private String email;

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + userID +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}