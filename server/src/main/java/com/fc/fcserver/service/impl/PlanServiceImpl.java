package com.fc.fcserver.service.impl;

import com.fc.fcserver.dao.PlanMapper;
import com.fc.fcserver.entity.V2PlanSt;
import com.fc.fcserver.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    PlanMapper pm;

    @Override
    public List<V2PlanSt> getAllPlans() {
        return pm.getAllPlans();
    }

    @Override
    public boolean addNewPlan(String year, String planName, String planDetail, String planCompleted, String planImg) {
        return pm.addNewPlan(year, planName, planDetail, planCompleted, planImg);
    }

    @Override
    public boolean changeNewPlan(int planId, String year, String planName, String planDetail, String planCompleted, String planImg) {
        return pm.changePlan(planId, year, planName, planDetail, planCompleted, planImg);
    }

    @Override
    public boolean deletePlan(int planId) {
        return pm.deletePlan(planId);
    }
}
