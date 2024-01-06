package dev.kcterala.tinyurl.controllers;

import dev.kcterala.tinyurl.dtos.requests.CreateUserRequest;
import dev.kcterala.tinyurl.exceptions.UserAlreadyExistsException;
import dev.kcterala.tinyurl.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> handleCreateUserRequest(@Valid @RequestBody CreateUserRequest createUserRequest) throws UserAlreadyExistsException {
        userService.createUser(createUserRequest);
        return ResponseEntity.ok().build();
    }
}
