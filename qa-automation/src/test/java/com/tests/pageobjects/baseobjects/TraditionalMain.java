package com.tests.pageobjects.baseobjects;

import com.tests.enums.platform.AssetClass;
import com.tests.helpers.platform.NavigationHelper;
import com.library.Main;
import com.tests.pageobjects.platform.StartingPage;

public class TraditionalMain extends Main {
    public TraditionalMain() {
        super("Traditional");
    }
    public StartingPage navToStart()
    {
        return NavigationHelper.loginAndNavigateTo(AssetClass.traditional);
    }
}
