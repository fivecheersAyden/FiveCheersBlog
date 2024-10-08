package com.fc.fcserver.controller;

import com.fc.fcserver.entity.V2PlanSt;
import com.fc.fcserver.service.PlanService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/plan")
public class PlanController {

    @Autowired
    PlanService ps;

    @GetMapping("/getAllPlans")
    public List<V2PlanSt> getAllPlans(){
        return ps.getAllPlans();
    }

    @PostMapping("/addNewPlan")
    public boolean addNewPlan(@Param("year") String year,
                              @Param("planName") String planName,
                              @Param("planDetail") String planDetail,
                              @Param("planCompleted") String planCompleted,
                              @Param("planImg") String planImg){
        return ps.addNewPlan(year, planName, planDetail, planCompleted, planImg);
    }

    @PostMapping("/changePlan")
    public boolean addNewPlan(@Param("planId") int planId,
                              @Param("year") String year,
                              @Param("planName") String planName,
                              @Param("planDetail") String planDetail,
                              @Param("planCompleted") String planCompleted,
                              @Param("planImg") String planImg){
        return ps.changeNewPlan(planId, year, planName, planDetail, planCompleted, planImg);
    }

    @PostMapping("/deletePlan")
    public boolean deletePlan(@Param("planId") int planId){
        return ps.deletePlan(planId);
    }

}
