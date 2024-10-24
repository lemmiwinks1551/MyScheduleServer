package com.lemmiwinks.myscheduleserver.controller.rest.dto;

import lombok.Data;

@Data
public class RegistrationRequestDto {
    private String username;
    private String email;
    private String password;
}
