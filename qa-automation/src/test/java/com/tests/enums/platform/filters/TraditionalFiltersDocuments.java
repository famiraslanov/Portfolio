package com.tests.enums.platform.filters;

import com.library.FindOptions;

import java.util.List;
import java.util.stream.Stream;

public enum TraditionalFiltersDocuments
{
    investorName("Investor name"),
    documentType("Document type"),
    assetClass("Asset class"),
    managerName("Manager name"),
    searchConsultantName("Search consultant name"),
    country("Country"),
    documentDate("Document date");

    public FindOptions findOptions;

    public final String value;

    TraditionalFiltersDocuments(String option)
    {
        this.value = option;
    }

    public static List<String> getAllFilters()
    {
        return Stream.of(TraditionalFiltersDocuments.values()).map(e->e.value).toList();
    }
}
