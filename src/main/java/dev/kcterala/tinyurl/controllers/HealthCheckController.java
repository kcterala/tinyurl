package dev.kcterala.tinyurl.controllers;

import dev.kcterala.tinyurl.interceptors.Unauthenticated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/healthcheck")
public class HealthCheckController {
    @GetMapping
    @Unauthenticated
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Hi, How are you feeling today?");
    }
}
