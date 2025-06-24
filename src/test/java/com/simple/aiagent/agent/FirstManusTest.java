package com.simple.aiagent.agent;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FirstManusTest {

    @Resource
    private FirstManus firstManus;

    @Test
    public void run() {
        String userPrompt = """
                我的另一半在湖北武汉江岸区，请帮我找到5公里内的合适约会地点，
                并给出一份详细的约会计划，配上应景的图片，
                以PDF格式输出""";
        String res = firstManus.run(userPrompt);
        Assertions.assertNotNull(res);
    }
}