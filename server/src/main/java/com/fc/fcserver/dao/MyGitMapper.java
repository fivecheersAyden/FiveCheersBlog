package com.fc.fcserver.dao;

import com.fc.fcserver.entity.V2MygitSt;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MyGitMapper {

    @Insert("INSERT INTO v2_mygit_st(git_content, git_url, git_video, run_url) VALUES (#{gitContent},#{gitUrl},#{gitVideo},#{runUrl})")
    boolean insertOne(String gitContent, String gitUrl, String gitVideo, String runUrl);

    @Delete("DELETE FROM v2_mygit_st WHERE git_id = #{gitId}")
    boolean deleteOne(int gitId);

    @Select("SELECT git_id AS gitId, git_content AS gitContent, git_url AS gitUrl, git_video AS gitVideo, run_url AS runUrl, time AS time FROM v2_mygit_st ORDER BY time DESC LIMIT #{startIndex}, #{length}")
    List<V2MygitSt> selectMyGit(int startIndex, int length);
}
