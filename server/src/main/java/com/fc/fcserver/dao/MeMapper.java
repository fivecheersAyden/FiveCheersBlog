package com.fc.fcserver.dao;

import com.fc.fcserver.entity.V2MeSt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MeMapper {

    @Select("SELECT * FROM v2_me_st")
    List<V2MeSt> selectAllFromV2Me();

    @Update("UPDATE v2_me_st SET value = #{value} WHERE type = #{type}")
    boolean changeKey(String type, String value);
}
