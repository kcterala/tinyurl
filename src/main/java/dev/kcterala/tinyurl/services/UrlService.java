package dev.kcterala.tinyurl.services;

import dev.kcterala.tinyurl.Utils.LinkUtils;
import dev.kcterala.tinyurl.dtos.requests.CreateUrlRequest;
import dev.kcterala.tinyurl.dtos.responses.CreateUrlResponse;
import dev.kcterala.tinyurl.entities.Url;
import dev.kcterala.tinyurl.entities.User;
import dev.kcterala.tinyurl.exceptions.UrlNotFoundException;
import dev.kcterala.tinyurl.exceptions.UrlNotValidException;
import dev.kcterala.tinyurl.exceptions.UserNotFoundException;
import dev.kcterala.tinyurl.repositories.UrlRepository;
import dev.kcterala.tinyurl.repositories.UserRepository;
import dev.kcterala.tinyurl.session.UserInfo;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;

@Service
public class UrlService {

    private final UserRepository userRepository;
    private final UrlRepository urlRepository;
    private static final int MAX_URL_LENGTH = 6;

    public UrlService(final UserRepository userRepository,
                      final UrlRepository urlRepository) {
        this.userRepository = userRepository;
        this.urlRepository = urlRepository;
    }

    public CreateUrlResponse createRandomUrl(final UserInfo userInfo, final CreateUrlRequest createUrlRequest) throws UrlNotValidException, UserNotFoundException {
        final int userId = userInfo.userId;
        final Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }
        final User user = optionalUser.get();

        final String longUrl = createUrlRequest.longUrl();
        validateUrl(longUrl);


        Random random = new Random();
        final int randomNumber = random.nextInt((int) Math.pow(62, MAX_URL_LENGTH));
        final String shortCode = LinkUtils.generateRandomShortCode(randomNumber);
        final Url url = new Url(randomNumber, shortCode, longUrl, user);
        urlRepository.save(url);
        return new CreateUrlResponse(longUrl, shortCode);
    }

    private void validateUrl(final String longUrl) throws UrlNotValidException {
        try {
            new URL(longUrl).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new UrlNotValidException();
        }
    }

    public String getUrl(final String shortCode) throws UrlNotFoundException {
        int id = LinkUtils.decodeShortCode(shortCode);
        Optional<Url> optionalUrl = urlRepository.findById(id);

        if (optionalUrl.isEmpty()) {
            throw new UrlNotFoundException();
        }

        return optionalUrl.get().longUrl;
    }
}
