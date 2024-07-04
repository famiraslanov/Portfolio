package com.tests.enums.platform.filters;

import com.library.FindOptions;

import java.util.List;
import java.util.stream.Stream;

public enum PrivateCreditFiltersConsultants
{
    consultantName("Consultant name"),
    consultantLocation("Consultant location"),
    attribute("Attribute"),
    industry("Industry");

    public FindOptions findOptions;

    public final String value;

    PrivateCreditFiltersConsultants(String option)
    {
        this.value = option;
    }

    public static List<String> getAllFilters()
    {
        return Stream.of(PrivateCreditFiltersConsultants.values()).map(e->e.value).toList();
    }
}
