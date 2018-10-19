package me.lecoding.springlearning.redis.auth;

import me.lecoding.springlearning.redis.data.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class AuthUserDetailsService implements ReactiveUserDetailsService {
    private UserService userService;

    @Autowired
    public AuthUserDetailsService(UserService userService){
        this.userService = userService;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userService.findById(username).map(AuthUser::new);
    }
}
