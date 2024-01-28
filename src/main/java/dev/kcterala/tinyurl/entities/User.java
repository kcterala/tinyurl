package dev.kcterala.tinyurl.entities;

import dev.kcterala.tinyurl.session.Session;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column
    private List<Session> sessions;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Url> urls;

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(final LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getPassword() {
        return password;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(final List<Session> sessions) {
        this.sessions = sessions;
    }

    public User(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public User() {}

    public void addSession(final String sessionToken) {
        if (sessions == null) {
            sessions = new ArrayList<>();
        }

        sessions = sessions.stream().filter(s -> s.getExpiredAt().isAfter(LocalDateTime.now())).collect(Collectors.toList());

        Session session = new Session(sessionToken, LocalDateTime.now(), LocalDateTime.now().plusMinutes(2));
        sessions.add(session);
    }

    public void removeSession(final String sessionToken) {
        this.sessions.removeIf(session -> session.getSessionToken().equals(sessionToken));
    }

}
