package com.tests.ui.platform.hedge.profile;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.tests.enums.platform.SettingsLinks;
import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.forms.ProfilesSettingForm;
import com.tests.pageobjects.platform.forms.TopNavigationMenu;
import com.tests.pageobjects.platform.*;
import org.testng.annotations.Test;


public class FollowProfileTests extends HedgeMain
{

    @CoverageInfo(details = "Verify follow and unfollow profile using my account page")
    @Test
    public void followHedgeProfileActionTest()
    {
        AssetClass assetClass = AssetClass.hedge;

        StartingPage startingPage = navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(assetClass);

        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.managers);
        String selectMandate = entityListingPage.getInvestorName(3);
        ProfilePage profilePage = entityListingPage.clickOnRowByName(selectMandate);
        profilePage.clickFollow();

        SettingPage settingPage = TopNavigationMenu.getInstance().navigateToSettings();
        settingPage.goToLink(SettingsLinks.profiles);

        ProfilesSettingForm profilesSettingForm = new ProfilesSettingForm();
        profilesSettingForm.verifyStatusOfProfileFollow(selectMandate, EntityCard.managers, false);

        profilesSettingForm.followProfile(selectMandate, EntityCard.managers, false);
        TopNavigationMenu.getInstance().navigateToExplore(assetClass);
        entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.managers);
        profilePage = entityListingPage.clickOnRowByName(selectMandate);
        profilePage.verifyFollowState(false);
    }
}
