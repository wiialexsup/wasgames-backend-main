package com.wasgames.wasgames;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wasgames.wasgames.config.TestConfig;
import com.wasgames.wasgames.model.Role;
import com.wasgames.wasgames.model.User;
import com.wasgames.wasgames.repository.UserRepository;
import com.wasgames.wasgames.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
@Import(TestConfig.class) // ДОБАВЬТЕ ЭТУ СТРОКУ
public abstract class BaseIntegrationTest {

    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected JwtService jwtService;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUpMockMvc() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    protected String createAdminToken() {
        User admin = userRepository.findByUsername("testadmin")
                .orElseGet(() -> {
                    User newAdmin = new User();
                    newAdmin.setUsername("testadmin");
                    newAdmin.setEmail("testadmin@test.com");
                    newAdmin.setPassword(passwordEncoder.encode("admin123"));
                    newAdmin.setRole(Role.ADMIN);
                    return userRepository.save(newAdmin);
                });
        
        return jwtService.generateToken(admin.getUsername());
    }

    protected String createUserToken() {
        User user = userRepository.findByUsername("testuser")
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setUsername("testuser");
                    newUser.setEmail("testuser@test.com");
                    newUser.setPassword(passwordEncoder.encode("user123"));
                    newUser.setRole(Role.USER);
                    return userRepository.save(newUser);
                });
        
        return jwtService.generateToken(user.getUsername());
    }
}