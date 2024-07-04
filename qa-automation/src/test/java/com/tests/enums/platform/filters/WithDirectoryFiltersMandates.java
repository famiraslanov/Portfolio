package com.tests.enums.platform.filters;

import com.library.FindOptions;

import java.util.List;
import java.util.stream.Stream;

public enum WithDirectoryFiltersMandates
{
    investorName("Investor name"),
    investorType("Investor type"),
    investorLocation("Investor location"),
    investorAuM("Investor AuM (m)"),
    assetClass("Asset class"),
    primaryStrategy("Primary strategy"),
    secondaryStrategy("Secondary Strategy"),
    industry("Industry"),
    investmentApproach("Investment approach"),
    marketCap("Market cap"),
    benchmark("Benchmark"),
    investmentRegion("Investment region"),
    leadConsultantName("Lead consultant name"),
    searchConsultantName("Search consultant name"),
    mandateStatus("Mandate status"),
    mandateSubStatus("Mandate sub-status"),
    mandateAttributes("Mandate attributes"),
    serviceMandate("Service mandate"),
    mandateSize("Mandate size (m)"),
    managerName("Manager name"),
    managerStatus("Manager status"),
    entryDate("Entry date");

    public FindOptions findOptions;

    public final String value;

    WithDirectoryFiltersMandates(String option)
    {
        this.value = option;
    }

    public static List<String> getAllFilters()
    {
        return Stream.of(WithDirectoryFiltersMandates.values()).map(e -> e.value).toList();
    }
}
