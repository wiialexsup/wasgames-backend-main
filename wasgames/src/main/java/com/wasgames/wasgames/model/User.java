package com.wasgames.wasgames.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import com.wasgames.wasgames.model.Role;

import java.util.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean enabled;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;
}
