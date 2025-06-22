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

    @Test
    void testChatWithRag() {
        String chatId = UUID.randomUUID().toString();
        String message = "我单身，但是对恋爱感到焦虑，怎么办？";
        String answer = loveApp.doChatWithRag(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void testChatWithTools() {
        // 测试资源下载：图片下载
        testMessage("直接下载一张适合做手机壁纸的星空情侣图片为文件");

        // 测试终端操作：执行代码
        testMessage("执行 Python3 脚本来生成数据分析报告");

        // 测试文件操作：保存用户档案
        testMessage("保存我的恋爱档案为文件");

        // 测试 PDF 生成
        testMessage("生成一份‘七夕约会计划’PDF，包含餐厅预订、活动流程和礼物清单");
    }

    private void testMessage(String message) {
        String chatId = UUID.randomUUID().toString();
        String answer = loveApp.doChatWithTools(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void testChatWithMcp() {
        String chatId = UUID.randomUUID().toString();
        String msg = "请搜索一些漫画风格的NBA球星图片";
        String res = loveApp.doChatWithMcp(msg, chatId);
        Assertions.assertNotNull(res);
    }
}