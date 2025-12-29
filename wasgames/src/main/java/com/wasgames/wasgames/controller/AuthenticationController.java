package com.wasgames.wasgames.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wasgames.wasgames.dto.LoginDTO;
import com.wasgames.wasgames.dto.UserDTO;
import com.wasgames.wasgames.model.Role;
import com.wasgames.wasgames.model.User;
import com.wasgames.wasgames.security.JwtService;
import com.wasgames.wasgames.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationController(UserService userService, JwtService jwtService,
                                    AuthenticationManager authenticationManager,
                                    PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        // Проверка на существующего пользователя
        if (userService.findByUsernameOrEmail(userDTO.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        if (userService.findByUsernameOrEmail(userDTO.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(Role.USER);
        userService.save(user);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/register-admin")
    public ResponseEntity<String> registerAdmin(@RequestBody UserDTO userDTO) {
        // Проверка на существующего пользователя
        if (userService.findByUsernameOrEmail(userDTO.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        if (userService.findByUsernameOrEmail(userDTO.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(Role.ADMIN);
        userService.save(user);
        return ResponseEntity.ok("Admin registered");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        User user = userService.findByUsernameOrEmail(loginDTO.getLogin());
        
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), loginDTO.getPassword())
        );
        
        String token = jwtService.generateToken(user.getUsername());
        return ResponseEntity.ok(token);
    }
}
