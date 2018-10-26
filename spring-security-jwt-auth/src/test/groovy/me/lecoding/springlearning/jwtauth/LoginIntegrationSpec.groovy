package me.lecoding.springlearning.jwtauth


import me.lecoding.springlearning.jwtauth.auth.AuthUser
import me.lecoding.springlearning.jwtauth.data.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import spock.lang.Specification
import spock.lang.Stepwise
import spock.mock.DetachedMockFactory

@SpringBootTest(properties = "spring.main.web-application-type=reactive")
@Stepwise
class LoginIntegrationSpec extends Specification{
    @Autowired
    ReactiveUserDetailsService authUserDetailsService
    @Autowired
    PasswordEncoder passwordEncoder

    @Autowired
    ApplicationContext context

    def "测试依赖注入"(){
        expect: "passwordEncoder 不为空"
        context != null
        context.containsBean("passwordEncoder")
        context.containsBean("authUserDetailsService")
        passwordEncoder != null
    }

    def "测试login登录获取token"(){
        given: "测试用户"
        authUserDetailsService.findByUsername("user") >> Mono.just(new AuthUser(
                User.builder()
                .id("user")
                .email("user@example.com")
                .nickName("user")
                .mobile("1111111")
                .password(passwordEncoder.encode("pass"))
                .build()))
        and:"client"
        def client = WebTestClient.bindToApplicationContext(context).build()

        expect: "请求登录"
        client != null
        client.get().uri("/api/test/login?username=user&password=pass")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith{r-> println(r)}
    }

@TestConfiguration
static class MockConfig{
    def detachedMockFactory = new DetachedMockFactory()
    @Bean
    ReactiveUserDetailsService authUserDetailsService(){
        return detachedMockFactory.Stub(ReactiveUserDetailsService)
    }
}
}
