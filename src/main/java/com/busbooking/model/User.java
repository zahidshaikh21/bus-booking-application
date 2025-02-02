package com.busbooking.model;

import com.busbooking.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // Ensure username is unique and not null
    private String username;

    @Column(nullable = false) // Ensure password is not null
    private String password;

    @Column(nullable = false, unique = true) // Ensure email is unique and not null
    private String email;

    @Column(nullable = false) // Ensure phone is not null
    private String phone;

    @Column(nullable = false) // Ensure roles is not null
    private String roles = Role.USER.name(); // Default role is USER

    // No need for explicit getters and setters (handled by Lombok)
}