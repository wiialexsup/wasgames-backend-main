package com.wasgames.wasgames.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wasgames.wasgames.dto.LoginDTO;
import com.wasgames.wasgames.dto.UserDTO;
import com.wasgames.wasgames.model.User;
import com.wasgames.wasgames.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserDTO> users = userService.findAll()
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }
}
