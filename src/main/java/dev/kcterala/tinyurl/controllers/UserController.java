package dev.kcterala.tinyurl.controllers;

import dev.kcterala.tinyurl.dtos.requests.CreateUserRequest;
import dev.kcterala.tinyurl.dtos.requests.LoginRequest;
import dev.kcterala.tinyurl.dtos.responses.CreateUserResponse;
import dev.kcterala.tinyurl.dtos.responses.LoginResponse;
import dev.kcterala.tinyurl.exceptions.InvalidCredentialsException;
import dev.kcterala.tinyurl.exceptions.InvalidTokenException;
import dev.kcterala.tinyurl.exceptions.UserAlreadyExistsException;
import dev.kcterala.tinyurl.exceptions.UserNotFoundException;
import dev.kcterala.tinyurl.interceptors.Unauthenticated;
import dev.kcterala.tinyurl.services.UserService;
import dev.kcterala.tinyurl.session.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    @Unauthenticated
    public ResponseEntity<CreateUserResponse> handleCreateUserRequest(@Valid @RequestBody CreateUserRequest createUserRequest) throws UserAlreadyExistsException {
        CreateUserResponse createUserResponse = userService.createUser(createUserRequest);
        return new ResponseEntity<>(createUserResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Unauthenticated
    public ResponseEntity<LoginResponse> handleLogin(@RequestBody LoginRequest loginRequest) throws InvalidCredentialsException, UserNotFoundException {
        final LoginResponse loginResponse = userService.login(loginRequest);
        return ResponseEntity.ok().body(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> handleLogout(HttpServletRequest request) throws InvalidTokenException, UserNotFoundException {
        UserInfo userInfo = (UserInfo) request.getAttribute("user");
        String token = request.getHeader("Authorization").substring(7);
        userService.logout(userInfo, token);
        return ResponseEntity.ok().build();
    }
}
