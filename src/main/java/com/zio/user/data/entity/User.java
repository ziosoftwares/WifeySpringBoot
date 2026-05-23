package com.zio.user.data.entity;

import com.zio.user.data.LoginDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String userName;
    private String password;

    private LocalDate joined;

    public User(LoginDTO loginDTO) {
        email = loginDTO.getEmail();
        userName = loginDTO.getUserName();
        password = loginDTO.getPassword();
        joined = LocalDate.now();
    }
}
