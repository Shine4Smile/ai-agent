package com.simple.aiagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ResourceDownloadToolTest {

    @Test
    void downloadResource() {
        ResourceDownloadTool resourceDownloadTool = new ResourceDownloadTool();
        String url = "https://inews.gtimg.com/om_bt/O-cw9xxkjVRgJmY2T2n_sPATCklTbWIcWC0Zrl8HmvMr0AA/0";
        String fileName = "test.jpg";
        String res = resourceDownloadTool.downloadResource(url, fileName);
        Assertions.assertNotNull(res);
    }
}