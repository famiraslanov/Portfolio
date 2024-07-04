package com.tests.ui.platform.hedge.explore.dashboardtabcounts;

import com.tests.enums.platform.EntityCard;
import com.tests.helpers.platform.HedgeHelper;
import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import org.testng.annotations.Test;

public class ConsultantsTabCountTests extends HedgeMain
{
    @CoverageInfo(details = "Verify count of consultants on explore dashboard and listing")
    @Test
    public void consultantsTabCountTest()
    {
        HedgeHelper.compareNumberOfEntityTest(this, EntityCard.consultants);
    }
}
