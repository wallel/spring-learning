## what need to do?

* implements UserDetails and ReactiveUserDetailsService
* add a bean of PasswordEncoder
* config SecurityWebFilterChain
    
    1. config a webFilter to handle jwt authentication
        * ReactiveAuthenticationManager ->just return authentication
        * AuthenticationConverter ->check Authorization header,then get username and authorities
        *  ServerAuthenticationSuccessHandler -> add Authorization header to response
        
    2. Config exceptionHandling
        * ServerAuthenticationEntryPoint ->handle when need authentication
        * ServerAccessDeniedHandler->handle when authenticate error
    
*  see "/api/test/login", an example of login handler

* add a new WebExceptionHandler with Order(-2) to handle exceptions  your self
    
    * auto config WebExceptionHandler    
        1. order=-1, DefaultErrorWebExceptionHandler
        2. order=0,  WebFluxResponseStatusExceptionHandler

