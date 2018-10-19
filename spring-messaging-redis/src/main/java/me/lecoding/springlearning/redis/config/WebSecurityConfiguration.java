package me.lecoding.springlearning.redis.config;


import me.lecoding.springlearning.redis.exception.CustomException;
import me.lecoding.springlearning.redis.exception.ErrorInfoCode;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;
import org.springframework.web.server.session.HeaderWebSessionIdResolver;
import org.springframework.web.server.session.WebSessionIdResolver;
import reactor.core.publisher.Mono;

@Configuration
@EnableRedisWebSession
public class WebSecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Bean
    public SecurityWebFilterChain sprintSecurityFilterChain(ServerHttpSecurity http){

        return http
                .csrf().disable()
                .authorizeExchange()
                .matchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .pathMatchers("/api/test/login").permitAll()
                .anyExchange().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((exchange,exception)->Mono.error(new CustomException(ErrorInfoCode.NEED_AUTHENTICATION)))
                .accessDeniedHandler((exchange,exception)->Mono.error(new CustomException(ErrorInfoCode.NEED_AUTHENTICATION)))
                .and()
                .httpBasic().securityContextRepository(securityContextRepository()).and().build();
    }

    @Bean
    ServerSecurityContextRepository securityContextRepository(){
        return new WebSessionServerSecurityContextRepository();
    }
    @Bean
    WebSessionIdResolver webSessionIdResolver(){
        return new HeaderWebSessionIdResolver();
    }
}
