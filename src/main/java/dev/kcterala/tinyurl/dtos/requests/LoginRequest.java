package dev.kcterala.tinyurl.dtos.requests;

public record LoginRequest(
        String email,
        String password
) {
}
