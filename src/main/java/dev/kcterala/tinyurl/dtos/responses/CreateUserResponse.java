package dev.kcterala.tinyurl.dtos.responses;

import dev.kcterala.tinyurl.entities.User;

import java.time.LocalDateTime;

public record CreateUserResponse (
        int id,
        String username,
        LocalDateTime lastUpdatedTime
){
    public CreateUserResponse(User user) {
        this(user.getId(), user.getEmail(), user.getUpdatedAt());
    }

    public CreateUserResponse(final int id, final String username, final LocalDateTime lastUpdatedTime) {
        this.id = id;
        this.username = username;
        this.lastUpdatedTime = lastUpdatedTime;
    }
}
