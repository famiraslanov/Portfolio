package com.tests.enums.platform.filters;

import com.library.FindOptions;

import java.util.List;
import java.util.stream.Stream;

public enum AllocateFilterInvestor
{
    investorName("Investor name"),
    investorType("Investor type"),
    investorLocation("Investor location"),
    aum("AuM"),
    consultantName("Consultant name"),
    industry("Industry"),
    investmentRegion("Investment region");

    public FindOptions findOptions;

    public final String value;

    AllocateFilterInvestor(String option)
    {
        this.value = option;
    }

    public static List<String> getAllFilters()
    {
        return Stream.of(AllocateFilterInvestor.values()).map(e->e.value).toList();
    }
}