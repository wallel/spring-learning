package me.lecoding.springlearning.jwtauth


import me.lecoding.springlearning.jwtauth.auth.AuthUser
import me.lecoding.springlearning.jwtauth.auth.Role
import me.lecoding.springlearning.jwtauth.data.User
import spock.lang.Specification

class AuthUserSpec extends Specification {

    def "测试获取权限列表"(){
        given:"AuthUser"
        def user = new AuthUser(User.builder()
                .id("user")
                .email("user@example.com")
                .nickName("user")
                .mobile("1111111")
                .password("pass")
                .roles(roles)
                .build())
        when:"获取权限"
        def auths = user.getAuthorities()

        then:"检查结果"
        auths.size() == output.size()
        auths.every{auth->output.contains(auth.authority)}

        where:"权限"
        roles                                   || output
        [Role.USER, Role.ADMIN]                 || ["ROLE_USER", "ROLE_ADMIN"]
        [Role.USER, Role.CURATOR]               || ["ROLE_USER", "ROLE_CURATOR"]
        [Role.USER, Role.CURATOR,Role.ADMIN]    || ["ROLE_USER", "ROLE_CURATOR","ROLE_ADMIN"]
        [Role.USER]                             || ["ROLE_USER"]
    }
}
