package dev.kcterala.tinyurl.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Url {

    @Id
    public int id;

    @Column
    public String shortUrl;

    @Column
    public String longUrl;

    @ManyToOne
    public User user;

    public Url(final int id, final String shortUrl, final String longUrl, final User user) {
        this.id = id;
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        this.user = user;
    }

    public Url() {
    }
}
