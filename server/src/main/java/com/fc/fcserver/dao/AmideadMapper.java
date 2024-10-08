package com.fc.fcserver.dao;

import com.fc.fcserver.entity.V2AmideadSt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AmideadMapper {

    @Select("SELECT dead_id AS deadId, am_i_dead AS amIDead, time AS time, last_word AS lastWord, map_url AS mapUrl, gift_url AS giftUrl, gift_still AS giftStill From v2_amidead_st")
    V2AmideadSt getAmidead();

    @Update("UPDATE v2_amidead_st SET time = CURRENT_TIMESTAMP")
    boolean updateTime();

    @Update("UPDATE v2_amidead_st SET gift_still = 0")
    boolean updateGiftStill();
}
