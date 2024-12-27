package com.green.greengram.feed;

import com.green.greengram.feed.model.FeedPicDto;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.MyBatisSystemException;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FeedPicMapperTest {
    @Autowired
        FeedPicMapper feedPicMapper;

    @Test
        void insFeedPicNoFeedIdThrowsForeignKeyException() {
            FeedPicDto givenParam = new FeedPicDto();
            givenParam.setFeedId(10L);
            givenParam.setPics(new ArrayList<>(1));
            givenParam.getPics().add("a.jpg");
            assertThrows(DataIntegrityViolationException.class, () -> feedPicMapper.insFeedPic(givenParam));
    }

    @Test
    void insFeedPicNoPicThrowNotNullException() {
        FeedPicDto givenParam = new FeedPicDto();
        givenParam.setFeedId(1L);
        givenParam.setPics(new ArrayList<>());
        assertThrows(BadSqlGrammarException.class, () -> feedPicMapper.insFeedPic(givenParam));
    }

    @Test
    void insFeedPic(){
        FeedPicDto givenParam = new FeedPicDto();
        givenParam.setFeedId(1L);
        givenParam.setPics(new ArrayList<>(1));
        givenParam.getPics().add("a.jpg");
        givenParam.getPics().add("b.jpg");

        int actualAffectedRows = feedPicMapper.insFeedPic(givenParam);

        assertEquals(givenParam.getPics().size(), actualAffectedRows);
    }
    @Test
    void insFeedPic1(){
        String [] pics = {"a.jpg", "b.jpg", "c.jpg"};
        FeedPicDto givenParam = new FeedPicDto();
        givenParam.setFeedId(1L);
        givenParam.setPics(new ArrayList<>(pics.length));
        for(String pic: pics){
            givenParam.getPics().add(pic);
        }
        int actualAffectedRows = feedPicMapper.insFeedPic(givenParam);
        assertEquals(pics.length, actualAffectedRows);
    }

    @Test
    void insFeedPic_PicStringLengthMoreThan50_ThrowException(){
        FeedPicDto givenParam = new FeedPicDto();
        givenParam.setFeedId(1L);
        givenParam.setPics(new ArrayList<>(1));
        givenParam.getPics().add("123456789_123456789_123456789_123456789_123456789_12");
        assertThrows(BadSqlGrammarException.class, () -> feedPicMapper.insFeedPic(givenParam));
    }
}