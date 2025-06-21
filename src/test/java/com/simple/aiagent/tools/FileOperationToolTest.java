package com.simple.aiagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FileOperationToolTest {

    @Test
    void readFile() {
        FileOperationTool fileOperationTool = new FileOperationTool();
        String fileName = "测试文件.txt";
        String content = fileOperationTool.readFile(fileName);
        Assertions.assertNotNull(content);
    }

    @Test
    void writeFile() {
        FileOperationTool fileOperationTool = new FileOperationTool();
        String fileName = "测试文件.txt";
        String res = fileOperationTool.writeFile(fileName, "测试文件写入方法");
        Assertions.assertNotNull(res);
    }
}