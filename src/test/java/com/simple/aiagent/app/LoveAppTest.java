package com.simple.aiagent.app;

import cn.hutool.core.lang.UUID;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoveAppTest {
    @Resource
    private LoveApp loveApp;

    @Test
    void testChat() {
        String chatId = UUID.randomUUID().toString();
        // 第一轮会话
        String msg = "你好，我是simple";
        String answer = loveApp.doChat(msg, chatId);
        Assertions.assertNotNull(answer);
        // 第二轮会话
        msg = "请问程序员如何寻找另一半呢？";
        answer = loveApp.doChat(msg, chatId);
        Assertions.assertNotNull(answer);
        // 第三轮会话
        msg = "我叫什么名字？我突然忘记了，请帮我回忆一下";
        answer = loveApp.doChat(msg, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void testChatWithReport() {
        String chatId = UUID.randomUUID().toString();
        String message = "你好，我是simple，如何让另一半对我产生好感呢？";
        LoveApp.LoveReport loveReport = loveApp.doChatWithReport(message, chatId);
        Assertions.assertNotNull(loveReport);
    }

}