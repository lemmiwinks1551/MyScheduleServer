package com.lemmiwinks.myscheduleserver.entity.dto;

import lombok.Data;

@Data
public class AuthenticationRequestDto {
    private String username;
    private String password;
}
