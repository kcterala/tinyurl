package dev.kcterala.tinyurl.session;

import dev.kcterala.tinyurl.entities.User;

import java.io.Serializable;

public class UserInfo implements Serializable {
    public int userId;
    public String email;

    public UserInfo(final int userId, final String email) {
        this.userId = userId;
        this.email = email;
    }

    public static UserInfo from(User user) {
        return new UserInfo(user.getId(), user.getEmail());
    }
}
