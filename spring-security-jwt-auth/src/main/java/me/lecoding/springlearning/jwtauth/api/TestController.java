package me.lecoding.springlearning.jwtauth.api;

import me.lecoding.springlearning.jwtauth.exception.CustomException;
import me.lecoding.springlearning.jwtauth.exception.ErrorInfoCode;
import me.lecoding.springlearning.jwtauth.jwt.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/test")
public class TestController {
    private PasswordEncoder passwordEncoder;
    private ReactiveUserDetailsService authUserDetailsService;
    private JWTUtils jwtUtils;

    @RequestMapping("/login")
    public Mono<String> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        return authUserDetailsService
                .findByUsername(username)
                .filter(u->passwordEncoder.matches(password,u.getPassword()))
                .switchIfEmpty(Mono.error(new CustomException(ErrorInfoCode.PASSWORD_ERROR)))
                .map(u->jwtUtils.generateToken(u.getUsername(),u.getAuthorities()));
    }

    @RequestMapping("/info")
    public Mono<String> hello(@AuthenticationPrincipal Authentication user) {
        return Mono.just("Hello " + user.getName() + "!!!");
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setAuthUserDetailsService(ReactiveUserDetailsService authUserDetailsService) {
        this.authUserDetailsService = authUserDetailsService;
    }

    @Autowired
    public void setJwtUtils(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }
}
