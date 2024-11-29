package com.green.greengramver2.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ResultResponse<T> {
    @Schema(title = "결과 메세지")
    private String resultMsg;
    @Schema(title = "결과 내용")
    private T resultData;
}
