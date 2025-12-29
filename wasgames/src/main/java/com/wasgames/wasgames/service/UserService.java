package com.wasgames.wasgames.service;

import java.util.List;
import java.util.Optional;

import com.wasgames.wasgames.dto.LoginDTO;
import com.wasgames.wasgames.dto.UserDTO;
import com.wasgames.wasgames.model.User;

public interface UserService {

    User save(User user);

    User findByUsernameOrEmail(String login);

    Optional<User> findById(Long id);

    List<User> findAll();

    void deleteById(Long id);
}
