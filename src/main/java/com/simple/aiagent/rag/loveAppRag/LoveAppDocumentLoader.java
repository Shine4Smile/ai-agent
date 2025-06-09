package com.simple.aiagent.rag.loveAppRag;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 加载本地恋爱相关文档作为AI恋爱助手的知识库
 *
 * @author Simple
 */
@Slf4j
@Component
public class LoveAppDocumentLoader {

    private final ResourcePatternResolver resourcePatternResolver;

    LoveAppDocumentLoader(ResourcePatternResolver resourcePatternResolver) {
        this.resourcePatternResolver = resourcePatternResolver;
    }


    public List<Document> loadMarkdowns() {
        List<Document> allDocuments = new ArrayList<>();
        try {
            // 加载目录下所有markdown文档
            Resource[] resources = resourcePatternResolver.getResources("classpath:document/loveAppDoc/*.md");
            for (Resource resource : resources) {
                String fileName = resource.getFilename();
                // 配置markdown读取设置
                MarkdownDocumentReaderConfig config = MarkdownDocumentReaderConfig.builder()
                        // 启用水平分隔线创建文档
                        .withHorizontalRuleCreateDocument(true)
                        // 不包含引用块
                        .withIncludeBlockquote(false)
                        // 不包含代码块
                        .withIncludeCodeBlock(false)
                        // 添加文件名作为元数据
                        .withAdditionalMetadata("fileName", fileName)
                        .build();
                // 创建Markdown文档读取器，并传入当前资源和配置
                MarkdownDocumentReader reader = new MarkdownDocumentReader(resource, config);
                // 将读取到的文档分片添加到allDocuments列表中
                allDocuments.addAll(reader.get());
            }
        } catch (IOException e) {
            log.error("加载恋爱markdown文档失败，{}", e.getMessage());
        }
        return allDocuments;
    }
}
