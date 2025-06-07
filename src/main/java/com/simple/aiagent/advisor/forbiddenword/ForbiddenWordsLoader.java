package com.simple.aiagent.advisor.forbiddenword;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 违禁词加载器，自动扫描resources/forbidden-words/目录下所有json文件，合并所有违禁词为一个Set。
 * 支持高效校验和后续扩展。
 *
 * @author Simple
 */
@Slf4j
public class ForbiddenWordsLoader {
    private static final String FORBIDDEN_WORDS_PATH = "classpath:forbidden-words/*.json";
    private static final Set<String> forbiddenWords = new HashSet<>();
    private static final Object lock = new Object();
    private static volatile boolean loaded = false;

    // 加载所有违禁词（懒加载，线程安全）
    public static Set<String> getForbiddenWords() {
        if (!loaded) {
            synchronized (lock) {
                if (!loaded) {
                    loadForbiddenWords();
                    loaded = true;
                }
            }
        }
        return forbiddenWords;
    }

    private static void loadForbiddenWords() {
        forbiddenWords.clear();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Resource[] resources = resolver.getResources(FORBIDDEN_WORDS_PATH);
            for (Resource resource : resources) {
                try (InputStream is = resource.getInputStream()) {
                    List<String> words = objectMapper.readValue(is, new TypeReference<List<String>>() {
                    });
                    forbiddenWords.addAll(words);
                } catch (IOException e) {
                    log.error("加载违禁词文件异常: {}", e.getMessage());
                }
            }
        } catch (IOException e) {
            log.error("加载违禁词目录异常: {}", e.getMessage());
        }
    }

    // 校验文本是否包含违禁词
    public static String findFirstForbiddenWord(String text) {
        for (String word : getForbiddenWords()) {
            if (text != null && text.contains(word)) {
                return word;
            }
        }
        return null;
    }

    // todo：支持热更新、定时刷新等
}
