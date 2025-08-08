package com.example.Employee_Directory.service.service_impl.redis;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class RedisCacheService {


    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private ObjectMapper objectMapper;

    private static final int DEFAULT_TTL = 3600;// 1 hour

    public <T> void setValue(String key, T object) {
        try (Jedis jedis = jedisPool.getResource()) {
            String jsonValue = objectMapper.writeValueAsString(object);
            jedis.set(key, jsonValue);
            jedis.expire(key,DEFAULT_TTL);
        }catch (Exception e) {
            log.error("Error caching object with key: {}", key, e);
        }

    }

    public <T> void setValueWithExpiry(String key, T object, int ttlSeconds) {
        try (Jedis jedis = jedisPool.getResource()) {
            String jsonValue = objectMapper.writeValueAsString(object);
            jedis.set(key, jsonValue);
            jedis.expire(key,ttlSeconds);
            log.debug("Cached object with key: {}", key);
        } catch (Exception e) {
            log.error("Error caching object with key: {}", key, e);
        }
    }

    public <T> Optional<T> getValue(String key, Class<T> relatedClass) {
        try (Jedis jedis = jedisPool.getResource()) {
            String jsonValue = jedis.get(key);
            if (jsonValue != null) {
                T object = objectMapper.readValue(jsonValue, relatedClass);
                log.debug("Retrieved object from cache with key: {}", key);
                return Optional.of(object);
            }
        } catch (Exception e) {
            log.error("Error retrieving object from cache with key: {}", key, e);
        }
        return Optional.empty();
    }

    public <T> Optional<List<T>> getValueList(String key, Class<T> relatedClass) {
        try (Jedis jedis = jedisPool.getResource()) {
            String jsonValue = jedis.get(key);
            if (jsonValue != null) {
                JavaType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, relatedClass);
                List<T> list = objectMapper.readValue(jsonValue, listType);
                log.debug("Retrieved list from cache with key: {}", key);
                return Optional.of(list);
            }
        } catch (Exception e) {
            log.error("Error retrieving list from cache with key: {}", key, e);
        }
        return Optional.empty();
    }

    public boolean exists(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.exists(key);
        } catch (Exception e) {
            log.error("Error checking if key exists: {}", key, e);
            return false;
        }
    }

    public void deleteKey(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(key);
            log.debug("Deleted from cache with key: {}", key);
        } catch (Exception e) {
            log.error("Error deleting from cache with key: {}", key, e);
        }
    }


}
