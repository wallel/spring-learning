package me.lecoding.springlearning.redis.exception;

import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class RestWebExceptionHandler implements WebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if(ex instanceof CustomException){
            CustomException exception = (CustomException)ex;
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(exception.getMsg().getBytes());
           return  exchange.getResponse().writeWith(Mono.just(buffer));
        }
        return Mono.error(ex);
    }
}
