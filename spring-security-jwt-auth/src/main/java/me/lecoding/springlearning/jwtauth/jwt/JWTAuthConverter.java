package me.lecoding.springlearning.jwtauth.jwt;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

public class JWTAuthConverter implements Function<ServerWebExchange,Mono<Authentication>> {

    @Override
    public Mono<Authentication> apply(ServerWebExchange serverWebExchange) {
        return Mono.justOrEmpty(serverWebExchange)
                .filter(w->w.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
                .map(JWTUtils::getAuthorizationPayload)
                .filter(Objects::nonNull)
                .filter(JWTUtils.matchBearerLength())
                .map(JWTUtils.getBearerValue())
                .filter(token ->!token.isEmpty())
                .map(JWTUtils.getInstance()::verifySignedJWT)
                .map(JWTUtils::getUsernamePasswordAuthenticationToken);
    }
}
