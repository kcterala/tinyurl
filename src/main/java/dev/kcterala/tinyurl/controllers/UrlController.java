package dev.kcterala.tinyurl.controllers;

import dev.kcterala.tinyurl.dtos.requests.CreateUrlRequest;
import dev.kcterala.tinyurl.dtos.responses.CreateUrlResponse;
import dev.kcterala.tinyurl.exceptions.UrlNotValidException;
import dev.kcterala.tinyurl.exceptions.UserNotFoundException;
import dev.kcterala.tinyurl.services.UrlService;
import dev.kcterala.tinyurl.session.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/urls")
public class UrlController {

    private final UrlService urlService;

    public UrlController(final UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    public ResponseEntity<CreateUrlResponse> createRandomUrl(HttpServletRequest request,
                                                             @RequestBody CreateUrlRequest createUrlRequest) throws UrlNotValidException, UserNotFoundException {
        final UserInfo userInfo = (UserInfo) request.getAttribute("user");
        final CreateUrlResponse createUrlResponse = urlService.createRandomUrl(userInfo, createUrlRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createUrlResponse);
    }

    @PostMapping("/{shortCode}")
    public ResponseEntity<CreateUrlResponse> createSpecificShortUrl(HttpServletRequest request,
                                                                    @RequestBody CreateUrlRequest createUrlRequest,
                                                                    @PathVariable String shortCode) throws UrlNotValidException, UserNotFoundException {
        final UserInfo userInfo = (UserInfo) request.getAttribute("user");
        final CreateUrlResponse createUrlResponse = urlService.createSpecificUrl(userInfo, createUrlRequest, shortCode);
        return ResponseEntity.status(HttpStatus.CREATED).body(createUrlResponse);
    }

}
