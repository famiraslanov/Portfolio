package com.tests.enums.platform.filters;

import com.library.FindOptions;

import java.util.List;
import java.util.stream.Stream;

public enum PrivateCreditPerformanceFiltersFund
{
    assetClass("Asset class"),
    primaryStrategy("Primary strategy"),
    secondaryStrategy("Secondary strategy"),
    investmentRegion("Investment region"),
    industry("Industry"),
    fundSize("Fund size (m)"),
    managerName("Manager name"),
    managerLocation("Manager location"),
    fundName("Fund name"),
    vintage("Vintage"),
    status("Status"),
    netIRR("Net IRR"),
    TVPI("TVPI"),
    RVPI("RVPI"),
    DPI("DPI"),
    PIC("PIC");

    public FindOptions findOptions;

    public final String value;

    PrivateCreditPerformanceFiltersFund(String option)
    {
        this.value = option;
    }

    public static List<String> getAllFilters()
    {
        return Stream.of(PrivateCreditPerformanceFiltersFund.values()).map(e->e.value).toList();
    }
}
