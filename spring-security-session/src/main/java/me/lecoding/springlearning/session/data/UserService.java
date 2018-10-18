package me.lecoding.springlearning.session.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    private ReactiveRedisOperations<String, User> redisOperations;

    public Mono<User> findById(String id){
        return redisOperations.opsForValue().get("user:"+id);
    }

    @Autowired
    public void setRedisOperations(ReactiveRedisOperations<String, User> redisOperations) {
        this.redisOperations = redisOperations;
    }
}
