package com.example.github.repository.user;

import com.example.github.repository.userRole.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="userId")
@Table(name= "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "user_id")
    private Integer userId;

    @Column(name= "email", nullable = false)
    private String email;

    @Column(name= "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name= "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name= "create_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy= "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> userRole;

}
