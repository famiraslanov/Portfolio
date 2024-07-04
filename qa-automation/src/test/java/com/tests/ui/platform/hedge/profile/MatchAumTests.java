package com.tests.ui.platform.hedge.profile;

import com.google.common.collect.Iterables;
import com.library.annotations.CoverageInfo;
import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.DataSettings;
import com.tests.enums.platform.EntityCard;
import com.tests.enums.platform.QuartileFilter;
import com.tests.helpers.platform.NavigationHelper;
import com.tests.helpers.platform.SearchApiHelper;
import com.tests.helpers.platform.models.FundApiModel;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.baseobjects.WithDirectoryMain;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.ProfilePage;
import com.tests.pageobjects.platform.forms.components.ProfileTableComponent;
import org.testng.annotations.Test;

import java.util.List;

public class MatchAumTests extends HedgeMain
{
    @CoverageInfo(details = "Verify that latest Aum values is match with api")
    @Test
    public void matchLatestAumTests()
    {
        ExplorePage explorePage = NavigationHelper.loginAndNavigateToExplore(AssetClass.hedge);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.funds);
        entityListingPage.selectFilters(List.of("Last year"));
        entityListingPage.applyRangeFilter("Last year", QuartileFilter.quartile4.name);
        ProfilePage fundPage = entityListingPage.getInvestorWithBlock(List.of("Fund assets under management (USD/m)", "Strategy assets under management (USD/m)"));
        int fundId = fundPage.getId();

        FundApiModel.Result fundApi = SearchApiHelper.getFundData(fundId).get(0);
        fundPage.verifyAUMinTable("Fund assets under management (USD/m)", "Fund AUM", fundApi.getLatestAum());
        fundPage.verifyAUMinTable("Strategy assets under management (USD/m)", "Strategy AUM", fundApi.getLatestStrategyAum());
    }
}
