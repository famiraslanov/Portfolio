package com.tests.pageobjects.baseobjects;

import com.library.Main;
import com.tests.enums.platform.AssetClass;
import com.tests.helpers.platform.NavigationHelper;
import com.tests.pageobjects.platform.StartingPage;

public class PrivateEquityMain extends Main {
    public PrivateEquityMain() {
        super("PrivateEquity");
    }
    public StartingPage navToStart()
    {
        return NavigationHelper.loginAndNavigateTo(AssetClass.privateEquity);
    }
}
