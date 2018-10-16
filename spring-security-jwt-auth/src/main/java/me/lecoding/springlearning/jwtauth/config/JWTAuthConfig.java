package me.lecoding.springlearning.jwtauth.config;

import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JWTAuthConfig {
    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public JWSSigner jwtSinger(){
        try {
            return new MACSigner(secret);
        }catch (Exception e){
            throw new RuntimeException("create signer error",e);
        }
    }

    @Bean
    public JWSVerifier jwtVerifier(){
        try {
            return new MACVerifier(secret);
        }catch (Exception e){
            throw new RuntimeException("create signer error",e);
        }
    }

}
