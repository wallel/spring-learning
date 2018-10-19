package me.lecoding.springlearning.redis.api;

import me.lecoding.springlearning.redis.auth.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private StringRedisTemplate template;

    @RequestMapping("/chat")
    public Mono<Void> hello(@AuthenticationPrincipal Authentication user,@RequestParam String msg) {
        return Mono.just(user)
                .map(Authentication::getPrincipal)
                .cast(AuthUser.class)
                .map(au->au.getUser().getNickName())
                .map(name->name+":"+msg)
                .doOnNext(message->template.convertAndSend("chat",message))
                .then();
    }
    @Autowired
    public void setTemplate(StringRedisTemplate template) {
        this.template = template;
    }
}
