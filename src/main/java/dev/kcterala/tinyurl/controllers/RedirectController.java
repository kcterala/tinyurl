package dev.kcterala.tinyurl.controllers;

import dev.kcterala.tinyurl.exceptions.UrlNotFoundException;
import dev.kcterala.tinyurl.interceptors.Unauthenticated;
import dev.kcterala.tinyurl.services.UrlService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class RedirectController {

    private final UrlService urlService;

    public RedirectController(final UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{shortCode}")
    @Unauthenticated
    public ResponseEntity<Void> redirectToUrl(@PathVariable String shortCode) throws UrlNotFoundException {
        final String longUrl = urlService.getUrl(shortCode);
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .location(URI.create(longUrl)).build();
    }

    @GetMapping("/")
    public ResponseEntity<Void> redirectToSwagger() {
        String longUrl =  "/swagger-ui/index.html"; // Assuming this is the Swagger UI endpoint
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
                .location(URI.create(longUrl)).build();
    }

}
