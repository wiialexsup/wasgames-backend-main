package com.wasgames.wasgames.dto;

import com.wasgames.wasgames.model.Role;
import com.wasgames.wasgames.model.User;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Role role;

    public UserDTO(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
    }
}
