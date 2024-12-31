package com.green.greengram.feed;

import com.green.greengram.feed.model.FeedPicVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FeedPicTestMapper {
    @Select("select * from feed_pic where feed_id = #{feedId}")
    List<FeedPicVo> selFeedPicListByFeedId(long feedId);
}