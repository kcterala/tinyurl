package dev.kcterala.tinyurl.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping("/api/healthcheck")
public class HealthCheckController {
    @GetMapping
    public ResponseEntity<String> healthCheck() {
        System.out.println("Build should fail")
        return ResponseEntity.ok("Hi, How are you feeling today?");
    }
}
