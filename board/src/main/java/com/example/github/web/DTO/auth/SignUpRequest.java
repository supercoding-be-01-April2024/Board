package com.example.github.web.DTO.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
}
