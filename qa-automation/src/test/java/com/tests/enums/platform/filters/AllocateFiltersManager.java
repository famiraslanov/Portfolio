package com.tests.enums.platform.filters;

import com.library.FindOptions;

import java.util.List;
import java.util.stream.Stream;

public enum AllocateFiltersManager
{
    managerName("Manager name"),
    managerLocation("Manager location"),
    managerSecondaryLocation("Manager secondary location"),
    managerAuM("Manager AuM"),
    managerAttributes("Manager attributes"),
    managerType("Manager type"),
    numberOfFunds("Number of funds"),
    assetClass("Asset Class"),
    primaryStrategy("Primary Strategy"),
    secondaryStrategy("Secondary Strategy"),
    sector("Sector"),
    approach("Approach"),
    investmentRegion("Investment region"),
    industry("Industry");

    public FindOptions findOptions;

    public final String value;

    AllocateFiltersManager(String option)
    {
        this.value = option;
    }

    public static List<String> getAllFilters()
    {
        return Stream.of(AllocateFiltersManager.values()).map(e->e.value).toList();
    }

}
