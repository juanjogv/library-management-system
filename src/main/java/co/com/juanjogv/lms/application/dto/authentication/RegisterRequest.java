package co.com.juanjogv.lms.application.dto.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank String name,
        @Email String email,
        @NotBlank String password
) {}
