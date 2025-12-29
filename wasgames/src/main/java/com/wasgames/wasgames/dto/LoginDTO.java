package com.wasgames.wasgames.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginDTO {
    private String login;
    private String password;
}
