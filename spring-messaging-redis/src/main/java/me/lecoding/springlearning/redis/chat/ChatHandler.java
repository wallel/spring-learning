package me.lecoding.springlearning.redis.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatHandler {
    private static Logger logger = LoggerFactory.getLogger(ChatHandler.class);

    public void handleMessage(String message){
        logger.info("receive a message:{}",message);
    }
}
