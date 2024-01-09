package dev.kcterala.tinyurl.services;

import dev.kcterala.tinyurl.dtos.requests.CreateUserRequest;
import dev.kcterala.tinyurl.dtos.requests.LoginRequest;
import dev.kcterala.tinyurl.dtos.responses.CreateUserResponse;
import dev.kcterala.tinyurl.dtos.responses.LoginResponse;
import dev.kcterala.tinyurl.entities.User;
import dev.kcterala.tinyurl.exceptions.InvalidCredentialsException;
import dev.kcterala.tinyurl.exceptions.InvalidTokenException;
import dev.kcterala.tinyurl.exceptions.UserAlreadyExistsException;
import dev.kcterala.tinyurl.exceptions.UserNotFoundException;
import dev.kcterala.tinyurl.repositories.UserRepository;
import dev.kcterala.tinyurl.session.SessionTokenManager;
import dev.kcterala.tinyurl.session.UserInfo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final SessionTokenManager sessionTokenManager;
    public UserService(PasswordEncoder passwordEncoder,
                       UserRepository userRepository,
                       SessionTokenManager sessionTokenManager) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.sessionTokenManager = sessionTokenManager;
    }

    public CreateUserResponse createUser(final CreateUserRequest createUserRequest) throws UserAlreadyExistsException {
        String email = createUserRequest.email();
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        String password = passwordEncoder.encode(createUserRequest.password());
        User user = new User(email, password);
        User savedUser = userRepository.save(user);
        return new CreateUserResponse(savedUser);
    }

    public LoginResponse login(final LoginRequest loginRequest) throws InvalidCredentialsException, UserNotFoundException {
        String email = loginRequest.email();
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isEmpty()) {
            throw new InvalidCredentialsException();
        }

        if (!passwordEncoder.matches(loginRequest.password(), existingUser.get().getPassword())) {
            throw new InvalidCredentialsException();
        }

        return sessionTokenManager.createLoginSession(existingUser.get());

    }


    public void logout(final UserInfo userInfo, String token) throws InvalidTokenException, UserNotFoundException {
        String email = userInfo.email;
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isEmpty()) {
            throw new InvalidTokenException();
        }

        sessionTokenManager.logoutSession(email, token);
    }

}
