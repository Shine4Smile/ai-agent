package com.simple.aiagent.advisor.forbiddenword;

/**
 * 自定义处理违禁词校验异常处理
 *
 * @author Simple
 */
public class ForbiddenWordException extends RuntimeException {
    public ForbiddenWordException(String message) {
        super(message);
    }
}
