package com.busbooking.controller;

import com.busbooking.model.User;
import com.busbooking.model.dto.LoginRequest;
import com.busbooking.model.dto.UserDto;
import com.busbooking.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/dashboard")
    public String userDashboard(HttpSession session, Model model) {
        UserDto loggedInUser = (UserDto) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login"; // Redirect if not authenticated
        }
        model.addAttribute("loggedInUser", loggedInUser);
        return "user-dashboard"; // Thymeleaf template
    }


    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        // Check if user already exists
        Optional<User> existingUser = userService.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
//            model.addAttribute("error", "Username is already taken!");
            return "register"; // Return to the registration page with an error
        }

        // Register the new user
        System.out.println(user.getPassword());
        userService.registerUser(user);

        return "redirect:/login"; // Redirect to login after successful registration
    }
}