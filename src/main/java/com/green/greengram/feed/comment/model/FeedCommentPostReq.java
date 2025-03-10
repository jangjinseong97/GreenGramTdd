package com.green.greengram.feed.comment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FeedCommentPostReq {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private long feedId;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private long userId;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String comment;
    @JsonIgnore
    private long feedCommentId;
}
