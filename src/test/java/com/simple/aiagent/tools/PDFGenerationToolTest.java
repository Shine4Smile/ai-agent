package com.simple.aiagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PDFGenerationToolTest {

    @Test
    void generatePDF() {
        PDFGenerationTool pdfGenerationTool = new PDFGenerationTool();
        String fileName = "Test.pdf";
        String content = "测试文件内容！";
        String res = pdfGenerationTool.generatePDF(fileName, content);
        Assertions.assertNotNull(res);
    }
}