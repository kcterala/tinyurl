package dev.kcterala.tinyurl.repositories;

import dev.kcterala.tinyurl.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
    @Modifying
    @Query("delete from Session s where s.sessionToken = :sessionToken")
    void deleteBySessionToken(String sessionToken);

    @Query("select s from Session s where s.sessionToken = :sessionToken")
    Optional<Session> findBySessionToken(String sessionToken);
}
