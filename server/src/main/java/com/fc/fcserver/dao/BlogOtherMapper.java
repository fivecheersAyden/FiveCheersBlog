package com.fc.fcserver.dao;

import com.fc.fcserver.entity.V2BlogSt;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BlogOtherMapper {

    @Select("SELECT blogKey, title FROM v2_blog_other_st WHERE user=#{user}")
    List<V2BlogSt> getAllBlogKeys(String user);

    @Select("Select blogKey FROM v2_blog_other_st WHERE user=#{user} AND blogKey LIKE CONCAT(#{frontKeys}, '%')")
    List<String> getFatherKeys(String frontKeys, String user);

    @Update("UPDATE v2_blog_other_st SET title = #{newName} WHERE user=#{user} AND blogKey = #{currentKey}")
    boolean resetName(String currentKey, String newName, String user);

    @Delete("DELETE FROM v2_blog_other_st WHERE user=#{user} AND blogKey = #{currentKey}")
    boolean deleteDoc(String currentKey, String user);

    @Select("SELECT * FROM v2_blog_other_st WHERE user=#{user} AND blogKey = #{currentKey}")
    V2BlogSt getDocDetail(String currentKey, String user);

    @Update("UPDATE v2_blog_other_st SET content = #{content} WHERE user=#{user} AND blogKey = #{blogKey}")
    boolean updateDocDetail(String blogKey, String content, String user);

    @Insert("INSERT INTO v2_blog_other_st (blogKey, createdTime, title, content, user) VALUES (#{newKey}, CURRENT_TIMESTAMP, 'Default_Title', '# Default_Content', #{user})")
    boolean insertNewDoc(String newKey, String user);

    @Select("SELECT COUNT(*) > 0 FROM v2_blog_other_st WHERE user = #{user}")
    boolean getIfUser(String user);

    @Select("SELECT keySpark FROM v2_blog_other_password_st WHERE keyAccount = #{account}")
    String getKeyByAccountAndPassword(String account);

    @Select("SELECT keyPassword FROM v2_blog_other_password_st WHERE keyAccount = #{account}")
    String getPassword(String account);

    @Update("UPDATE v2_blog_other_password_st set keyPassword = #{password} WHERE keyAccount = #{account}")
    boolean changePassword(String account, String password);

    @Update("UPDATE v2_blog_other_password_st set keySpark = #{key} where keyAccount = #{account}")
    boolean changeKeyByAccount(String account, String key);
}
