package com.busbooking.controller; // New package for admin controllers

import com.busbooking.model.User;
import com.busbooking.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/api/admin/manage-users") // Admin-specific URL
public class AdminUserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String manageUsers(Model model) {
        List<User> users = userService.getAllUsers(); // Get all users
        model.addAttribute("users", users);
        return "admin-manage-users"; // Thymeleaf template name
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String updateUser(@RequestParam Long id, @RequestParam Map<String, String> updatedUserData, RedirectAttributes redirectAttributes) {
        try {
            Optional<User> existingUser = userService.getUserById(id);
            if (existingUser.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "User not found!");
                return "redirect:/api/admin/manage-users";
            }

            User userToUpdate = existingUser.get();

            userToUpdate.setUsername(updatedUserData.get("username"));
            userToUpdate.setEmail(updatedUserData.get("email"));
            userToUpdate.setPhone(updatedUserData.get("phone"));
            userToUpdate.setRoles(updatedUserData.get("roles"));

            userService.saveUser(userToUpdate);
            redirectAttributes.addFlashAttribute("successMessage", "User updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating user: " + e.getMessage());
        }
        return "redirect:/api/admin/manage-users";
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting user: " + e.getMessage());
        }
    }
}