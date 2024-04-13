package dev.kcterala.tinyurl.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column
    public String sessionToken;

    @Column
    public int userId;

    @CreationTimestamp
    public LocalDateTime creationTime;

    @Column
    public LocalDateTime expirationTime;


    public Session() {
    }

    public Session(final String sessionToken, final int userId) {
        this.sessionToken = sessionToken;
        this.userId = userId;
        this.expirationTime = LocalDateTime.now().plusHours(2);
    }

}
