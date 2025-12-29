package com.wasgames.wasgames.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.wasgames.wasgames.dto.LoginDTO;
import com.wasgames.wasgames.dto.UserDTO;
import com.wasgames.wasgames.model.User;
import com.wasgames.wasgames.repository.UserRepository;
import com.wasgames.wasgames.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User findByUsernameOrEmail(String login) {
        User user = userRepository.findByUsername(login).orElse(null);
        
        if (user == null) {
            user = userRepository.findByEmail(login).orElse(null);
        }
        
        return user;
    }
}
