package com.fc.fcserver.dao;

import com.fc.fcserver.entity.V2VideoSt;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VideoMapper {

    @Insert("INSERT INTO v2_video_st(video_content, video_url) VALUES (#{videoContent}, #{videoUrl})")
    boolean insertOne(String videoContent, String videoUrl);

    @Delete("DELETE FROM v2_video_st WHERE video_id = #{videoId}")
    boolean deleteOne(int videoId);

    @Select("SELECT video_id as videoId, video_url as videoUrl, video_content as videoContent, time FROM v2_video_st ORDER BY time DESC LIMIT #{startIndex}, #{length}")
    List<V2VideoSt> selectVideos(int startIndex, int length);
}
