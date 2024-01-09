package dev.kcterala.tinyurl.session;

import dev.kcterala.tinyurl.dtos.responses.LoginResponse;
import dev.kcterala.tinyurl.entities.User;
import dev.kcterala.tinyurl.exceptions.UserNotFoundException;
import dev.kcterala.tinyurl.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

@Service
public class SessionTokenManager {
    private final RedisSessionService sessionService;
    private final UserRepository userRepository;
    public SessionTokenManager(RedisSessionService redisSessionService,
                               UserRepository userRepository) {
        this.sessionService = redisSessionService;
        this.userRepository = userRepository;
    }

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    public LoginResponse createLoginSession(final User user) throws UserNotFoundException {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        String sessionToken = base64Encoder.encodeToString(randomBytes);
        UserInfo userInfo = UserInfo.from(user);

        addSession(sessionToken, user.getEmail());
        sessionService.storeSessionToken(sessionToken, userInfo);
        return new LoginResponse(sessionToken);
    }

    public void logoutSession(final String email, final String sessionToken) throws UserNotFoundException {
        removeSession(sessionToken, email);
        sessionService.removeSessionToken(sessionToken);
    }

    public void addSession(final String sessionToken, final String email) throws UserNotFoundException {
        User user = getUser(email);
        user.addSession(sessionToken);
        userRepository.save(user);
    }

    public void removeSession(final String sessionToken, final String email) throws UserNotFoundException {
        User user = getUser(email);
        user.removeSession(sessionToken);
        userRepository.save(user);
    }

    public User getUser(String email) throws UserNotFoundException{
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }
        return optionalUser.get();
    }
}
