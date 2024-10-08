package com.fc.fcserver.controller;

import com.fc.fcserver.dto.MonthAnalysis;
import com.fc.fcserver.entity.V2CountSt;
import com.fc.fcserver.service.CostService;
import com.fc.fcserver.service.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cost")
public class CostController {

    @Autowired
    CostService cs;


    /**
     * 增加一条账单
     */
    @PostMapping("/addNewCost")
    public boolean addNewCost(@RequestBody V2CountSt countSt){
        countSt.setCountTime(TimeUtils.convertToShanghaiTime(countSt.getCountTime().toString()));
        return cs.addNewCost(countSt);
    }

    /**
     * 删除一条账单
     */
    @PostMapping("/deleteCost")
    public boolean deleteCost(@RequestParam("countId") int countId){
        return cs.deleteCost(countId);
    }

    /**
     * 更改一条账单
     */
    @PostMapping("/updateCost")
    public boolean updateCost(@RequestBody V2CountSt countSt){
        countSt.setCountTime(TimeUtils.convertToShanghaiTime(countSt.getCountTime().toString()));
        return cs.updateCost(countSt);
    }

    /**
     * 获取账单条数
     */
    @GetMapping("/getCostSum")
    public int getCostSum(){
        return cs.getCostSum();
    }

    /**
     * 按照时间顺序按页查询账单
     */
    @GetMapping("/getCostByPage")
    public List<V2CountSt> getCostByPage(@RequestParam("startIndex") int startIndex, @RequestParam("length") int length){
        return cs.getCostByPage(startIndex, length);
    }

    /**
     * 返回某月所有账单
     */
    @GetMapping("/getCostByMonth")
    public List<V2CountSt> getCostByMonth(@RequestParam("year") int year, @RequestParam("month") int month){
        return cs.getCostByMonth(year, month);
    }

    /**
     * 返回某月账单统计数据（总数、比例、基尼系数）
     */
    @GetMapping("/getMonthAnalysisByMonth")
    public MonthAnalysis getMonthAnalysisByMonth(@RequestParam("year") int year, @RequestParam("month") int month){
        return cs.getMonthAnalysisByMonth(year, month);
    }

    /**
     * 返回某年所有账单
     */
    @GetMapping("/getCostByYear")
    public List<V2CountSt> getCostByYear(@RequestParam("year") int year){
        return cs.getCostByYear(year);
    }

    /**
     * 返回某年所有月账单统计数据
     */
    @GetMapping("/getMonthAnalysisByYear")
    public List<MonthAnalysis> getMonthAnalysisByYear(@RequestParam("year") int year){
        return cs.getMonthAnalysisByYear(year);
    }

    /**
     * 返回某年账单统计数据（总数、比例、基尼系数）
     */
    @GetMapping("/getYearAnalysisByYear")
    public MonthAnalysis getYearAnalysisByYear(@RequestParam("year") int year){
        return cs.getYearAnalysisByYear(year);
    }

    /**
     * 返回所有年账单统计数据
     */
    @GetMapping("/getYearAnalysisByAll")
    public MonthAnalysis getYearAnalysisByAll(){
        return cs.getYearAnalysisByAll();
    }

    /**
     * 获取所有账单总计正负金额
     */
    @GetMapping("/getAllCostSum")
    public int getAllCostSum(){
        return cs.getAllCostSum();
    }


}
