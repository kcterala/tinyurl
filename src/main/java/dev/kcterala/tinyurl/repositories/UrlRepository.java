package dev.kcterala.tinyurl.repositories;

import dev.kcterala.tinyurl.entities.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, Integer> {
}
