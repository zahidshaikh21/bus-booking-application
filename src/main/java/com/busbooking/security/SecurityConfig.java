package com.busbooking.security;

import com.busbooking.model.dto.UserDto;
import com.busbooking.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return userService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService(userService));
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF (for now)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/api/users/register", "/register", "/login", "/css/**", "/static/js/**", "/images/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login") // Custom login page (GET request)
                        .loginProcessingUrl("/login") // Spring Security handles POST login
                        .successHandler((request, response, authentication) -> {
                            // Get logged-in user details
                            UserDto loggedInUser = (UserDto) authentication.getPrincipal();

                            // Store user in session
                            request.getSession().setAttribute("loggedInUser", loggedInUser);

                            // Redirect based on role
                            String role = authentication.getAuthorities().toString();
                            if (role.contains("ADMIN")) {
                                response.sendRedirect("api/admin/dashboard"); // Admin dashboard
                            } else {
                                response.sendRedirect("/api/user/dashboard"); // User dashboard
                            }
                        })
                        .failureUrl("/login?error=true") // Redirect to login page with error
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }
}