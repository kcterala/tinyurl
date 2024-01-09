package dev.kcterala.tinyurl.session;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisSessionService {

    private final RedisTemplate redisTemplate;
    public RedisSessionService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private static final String USER_PREFIX = "session:";

    public void storeSessionToken(String token, UserInfo userInfo) {
        String redisKey = USER_PREFIX + token;
        redisTemplate.opsForValue().set(redisKey, userInfo);
        redisTemplate.expire(redisKey, 3, TimeUnit.MINUTES);
    }

    public UserInfo getSessionToken(String token) {
        String redisKey = USER_PREFIX + token;
        Object userInfoObject = redisTemplate.opsForValue().get(redisKey);
        if (userInfoObject == null) {
            return null;
        }
        return (UserInfo) userInfoObject;
    }

    public void removeSessionToken(final String token) {
        String redisKey = USER_PREFIX + token;
        redisTemplate.opsForValue().getAndDelete(redisKey);
    }
}
