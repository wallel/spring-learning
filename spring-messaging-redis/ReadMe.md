## redis pub/sub使用

* 发布消息调用redisTemplte.convertAndSend()方法

* 处理消息

    1. 创建bean,RedisMessageListenerContainer用来集中管理所有的订阅
    2. 每个订阅创建一个MessageListenerAdapter,用来处理对应的消息
    3. 消息处理类可以是任意的类,默认的消息处理接口是handleMessage,当然也可以使用任意的函数
    
        * new MessageListenerAdapter(消息处理类实例) =>调用默认的方法 handleMessage
        * new MessageListenerAdapter(消息处理类实例,"指定处理消息的方法") 

* 测试:
```
λ http http://localhost:8080/api/chat/chat?msg=thisisamessage --auth 8e96f68e-fa69-43e9-bfb7-48ba25df5b9f:pass
HTTP/1.1 200 OK
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Expires: 0
Pragma: no-cache
SESSION: afb47301-1de8-475b-a0b1-bc5802479ed1
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1 ; mode=block
content-length: 0

λ http http://localhost:8080/api/chat/chat?msg=messageagain SESSION:afb47301-1de8-475b-a0b1-bc5802479ed1
HTTP/1.1 200 OK
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Expires: 0
Pragma: no-cache
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1 ; mode=block
content-length: 0

服务器输出日志:
2018-10-19 11:20:44 [container-2] INFO (ChatHandler.java:10) - receive a message:jack:thisisamessage
2018-10-19 11:21:50 [container-3] INFO (ChatHandler.java:10) - receive a message:jack:messageagain
```