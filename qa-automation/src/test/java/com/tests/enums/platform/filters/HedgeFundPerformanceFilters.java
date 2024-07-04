package com.tests.enums.platform.filters;

import com.library.FindOptions;

import java.util.List;
import java.util.stream.Stream;

public enum HedgeFundPerformanceFilters
{
    fundName("Fund name"),
    fundAuM("Fund AuM (m)"),
    fundStructure("Fund structure"),
    openToInvestment("Open to investment"),
    inceptionDate("Inception date"),
    domicile("Domicile"),
    attribute("Attribute"),
    managerName("Manager name"),
    managerLocation("Manager location"),
    managerAuM("Manager total AuM (m)"),
    managerHedgeAuM("Manager hedge AuM (m)"),
    mainOffice("Main office"),
    primaryStrategy("Primary strategy"),
    secondaryStrategy("Secondary strategy"),
    approach("Approach"),
    investmentRegion("Investment region"),
    industry("Industry"),
    last3Months("Last 3 months"),
    worstMonth("Worst month"),
    YTD("YTD"),
    annualizedReturns("Annualized returns"),
    lastYear("Last year"),
    sharpeRatio("Sharpe ratio"),
    annualisedStandardDeviation("Annualised standard deviation"),
    maximumDrawdown("Maximum drawdown"),
    managementFees("Management fees"),
    performanceFees("Performance fees"),
    hurdleRate("Hurdle rate"),
    highWaterMark("High water mark"),
    primeBroker("Prime broker"),
    administrator("Administrator"),
    auditor("Auditor"),
    custodian("Custodian"),
    legalAdvisor("Legal advisor");

    public FindOptions findOptions;

    public final String value;

    HedgeFundPerformanceFilters(String option)
    {
        this.value = option;
    }

    public static List<String> getAllFilters()
    {
        return Stream.of(HedgeFundPerformanceFilters.values()).map(e -> e.value).toList();
    }
}
