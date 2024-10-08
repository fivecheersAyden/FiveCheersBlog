package com.fc.fcserver.dao;

import com.fc.fcserver.entity.V2PlanSt;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface PlanMapper {

    @Results({
            @Result(column = "plan_id", property = "planId"),
            @Result(column = "year", property = "year"),
            @Result(column = "plan_name", property = "planName"),
            @Result(column = "plan_detail", property = "planDetail"),
            @Result(column = "plan_completed", property = "planCompleted"),
            @Result(column = "plan_img", property = "planImg")
    })
    @Select("SELECT * FROM v2_plan_st")
    List<V2PlanSt> getAllPlans();

    @Insert("INSERT INTO v2_plan_st (year, plan_name, plan_detail, plan_completed, plan_img) " +
            "VALUES (#{year}, #{planName}, #{planDetail}, #{planCompleted}, #{planImg})")
    boolean addNewPlan(@Param("year") String year, @Param("planName") String planName,
                       @Param("planDetail") String planDetail, @Param("planCompleted") String planCompleted,
                       @Param("planImg") String planImg);

    @Update("UPDATE v2_plan_st SET year = #{year}, plan_name = #{planName}, plan_detail = #{planDetail}, " +
            "plan_completed = #{planCompleted}, plan_img = #{planImg} WHERE plan_id = #{planId}")
    boolean changePlan(@Param("planId") int planId, @Param("year") String year, @Param("planName") String planName,
                       @Param("planDetail") String planDetail, @Param("planCompleted") String planCompleted,
                       @Param("planImg") String planImg);

    @Delete("DELETE FROM v2_plan_st WHERE plan_id = #{planId}")
    boolean deletePlan(@Param("planId") int planId);
}
