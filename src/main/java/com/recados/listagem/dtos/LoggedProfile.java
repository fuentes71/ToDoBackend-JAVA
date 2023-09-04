package com.recados.listagem.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoggedProfile(@NotBlank
                            @Email
                            String email,
                            @NotBlank
                            String password) {
}
