package com.example.Employee_Directory.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
public class RedisConnectivityTest {
    @Autowired
    private JedisPool jedisPool;

    @Test
    void testRedisConnection() {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set("test:key", "Hello Redis!");
            jedis.expire("test:key", 180);
            String value = jedis.get("test:key");

            assertEquals("Hello Redis!", value);
            log.info("Redis is connected successfully. Value: " + value);
            log.info("Current Redis DB: " + jedis.getDB());

        } catch (Exception e) {
            log.error("Redis connection failed.", e.getMessage());

        }
    }
}
