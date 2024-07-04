package com.tests.enums.platform.filters;

import com.library.FindOptions;

import java.util.List;
import java.util.stream.Stream;

public enum HedgeFundFiltersManager
{
    managerName("Manager name"),
    managerLocation("Manager location"),
    managerSecondaryLocation("Manager secondary location"),
    managerAuM("Manager AuM (m)"),
    managerAttributes("Manager attribute"),
    managerType("Manager type"),
    numberOfFunds("Number of funds"),
    fundStructure("Fund structure"),
    primaryStrategy("Primary strategy"),
    secondaryStrategy("Secondary strategy"),
    industry("Industry"),
    approach("Approach"),
    investmentRegion("Investment region"),
    primeBroker("Prime broker"),
    administrator("Administrator"),
    auditor("Auditor"),
    custodian("Custodian"),
    legalAdvisor("Legal advisor");

    public FindOptions findOptions;

    public final String value;

    HedgeFundFiltersManager(String option)
    {
        this.value = option;
    }

    public static List<String> getAllFilters()
    {
        return Stream.of(HedgeFundFiltersManager.values()).map(e -> e.value).toList();
    }
}
