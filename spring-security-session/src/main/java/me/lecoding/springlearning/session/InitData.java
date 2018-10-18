package me.lecoding.springlearning.session;

import me.lecoding.springlearning.session.data.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class InitData implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(InitData.class);

    private ReactiveRedisOperations<String, User> redisOperations;
    private PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args){
        User u = User.builder()
                .id(UUID.randomUUID().toString())
                .email("user@example.com")
                .nickName("user")
                .mobile("1111111")
                .password(passwordEncoder.encode("pass"))
                .build();
        logger.info("create new user: id-{}",u.getId());
        redisOperations.opsForValue().set("user:"+u.getId(),u)
                .subscribe();

    }
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setRedisOperations(ReactiveRedisOperations<String, User> redisOperations) {
        this.redisOperations = redisOperations;
    }
}
