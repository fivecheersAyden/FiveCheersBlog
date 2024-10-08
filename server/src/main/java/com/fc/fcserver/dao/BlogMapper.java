package com.fc.fcserver.dao;

import com.fc.fcserver.entity.V2BlogSt;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BlogMapper {

    @Select("SELECT blogKey, title FROM v2_blog_st")
    List<V2BlogSt> getAllBlogKeys();

    @Select("Select blogKey FROM v2_blog_st WHERE blogKey LIKE CONCAT(#{frontKeys}, '%')")
    List<String> getFatherKeys(String frontKeys);

    @Update("UPDATE v2_blog_st SET title = #{newName} WHERE blogKey = #{currentKey}")
    boolean resetName(String currentKey, String newName);

    @Delete("DELETE FROM v2_blog_st WHERE blogKey = #{currentKey}")
    boolean deleteDoc(String currentKey);

    @Select("SELECT * FROM v2_blog_st WHERE blogKey = #{currentKey}")
    V2BlogSt getDocDetail(String currentKey);

    @Update("UPDATE v2_blog_st SET content = #{content} WHERE blogKey = #{blogKey}")
    boolean updateDocDetail(String blogKey, String content);

    @Insert("INSERT INTO v2_blog_st (blogKey, createdTime, title, content) VALUES (#{newKey}, CURRENT_TIMESTAMP, 'Default_Title', '# Default_Content')")
    boolean insertNewDoc(String newKey);
}
