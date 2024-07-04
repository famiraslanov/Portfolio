package com.tests.helpers.platform;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.ColumnName;
import com.tests.enums.platform.EntityCard;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;

import java.util.List;

public class ConsultantLocationFilterHelper
{
    public static void filter(String filterSearchText)
    {
        String filterName = "Consultant location";

        ExplorePage explorePage = NavigationHelper.loginAndNavigateToExplore(AssetClass.traditional);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.consultants);
        entityListingPage.selectFilters(List.of(ColumnName.consultantLocation.headerText));
        entityListingPage.applyFilter(filterName, filterSearchText);

        List<String> selectedValues = entityListingPage.getListOfAllSelectedInternalFilterOptions(filterName, filterSearchText);
        entityListingPage.verifyFilterApplied(ColumnName.consultantLocation, selectedValues);
    }
}
