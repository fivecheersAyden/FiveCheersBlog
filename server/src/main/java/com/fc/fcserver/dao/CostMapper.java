package com.fc.fcserver.dao;

import com.fc.fcserver.dto.MonthAnalysis;
import com.fc.fcserver.entity.V2CountSt;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CostMapper {

    @Insert("INSERT INTO v2_count_st (countTime, countNum, countDetail, countType) VALUES (#{countTime}, #{countNum}, #{countDetail}, #{countType})")
    boolean addNewCost(V2CountSt countSt);

    @Delete("DELETE FROM v2_count_st WHERE countId=#{countId}")
    boolean deleteCost(int countId);

    @Update("UPDATE v2_count_st SET countNum = #{countNum}, countDetail = #{countDetail}, countType = #{countType} WHERE countId = #{countId}")
    boolean updateCost(V2CountSt countSt);

    @Select("SELECT COUNT(*) FROM v2_count_st")
    int getCostSum();

    @Select("SELECT * FROM v2_count_st ORDER BY countTime ASC LIMIT #{startIndex}, #{length}")
    List<V2CountSt> getCostByPage(int startIndex, int length);

    @Select("SELECT * FROM v2_count_st WHERE YEAR(countTime) = #{year} AND MONTH(countTime) = #{month} ORDER BY countTime ASC")
    List<V2CountSt> getCostByMonth(int year, int month);

    @Select("WITH summary AS (\n" +
            "    SELECT \n" +
            "        SUM(countNum) AS totalCost,\n" +
            "        SUM(CASE WHEN countNum > 0 THEN countNum ELSE 0 END) AS inNum,\n" +
            "        SUM(CASE WHEN countNum < 0 THEN countNum ELSE 0 END) AS outNum,\n" +
            "        SUM(CASE WHEN countType = '吃喝' THEN countNum ELSE 0 END) AS eatNum,\n" +
            "        SUM(CASE WHEN countType = '玩乐' THEN countNum ELSE 0 END) AS funNum,\n" +
            "        SUM(CASE WHEN countType = '住宿' THEN countNum ELSE 0 END) AS houseNum,\n" +
            "        SUM(CASE WHEN countType = '旅行' THEN countNum ELSE 0 END) AS travelNum,\n" +
            "        SUM(CASE WHEN countType = '生活花销' THEN countNum ELSE 0 END) AS livingNum,\n" +
            "        SUM(CASE WHEN countType = '大宗商品' THEN countNum ELSE 0 END) AS pricedNum,\n" +
            "        SUM(CASE WHEN countType = '其他' THEN countNum ELSE 0 END) AS otherNum\n" +
            "    FROM \n" +
            "        `v2_count_st` \n" +
            "    WHERE \n" +
            "        YEAR(countTime) = #{year} AND \n" +
            "        MONTH(countTime) = #{month}\n" +
            ")\n" +
            "SELECT \n" +
            "    #{year} AS `year`,\n" +
            "    #{month} AS `month`,\n" +
            "    totalCost,\n" +
            "    inNum,\n" +
            "    outNum,\n" +
            "    CASE WHEN outNum <> 0 THEN CAST(eatNum AS DOUBLE) / outNum ELSE 0 END AS `eatPercent`,\n" +
            "    CASE WHEN outNum <> 0 THEN CAST(funNum AS DOUBLE) / outNum ELSE 0 END AS `funPercent`,\n" +
            "    CASE WHEN outNum <> 0 THEN CAST(houseNum AS DOUBLE) / outNum ELSE 0 END AS `housePercent`,\n" +
            "    CASE WHEN outNum <> 0 THEN CAST(travelNum AS DOUBLE) / outNum ELSE 0 END AS `travelPercent`,\n" +
            "    CASE WHEN outNum <> 0 THEN CAST(livingNum AS DOUBLE) / outNum ELSE 0 END AS `livingPercent`,\n" +
            "    CASE WHEN outNum <> 0 THEN CAST(pricedNum AS DOUBLE) / outNum ELSE 0 END AS `pricedPercent`,\n" +
            "    CASE WHEN outNum <> 0 THEN CAST(otherNum AS DOUBLE) / outNum ELSE 0 END AS `otherPercent`\n" +
            "FROM \n" +
            "    summary;\n")
    MonthAnalysis getMonthAnalysisByMonth(int year, int month);

    @Select("SELECT * FROM v2_count_st WHERE YEAR(countTime) = #{year} ORDER BY countTime ASC")
    List<V2CountSt> getCostByYear(int year);

    @Select("WITH summary AS (\n" +
            "    SELECT \n" +
            "        MONTH(countTime) AS `month`,\n" +
            "        SUM(countNum) AS totalCost,\n" +
            "        SUM(CASE WHEN countNum > 0 THEN countNum ELSE 0 END) AS inNum,\n" +
            "        SUM(CASE WHEN countNum < 0 THEN countNum ELSE 0 END) AS outNum,\n" +
            "        SUM(CASE WHEN countType = '吃喝' THEN countNum ELSE 0 END) AS eatNum,\n" +
            "        SUM(CASE WHEN countType = '玩乐' THEN countNum ELSE 0 END) AS funNum,\n" +
            "        SUM(CASE WHEN countType = '住宿' THEN countNum ELSE 0 END) AS houseNum,\n" +
            "        SUM(CASE WHEN countType = '旅行' THEN countNum ELSE 0 END) AS travelNum,\n" +
            "        SUM(CASE WHEN countType = '生活花销' THEN countNum ELSE 0 END) AS livingNum,\n" +
            "        SUM(CASE WHEN countType = '大宗商品' THEN countNum ELSE 0 END) AS pricedNum,\n" +
            "        SUM(CASE WHEN countType = '其他' THEN countNum ELSE 0 END) AS otherNum\n" +
            "    FROM \n" +
            "        `v2_count_st` \n" +
            "    WHERE \n" +
            "        YEAR(countTime) = #{year}\n" +
            "    GROUP BY \n" +
            "        MONTH(countTime)\n" +
            ")\n" +
            "SELECT \n" +
            "    #{year} AS `year`,\n" +
            "    `month`,\n" +
            "    totalCost,\n" +
            "    inNum,\n" +
            "    outNum,\n" +
            "    CASE WHEN outNum <> 0 THEN CAST(eatNum AS DOUBLE) / outNum ELSE 0 END AS `eatPercent`,\n" +
            "    CASE WHEN outNum <> 0 THEN CAST(funNum AS DOUBLE) / outNum ELSE 0 END AS `funPercent`,\n" +
            "    CASE WHEN outNum <> 0 THEN CAST(houseNum AS DOUBLE) / outNum ELSE 0 END AS `housePercent`,\n" +
            "    CASE WHEN outNum <> 0 THEN CAST(travelNum AS DOUBLE) / outNum ELSE 0 END AS `travelPercent`,\n" +
            "    CASE WHEN outNum <> 0 THEN CAST(livingNum AS DOUBLE) / outNum ELSE 0 END AS `livingPercent`,\n" +
            "    CASE WHEN outNum <> 0 THEN CAST(pricedNum AS DOUBLE) / outNum ELSE 0 END AS `pricedPercent`,\n" +
            "    CASE WHEN outNum <> 0 THEN CAST(otherNum AS DOUBLE) / outNum ELSE 0 END AS `otherPercent`\n" +
            "FROM \n" +
            "    summary;\n")
    List<MonthAnalysis> getMonthAnalysisByYear(int year);

    @Select("WITH summary AS (\n" +
            "    SELECT \n" +
            "        SUM(countNum) AS totalCost,\n" +
            "        SUM(CASE WHEN countNum > 0 THEN countNum ELSE 0 END) AS inNum,\n" +
            "        SUM(CASE WHEN countNum < 0 THEN countNum ELSE 0 END) AS outNum,\n" +
            "        SUM(CASE WHEN countType = '吃喝' THEN countNum ELSE 0 END) AS eatNum,\n" +
            "        SUM(CASE WHEN countType = '玩乐' THEN countNum ELSE 0 END) AS funNum,\n" +
            "        SUM(CASE WHEN countType = '住宿' THEN countNum ELSE 0 END) AS houseNum,\n" +
            "        SUM(CASE WHEN countType = '旅行' THEN countNum ELSE 0 END) AS travelNum,\n" +
            "        SUM(CASE WHEN countType = '生活花销' THEN countNum ELSE 0 END) AS livingNum,\n" +
            "        SUM(CASE WHEN countType = '大宗商品' THEN countNum ELSE 0 END) AS pricedNum,\n" +
            "        SUM(CASE WHEN countType = '其他' THEN countNum ELSE 0 END) AS otherNum\n" +
            "    FROM \n" +
            "        v2_count_st \n" +
            "    WHERE \n" +
            "        YEAR(countTime) = #{year}\n" +
            ")\n" +
            "SELECT \n" +
            "    #{year} AS year,\n" +
            "    NULL AS month,\n" +
            "    totalCost,\n" +
            "    inNum,\n" +
            "    outNum,\n" +
            "    CASE WHEN outNum <> 0 THEN CAST(eatNum AS DOUBLE) / outNum ELSE 0 END AS eatPercent,\n" +
            "    CASE WHEN outNum <> 0 THEN CAST(funNum AS DOUBLE) / outNum ELSE 0 END AS funPercent,\n" +
            "    CASE WHEN outNum <> 0 THEN CAST(houseNum AS DOUBLE) / outNum ELSE 0 END AS housePercent,\n" +
            "    CASE WHEN outNum <> 0 THEN CAST(travelNum AS DOUBLE) / outNum ELSE 0 END AS travelPercent,\n" +
            "    CASE WHEN outNum <> 0 THEN CAST(livingNum AS DOUBLE) / outNum ELSE 0 END AS livingPercent,\n" +
            "    CASE WHEN outNum <> 0 THEN CAST(pricedNum AS DOUBLE) / outNum ELSE 0 END AS pricedPercent,\n" +
            "    CASE WHEN outNum <> 0 THEN CAST(otherNum AS DOUBLE) / outNum ELSE 0 END AS otherPercent\n" +
            "FROM \n" +
            "    summary;")
    MonthAnalysis getYearAnalysisByYear(int year);

    @Select("WITH summary AS (\n" +
            "    SELECT \n" +
            "        Year(countTime) AS `year`,\n" +
            "        SUM(countNum) AS totalCost,\n" +
            "        SUM(CASE WHEN countNum > 0 THEN countNum ELSE 0 END) AS inNum,\n" +
            "        SUM(CASE WHEN countNum < 0 THEN countNum ELSE 0 END) AS outNum,\n" +
            "        SUM(CASE WHEN countType = '吃喝' THEN countNum ELSE 0 END) AS eatNum,\n" +
            "        SUM(CASE WHEN countType = '玩乐' THEN countNum ELSE 0 END) AS funNum,\n" +
            "        SUM(CASE WHEN countType = '住宿' THEN countNum ELSE 0 END) AS houseNum,\n" +
            "        SUM(CASE WHEN countType = '旅行' THEN countNum ELSE 0 END) AS travelNum,\n" +
            "        SUM(CASE WHEN countType = '生活花销' THEN countNum ELSE 0 END) AS livingNum,\n" +
            "        SUM(CASE WHEN countType = '大宗商品' THEN countNum ELSE 0 END) AS pricedNum,\n" +
            "        SUM(CASE WHEN countType = '其他' THEN countNum ELSE 0 END) AS otherNum\n" +
            "    FROM \n" +
            "        `v2_count_st` \n" +
            "    GROUP BY \n" +
            "        YEAR(countTime)\n" +
            ")\n" +
            "SELECT \n" +
            "    `year`,\n" +
            "    NULL AS `month`,\n" +
            "    totalCost,\n" +
            "    inNum,\n" +
            "    outNum,\n" +
            "    CASE WHEN outNum <> 0 THEN CAST(eatNum AS DOUBLE) / outNum ELSE 0 END AS `eatPercent`,\n" +
            "    CASE WHEN outNum <> 0 THEN CAST(funNum AS DOUBLE) / outNum ELSE 0 END AS `funPercent`,\n" +
            "    CASE WHEN outNum <> 0 THEN CAST(houseNum AS DOUBLE) / outNum ELSE 0 END AS `housePercent`,\n" +
            "    CASE WHEN outNum <> 0 THEN CAST(travelNum AS DOUBLE) / outNum ELSE 0 END AS `travelPercent`,\n" +
            "    CASE WHEN outNum <> 0 THEN CAST(livingNum AS DOUBLE) / outNum ELSE 0 END AS `livingPercent`,\n" +
            "    CASE WHEN outNum <> 0 THEN CAST(pricedNum AS DOUBLE) / outNum ELSE 0 END AS `pricedPercent`,\n" +
            "    CASE WHEN outNum <> 0 THEN CAST(otherNum AS DOUBLE) / outNum ELSE 0 END AS `otherPercent`\n" +
            "FROM \n" +
            "    summary;\n")
    MonthAnalysis getYearAnalysisByAll();

    @Select("SELECT SUM(countNum) FROM v2_count_st")
    int getAllCostSum();
}
