package com.simple.imagesearchmcpserver.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ImageSearchToolTest {

    @Test
    void searchImage() {
        ImageSearchTool imageSearchTool = new ImageSearchTool();
        String res = imageSearchTool.searchImage("Kobe Bryant");
        Assertions.assertNotNull(res);
    }
}