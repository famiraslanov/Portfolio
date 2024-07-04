package com.tests.ui.platform.hedge.explore.filters;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.tests.enums.platform.filters.HedgeFundFiltersFunds;
import com.tests.helpers.platform.FilterHelper;
import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import org.testng.annotations.Test;

public class FundsFiltersTests extends HedgeMain
{
    @CoverageInfo(details = "Verify filters list for Funds")
    @Test
    public void fundsFiltersTests()
    {
        FilterHelper.verifyFilterIsDisplayed(AssetClass.hedge, EntityCard.funds, HedgeFundFiltersFunds.getAllFilters());
    }
}
