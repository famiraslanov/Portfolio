package com.tests.ui.platform.hedge.explore.search;

import com.tests.helpers.platform.HedgeHelper;
import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import org.testng.annotations.Test;

public class SearchOnExploreTests extends HedgeMain
{
    @CoverageInfo(details = "Verify search for Toronto returns relevant results")
    @Test
    public void searchOnExploreTorontoTest()
    {
        HedgeHelper.searchOnExplore("Toronto");
    }

    @CoverageInfo(details = "Verify search for RUSSEL returns relevant results")
    @Test
    public void searchOnExploreRusselTest()
    {
        HedgeHelper.searchOnExplore("RUSSELL");
    }
}
