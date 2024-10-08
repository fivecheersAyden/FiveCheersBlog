package com.fc.fcserver.service;

import com.fc.fcserver.entity.V2PlanSt;

import java.util.List;

public interface PlanService {

    List<V2PlanSt> getAllPlans();

    boolean addNewPlan(String year, String planName, String planDetail, String planCompleted, String planImg);

    boolean changeNewPlan(int planId, String year, String planName, String planDetail, String planCompleted, String planImg);

    boolean deletePlan(int planId);
}
