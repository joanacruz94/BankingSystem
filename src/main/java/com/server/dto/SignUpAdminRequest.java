package com.server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignUpAdminRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
