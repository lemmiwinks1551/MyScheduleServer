package com.lemmiwinks.myscheduleserver.controller.rest;

import lombok.Data;

@Data
public class AuthenticationRequestDTO {
    private String username;
    private String password;
}
