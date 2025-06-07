package com.simple.aiagent.advisor.forbiddenword;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.advisor.api.*;
import org.springframework.ai.chat.model.MessageAggregator;
import reactor.core.publisher.Flux;

/**
 * 违禁词校验advisor，自动加载所有违禁词，用户请求和AI回复均校验。
 * 命中违禁词则抛出ForbiddenWordException。
 *
 * @author Simple
 */
@Slf4j
public class ForbiddenWordsAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {
    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return 1; // 比日志advisor优先级高
    }

    // 校验请求
    private void checkRequest(AdvisedRequest request) {
        String userText = request.userText();
        String forbidden = ForbiddenWordsLoader.findFirstForbiddenWord(userText);
        if (forbidden != null) {
            log.warn("AI Request 用户输入内容命中违禁词: {}", forbidden);
            throw new ForbiddenWordException("请求内容包含违禁词: " + forbidden);
        }
    }

    // 校验AI回复
    private void checkResponse(AdvisedResponse response) {
        String aiText = response.response().getResult().getOutput().getText();
        String forbidden = ForbiddenWordsLoader.findFirstForbiddenWord(aiText);
        if (forbidden != null) {
            log.warn("AI Response AI回复内容命中违禁词: {}", forbidden);
            throw new ForbiddenWordException("AI回复内容包含违禁词: " + forbidden);
        }
    }

    @Override
    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
        checkRequest(advisedRequest);
        AdvisedResponse response = chain.nextAroundCall(advisedRequest);
        checkResponse(response);
        return response;
    }

    @Override
    public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
        checkRequest(advisedRequest);
        Flux<AdvisedResponse> responses = chain.nextAroundStream(advisedRequest);
        return (new MessageAggregator()).aggregateAdvisedResponse(responses, this::checkResponse);
    }
}
