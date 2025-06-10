package kz.balaguide.auth_module.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void saveToken(String phoneNumber, String token, long ttlMillis) {
        redisTemplate.opsForValue().set(phoneNumber, token, ttlMillis, TimeUnit.MILLISECONDS);
    }

    public String getToken(String phoneNumber) {
        Object token = redisTemplate.opsForValue().get(phoneNumber);
        return token != null ? token.toString() : null;
    }

    public void deleteToken(String phoneNumber) {
        redisTemplate.delete(phoneNumber);
    }

    public boolean isTokenValid(String phoneNumber, String token) {
        String stored = getToken(phoneNumber);
        return stored != null && stored.equals(token);
    }
}

