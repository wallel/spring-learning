## what need to do?

* 定义securityContextRepository 为 WebSessionServerSecurityContextRepository,用来在授权成功之后把SecurityContext保存到session中
* 添加注释@EnableRedisWebSession,开启redissession的相关配置
* 添加redis配置
  spring.session.store-type=redis
  spring.session.redis.flush-mode=immediate
  spring.session.redis.namespace=spring:session
  
* 对于restful 应用,修改webSessionIdResolver为HeaderWebSessionIdResolver,在http的header中添加"SESSION"自定义头来传递session

* 测试,使用httpie:  

```
$: http http://localhost:8080/api/test/test --auth 140b48da-4aa3-4a0d-86e3-4a6a9772bd54:pass

HTTP/1.1 200 OK
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Content-Length: 50
Content-Type: text/plain;charset=UTF-8
Expires: 0
Pragma: no-cache
SESSION: 59bb73d1-691e-437d-8618-8242df0e4e01
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1 ; mode=block

Hello session:59bb73d1-691e-437d-8618-8242df0e4e01

$:http http://localhost:8080/api/test/info SESSION:59bb73d1-691e-437d-8618-8242df0e4e01

HTTP/1.1 200 OK
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Content-Length: 45
Content-Type: text/plain;charset=UTF-8
Expires: 0
Pragma: no-cache
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1 ; mode=block

Hello 140b48da-4aa3-4a0d-86e3-4a6a9772bd54!!!
```