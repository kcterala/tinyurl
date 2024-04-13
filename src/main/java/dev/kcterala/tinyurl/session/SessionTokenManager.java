package dev.kcterala.tinyurl.session;

import dev.kcterala.tinyurl.dtos.responses.LoginResponse;
import dev.kcterala.tinyurl.entities.Session;
import dev.kcterala.tinyurl.entities.User;
import dev.kcterala.tinyurl.exceptions.InvalidCredentialsException;
import dev.kcterala.tinyurl.exceptions.InvalidTokenException;
import dev.kcterala.tinyurl.exceptions.UserNotFoundException;
import dev.kcterala.tinyurl.repositories.SessionRepository;
import dev.kcterala.tinyurl.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@Service
public class SessionTokenManager {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    public SessionTokenManager(final UserRepository userRepository,
                               final SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }



    public LoginResponse createLoginSession(final User user) throws UserNotFoundException {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        String sessionToken = base64Encoder.encodeToString(randomBytes);
        Session session = new Session(sessionToken, user.getId());
        sessionRepository.save(session);
        return new LoginResponse(sessionToken);
    }

    public void removeSession(final String sessionToken) {
        sessionRepository.deleteBySessionToken(sessionToken);
    }

    public UserInfo getUserInfo(final String sessionToken) throws InvalidTokenException {
        Optional<Session> optionalSession = sessionRepository.findBySessionToken(sessionToken);
        if (optionalSession.isEmpty()) {
            throw new InvalidTokenException();
        }

        Session session = optionalSession.get();

        if (session.expirationTime.isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException();
        }


        Optional<User> optionalUser = userRepository.findById(session.userId);
        if (optionalUser.isEmpty()) {
            throw new InvalidTokenException();
        }

        return UserInfo.from(optionalUser.get());

    }

}
