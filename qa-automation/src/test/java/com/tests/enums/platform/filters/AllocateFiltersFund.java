package com.tests.enums.platform.filters;

import com.library.FindOptions;

import java.util.List;
import java.util.stream.Stream;

public enum AllocateFiltersFund
{
    fundName("Fund name"),
    FundAuM("Fund AuM (m)"),
    fundStructure("Fund structure"),
    inceptionDate("Inception date"),
    vintage("Vintage"),
    domicile("Domicile"),
    oenToInvestment("Open to Investment"),
    lifecycleStatus("Status"),
    managerName("Manager name"),
    managerLocation("Manager location"),
    managerAuM("Manager AuM (m)"),
    mainOffice("Main office"),
    assetClass("Asset class"),
    companySizeFocus("Company size focus"),
    primaryStrategy("Primary strategy"),
    secondaryStrategy("Secondary strategy"),
    sector("Sector"),
    industry("Industry"),
    approach("Approach"),
    investmentRegion("Investment region"),
    managementFees("Management fees"),
    performanceFees("Performance fees"),
    hurdleRate("Hurdle rate"),
    highWaterMark("High water mark"),
    dateAdded("Date added");

    public FindOptions findOptions;

    public final String value;

    AllocateFiltersFund(String option)
    {
        this.value = option;
    }

    public static List<String> getAllFilters()
    {
        return Stream.of(AllocateFiltersFund.values()).map(e->e.value).toList();
    }
}
