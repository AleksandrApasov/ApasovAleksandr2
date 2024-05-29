package com.example.demo.Models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Data
@Entity
@Table(name = "auser")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    @Email
    private String email;
    @Column(unique=true)
    @Pattern(regexp = "^\\+?[0-9\\-\\s]*$",
            message = "Неправильный формат номера")
    private String phoneNumber;
    private String password;
    @Min(0)
    @ColumnDefault("0")
    private double balance;
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @Pattern(regexp = "[A-Я]{1}[а-я]+\\s[A-Я]{1}[а-я]+\\s[A-Я]{1}[а-я]+",
            message = "Неправильный формат ФИО")
    private String fio;

    public User(String username, String email, String password, double balance, Date birthDate,
                String fio) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.birthDate = birthDate;
        this.fio = fio;
    }
    public User() {
        super();
    }
}
