package dev.kcterala.tinyurl.session;

import java.time.LocalDateTime;

public class Session {
    String sessionToken;
    LocalDateTime createdAt;
    LocalDateTime expiredAt;

    public Session(final String sessionToken, final LocalDateTime createdAt, final LocalDateTime expiredAt) {
        this.sessionToken = sessionToken;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(final String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(final LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }
}
