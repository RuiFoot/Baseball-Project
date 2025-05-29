package io.github.ruifoot.infrastructure.cache.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    /**
     * Store a key-value pair in Redis with expiration time
     * @param key Key
     * @param value Value
     * @param timeout Expiration time
     * @param unit Time unit
     */
    public void setValues(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * Get a value from Redis by key
     * @param key Key
     * @return Value
     */
    public String getValues(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * Delete a key-value pair from Redis
     * @param key Key
     */
    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }

    /**
     * Check if a key exists in Redis
     * @param key Key
     * @return True if key exists, false otherwise
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
}
