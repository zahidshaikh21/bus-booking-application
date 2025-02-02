package com.busbooking;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordCheck {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String rawPassword = "test";
        String newHashedPassword = encoder.encode(rawPassword);

        System.out.println("New Hashed Password for 'test': " + newHashedPassword);
    }
}
