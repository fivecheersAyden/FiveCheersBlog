package com.fc.fcserver.dao;

import com.fc.fcserver.entity.V2SpaceSt;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SpaceMapper {

    @Insert("INSERT INTO v2_space_st(space_content, space_imgs, space_files, like_num) VALUES (#{spaceContent}, #{spaceImgs}, #{spaceFiles}, 0)")
    boolean insertOne(@Param("spaceContent") String spaceContent, @Param("spaceImgs") String spaceImgs, @Param("spaceFiles") String spaceFiles);

    @Delete("DELETE FROM v2_space_st WHERE space_id = #{spaceId}")
    boolean deleteOne(@Param("spaceId") int spaceId);

    @Update("UPDATE v2_space_st SET space_content = #{newSpace.spaceContent}, space_imgs = #{newSpace.spaceImgs}, space_files = #{newSpace.spaceFiles} WHERE space_id = #{newSpace.spaceId}")
    boolean updateOne(@Param("newSpace") V2SpaceSt newSpace);

    @Select("SELECT space_id as spaceId, space_time as spaceTime, space_content as spaceContent, space_imgs as spaceImgs, space_files as spaceFiles, like_num as likeNum FROM v2_space_st ORDER BY space_time DESC LIMIT #{startIndex}, #{length}")
    List<V2SpaceSt> selectSpace(@Param("startIndex") int startIndex, @Param("length") int length);

    @Update("UPDATE v2_space_st SET like_num = like_num + 1 WHERE space_id = #{spaceId}")
    boolean likeSpace(int spaceId);
}
