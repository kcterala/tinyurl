package dev.kcterala.tinyurl.services;

import dev.kcterala.tinyurl.dtos.requests.CreateUserRequest;
import dev.kcterala.tinyurl.entities.User;
import dev.kcterala.tinyurl.exceptions.UserAlreadyExistsException;
import dev.kcterala.tinyurl.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    public UserService(PasswordEncoder passwordEncoder,
                       UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void createUser(final CreateUserRequest createUserRequest) throws UserAlreadyExistsException {
        String email = createUserRequest.email();
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        String password = passwordEncoder.encode(createUserRequest.password());
        User user = new User(email, password);
        userRepository.save(user);
    }

}
