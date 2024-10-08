package com.fc.fcserver.service;

import com.fc.fcserver.dto.MonthAnalysis;
import com.fc.fcserver.entity.V2CountSt;

import java.util.List;

public interface CostService {
    boolean addNewCost(V2CountSt countSt);

    boolean deleteCost(int countId);

    boolean updateCost(V2CountSt countSt);

    int getCostSum();

    List<V2CountSt> getCostByPage(int startIndex, int length);

    List<V2CountSt> getCostByMonth(int year, int month);

    MonthAnalysis getMonthAnalysisByMonth(int year, int month);

    List<V2CountSt> getCostByYear(int year);

    List<MonthAnalysis> getMonthAnalysisByYear(int year);

    MonthAnalysis getYearAnalysisByYear(int year);

    MonthAnalysis getYearAnalysisByAll();

    int getAllCostSum();
}
