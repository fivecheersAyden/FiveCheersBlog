package com.fc.fcserver.dto;

import lombok.Data;

@Data
public class MonthAnalysis {
    int year;
    int month;
    int totalCost;
    int inNum;
    int outNum;
    double eatPercent;
    double funPercent;
    double housePercent;
    double travelPercent;
    double livingPercent;
    double pricedPercent;
    double otherPercent;
}
