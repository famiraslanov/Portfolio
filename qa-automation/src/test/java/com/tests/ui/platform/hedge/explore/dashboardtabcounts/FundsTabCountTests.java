package com.tests.ui.platform.hedge.explore.dashboardtabcounts;

import com.tests.enums.platform.EntityCard;
import com.tests.helpers.platform.HedgeHelper;
import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import org.testng.annotations.Test;

public class FundsTabCountTests extends HedgeMain
{
    @CoverageInfo(details = "Verify count of funds on explore dashboard and listing")
    @Test
    public void fundsTabCountTest()
    {
        HedgeHelper.compareNumberOfEntityTest(this, EntityCard.funds);
    }
}
