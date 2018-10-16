package me.lecoding.springlearning.jwtauth.config;


import me.lecoding.springlearning.jwtauth.exception.CustomException;
import me.lecoding.springlearning.jwtauth.exception.ErrorInfoCode;
import me.lecoding.springlearning.jwtauth.jwt.JWTAuthConverter;
import me.lecoding.springlearning.jwtauth.jwt.JWTAuthSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import reactor.core.publisher.Mono;

@Configuration
public class WebSecurityConfiguration {

    private ReactiveUserDetailsService authUserDetailsService;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Bean
    public SecurityWebFilterChain sprintSecurityFilterChain(ServerHttpSecurity http){
        AuthenticationWebFilter auth = new AuthenticationWebFilter(new UserDetailsRepositoryReactiveAuthenticationManager(authUserDetailsService));
        auth.setAuthenticationSuccessHandler(authSuccessHandler());
        return http
                .csrf().disable()
                .addFilterAt(auth,SecurityWebFiltersOrder.FIRST)
                .addFilterAt(jwtWebFilter(),SecurityWebFiltersOrder.HTTP_BASIC)
                .authorizeExchange()
                .matchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .pathMatchers("/api/test/login").permitAll()
                .anyExchange().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((exchange,exception)->Mono.error(new CustomException(ErrorInfoCode.TOKEN_CHECK_ERROR)))
                .accessDeniedHandler((exchange,exception)->Mono.error(new CustomException(ErrorInfoCode.TOKEN_CHECK_ERROR)))
                .and().build();
    }

    @Bean
    public AuthenticationWebFilter jwtWebFilter(){
        AuthenticationWebFilter webFilter = new AuthenticationWebFilter(Mono::justOrEmpty);
        webFilter.setAuthenticationConverter(new JWTAuthConverter());
        webFilter.setAuthenticationSuccessHandler(authSuccessHandler());
        return webFilter;
    }

    @Autowired
    public void setAuthUserDetailsService(ReactiveUserDetailsService authUserDetailsService) {
        this.authUserDetailsService = authUserDetailsService;
    }
    @Bean
    public ServerAuthenticationSuccessHandler authSuccessHandler(){
        return new JWTAuthSuccessHandler();
    }
}
