package com.green.greengram.feed.like;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram.common.model.ResultResponse;
import com.green.greengram.feed.like.model.FeedLikeReq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FeedLikeController.class,
            excludeAutoConfiguration = SecurityAutoConfiguration.class)
class FeedLikeControllerTest {


    @Autowired ObjectMapper objectMapper; //json 사용
    @Autowired MockMvc mockMvc; // 요청(보내고) + 응답(받기) 처리
    @MockBean FeedLikeService feedLikeService; // 가짜 객체생성 + 빈등록
    final String BASE_URL = "/api/feed/like";
    final long feedId_2 = 2L;
    @Test
    @DisplayName("좋아요 등록")
    void feedLikeReg() throws Exception {
        feedLikeToggle(1);
    }
    @Test
    @DisplayName("좋아요 취소")
    void feedLikeCancel() throws Exception {
        feedLikeToggle(0);
    }
    private void feedLikeToggle(final int result) throws Exception {
        FeedLikeReq givenParam = getGivenParam();
        given(feedLikeService.feedLikeToggle(givenParam)).willReturn(result);
        ResultActions resultActions = mockMvc.perform(get(BASE_URL).queryParams(getParameter()));
        // get방식  요청 보냄
        String expectedResJson = getExpectedResJson(result);
        // get방식의 기대 응답의 json을 저장
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResJson));
        verify(feedLikeService).feedLikeToggle(givenParam);
    }
    private MultiValueMap<String, String> getParameter() {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>(1);
        queryParams.add("feedId", String.valueOf(feedId_2));
        return queryParams;
    }
    private FeedLikeReq getGivenParam() {
        FeedLikeReq givenParam = new FeedLikeReq();
        givenParam.setFeedId(feedId_2);
        return givenParam;
    }
    private String getExpectedResJson(int result) throws Exception {
        ResultResponse expectedRes = ResultResponse.<Integer>builder()
                .resultMsg(result == 0 ? "좋아요 취소" : "좋아요 등록")
                .resultData(result)
                .build();
        return objectMapper.writeValueAsString(expectedRes);
    }
}