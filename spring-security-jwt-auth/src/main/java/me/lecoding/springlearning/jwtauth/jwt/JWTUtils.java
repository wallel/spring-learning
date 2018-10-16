package me.lecoding.springlearning.jwtauth.jwt;

import com.nimbusds.jose.*;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import me.lecoding.springlearning.jwtauth.exception.CustomException;
import me.lecoding.springlearning.jwtauth.exception.ErrorInfoCode;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JWTUtils implements InitializingBean {
    private static final String BEARER = "Bearer ";
    private static final long DEFAULT_EXPIRE_TIME = 30 * 60 * 1000;
    private static final String CLAIM_AUTH = "auths";

    private JWSSigner jwtSinger;
    private JWSVerifier jwtVerifier;

    public String generateToken(String username,Collection<? extends GrantedAuthority> authorities){
        JWTClaimsSet claimSet = new JWTClaimsSet.Builder()
                .subject(username)
                .expirationTime(new Date(new Date().getTime() + DEFAULT_EXPIRE_TIME))
                .claim(CLAIM_AUTH,authorities.parallelStream().map(GrantedAuthority.class::cast).map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                .build();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimSet);
        try {
            signedJWT.sign(jwtSinger);
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return signedJWT.serialize();
    }

    public static String getAuthorizationPayload(ServerWebExchange serverWebExchange) {
        return serverWebExchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);
    }

    public static Predicate<String> matchBearerLength(){
        return authValue -> authValue.length() > BEARER.length();
    }

    public static Function<String,String> getBearerValue(){
        return authValue -> authValue.substring(BEARER.length(), authValue.length());
    }

    public Mono<SignedJWT> verifySignedJWT(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            boolean valid = signedJWT.verify(jwtVerifier);
            if(!valid){
                throw new CustomException(ErrorInfoCode.TOKEN_CHECK_ERROR);
            }
            return Mono.just(signedJWT);
        }catch (ParseException | JOSEException e) {
            return Mono.empty();
        }

    }

    public static Authentication getUsernamePasswordAuthenticationToken(Mono<SignedJWT> signedJWTMono) {
        SignedJWT signedJWT = signedJWTMono.block();
        String subject;
        String auths;
        try {
            subject = signedJWT.getJWTClaimsSet().getSubject();
            auths = (String) signedJWT.getJWTClaimsSet().getClaim("auths");
        } catch (ParseException e) {
            return null;
        }

        List<GrantedAuthority> authorities = Stream.of(auths.split(","))
                .filter(Strings::isNotEmpty)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(subject, null, authorities);
    }

    @Autowired
    public void setJwtSinger(JWSSigner jwtSinger) {
        this.jwtSinger = jwtSinger;
    }

    @Autowired
    public void setJwtVerifier(JWSVerifier jwtVerifier) {
        this.jwtVerifier = jwtVerifier;
    }

    private static JWTUtils INSTANCE;
    @Override
    public void afterPropertiesSet() throws Exception {
        INSTANCE = this;
    }
    
    public static JWTUtils getInstance(){
        return INSTANCE;
    }
}
