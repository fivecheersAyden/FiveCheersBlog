package com.fc.fcserver.service.impl;

import com.fc.fcserver.dao.CostMapper;
import com.fc.fcserver.dto.MonthAnalysis;
import com.fc.fcserver.entity.V2CountSt;
import com.fc.fcserver.service.CostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CostServiceImpl implements CostService {

    @Autowired
    CostMapper cm;

    @Override
    public boolean addNewCost(V2CountSt countSt) {
        return cm.addNewCost(countSt);
    }

    @Override
    public boolean deleteCost(int countId) {
        return cm.deleteCost(countId);
    }

    @Override
    public boolean updateCost(V2CountSt countSt) {
        return cm.updateCost(countSt);
    }

    @Override
    public int getCostSum() {
        return cm.getCostSum();
    }

    @Override
    public List<V2CountSt> getCostByPage(int startIndex, int length) {
        return cm.getCostByPage(startIndex, length);
    }

    @Override
    public List<V2CountSt> getCostByMonth(int year, int month) {
        return cm.getCostByMonth(year, month);
    }

    @Override
    public MonthAnalysis getMonthAnalysisByMonth(int year, int month) {
        return cm.getMonthAnalysisByMonth(year, month);
    }

    @Override
    public List<V2CountSt> getCostByYear(int year) {
        return cm.getCostByYear(year);
    }

    @Override
    public List<MonthAnalysis> getMonthAnalysisByYear(int year) {
        return cm.getMonthAnalysisByYear(year);
    }

    @Override
    public MonthAnalysis getYearAnalysisByYear(int year) {
        return cm.getYearAnalysisByYear(year);
    }

    @Override
    public MonthAnalysis getYearAnalysisByAll() {
        return cm.getYearAnalysisByAll();
    }

    @Override
    public int getAllCostSum() {
        return cm.getAllCostSum();
    }
}
