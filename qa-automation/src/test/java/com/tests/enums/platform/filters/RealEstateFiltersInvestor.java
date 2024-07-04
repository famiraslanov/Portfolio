package com.tests.enums.platform.filters;

import com.library.FindOptions;

import java.util.List;
import java.util.stream.Stream;

public enum RealEstateFiltersInvestor
{
    investorName("Investor name"),
    investorType("Investor type"),
    investorLocation("Investor location"),
    investorAuM("Investor AuM (m)"),
    assetClass("Asset class"),
    primaryStrategy("Primary strategy"),
    secondaryStrategy("Secondary strategy"),
    sector("Sector"),
    investmentRegion("Investment region"),
    attribute("Attribute"),
    consultantName("Consultant name"),
    managerName("Manager name");

    public FindOptions findOptions;

    public final String value;

    RealEstateFiltersInvestor(String option)
    {
        this.value = option;
    }

    public static List<String> getAllFilters()
    {
        return Stream.of(RealEstateFiltersInvestor.values()).map(e->e.value).toList();
    }
}
