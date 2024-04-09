package com.example.github.service.service;

import com.example.github.config.security.JwtTokenProvider;
import com.example.github.repository.role.Role;
import com.example.github.repository.role.RoleJpa;
import com.example.github.repository.user.User;
import com.example.github.repository.user.UserJpa;
import com.example.github.repository.userRole.UserRole;
import com.example.github.repository.userRole.UserRoleJpa;
import com.example.github.web.DTO.auth.LoginRequest;
import com.example.github.web.DTO.auth.SignUpRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.server.NotAcceptableStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserJpa userJpa;
    private final UserRoleJpa userRoleJpa;
    private final RoleJpa roleJpa;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    //    @Transactional(transactionManager = "tm")
    public boolean signUp(SignUpRequest signUpRequest) {
        if(userJpa.existsByEmail(signUpRequest.getEmail())){
            return false;
        }
        Role role= roleJpa.findByName("ROLE_USER");

        User user= User.builder()
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .name(signUpRequest.getName())
                .phoneNumber(signUpRequest.getPhoneNumber())
                .createdAt(LocalDateTime.now())
                .build();
        userJpa.save(user);
        userRoleJpa.save(
                UserRole.builder()
                        .user(user)
                        .role(role)
                        .build()
        );
        return true;
    }

    public String login(LoginRequest loginRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),  loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user= userJpa.findByEmailFetchJoin(loginRequest.getEmail())
                    .orElseThrow(()-> new NullPointerException("해당 이메일로 계정을 찾을 수 없습니다."));

            List<String> roles= user.getUserRole().stream().map(UserRole::getRole).map(Role::getName).collect(Collectors.toList());
            return jwtTokenProvider.createToken(loginRequest.getEmail(), roles);

        } catch(Exception e){
            e.printStackTrace();
            throw new NotAcceptableStatusException("login not possible");
        }
    }


    public boolean logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Authentication auth= SecurityContextHolder.getContext().getAuthentication(); //JwtAuthenticationFilter에서 setAuthentication했음
        if(auth != null){
            String currentToken= jwtTokenProvider.resolveToken(httpServletRequest);
            jwtTokenProvider.addToBlackList(currentToken);
            new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, auth);
        }
        return true;
    }

}