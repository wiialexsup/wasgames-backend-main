package com.wasgames.wasgames.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wasgames.wasgames.model.User;
import com.wasgames.wasgames.repository.UserRepository;
import com.wasgames.wasgames.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void findByUsernameOrEmail_username() {
        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.of(new User()));

        User user = userService.findByUsernameOrEmail("john");

        assertNotNull(user);
    }

    @Test
    void findByUsernameOrEmail_email() {
        when(userRepository.findByUsername("mail@test.com"))
                .thenReturn(Optional.empty());
        when(userRepository.findByEmail("mail@test.com"))
                .thenReturn(Optional.of(new User()));

        User user = userService.findByUsernameOrEmail("mail@test.com");

        assertNotNull(user);
    }
}
