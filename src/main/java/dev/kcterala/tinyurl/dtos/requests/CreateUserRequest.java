package dev.kcterala.tinyurl.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
    @NotBlank(message = "Email should not be blank")
    @Email
    String email,

    @NotBlank(message = "Password should not be blank")
    @Size(min = 4, max = 15, message = "Password should be min 4 to max 15 length")
    String password
) {
}
