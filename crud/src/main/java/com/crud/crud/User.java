package com.crud.crud;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;

@Entity
@Table(name = "users2")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String lastname;
    private String username;
    private String password;
    private String email;
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

}
