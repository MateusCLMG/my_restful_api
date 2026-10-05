package com.project.my_api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationData(
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O formato do e-mail é inválido.")
    String email,

    @NotBlank(message = "A senha é obrigatória.")
    String password
) {}
