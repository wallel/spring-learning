package me.lecoding.springlearning.redis.config;

import me.lecoding.springlearning.redis.chat.ChatHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class MessageConfig {
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory redisConnectionFactory){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(listenerAdapter(handler()),new PatternTopic("chat"));
        return container;
    }
    @Bean
    StringRedisTemplate template(RedisConnectionFactory redisConnectionFactory){
        return new StringRedisTemplate(redisConnectionFactory);
    }
    @Bean
    MessageListenerAdapter listenerAdapter(ChatHandler handler){
        return new MessageListenerAdapter(handler);
    }
    @Bean
    ChatHandler handler(){
        return new ChatHandler();
    }
}
