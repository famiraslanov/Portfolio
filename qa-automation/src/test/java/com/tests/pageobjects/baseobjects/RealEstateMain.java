package com.tests.pageobjects.baseobjects;

import com.tests.enums.platform.AssetClass;
import com.tests.helpers.platform.NavigationHelper;
import com.library.Main;
import com.tests.pageobjects.platform.StartingPage;

public class RealEstateMain extends Main {
    public RealEstateMain() {
        super("RealEstate");
    }
    public StartingPage navToStart()
    {
        return NavigationHelper.loginAndNavigateTo(AssetClass.realEstate);
    }
}
