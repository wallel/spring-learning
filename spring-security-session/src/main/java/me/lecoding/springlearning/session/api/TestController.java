package me.lecoding.springlearning.session.api;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @RequestMapping("/info")
    public Mono<String> hello(@AuthenticationPrincipal Authentication user) {
        return Mono.just("Hello " + user.getName() + "!!!");
    }

    @RequestMapping("/test")
    public Mono<String> test(WebSession session){
        return Mono.just("Hello session:" + session.getId());
    }
}
