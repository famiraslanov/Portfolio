package com.tests.pageobjects.baseobjects;

import com.tests.enums.platform.AssetClass;
import com.tests.helpers.platform.NavigationHelper;
import com.library.Main;
import com.tests.pageobjects.platform.StartingPage;

public class PrivateCreditMain extends Main {
    public PrivateCreditMain() {
        super("PrivateCredit");
    }
    public StartingPage navToStart()
    {
        return NavigationHelper.loginAndNavigateTo(AssetClass.privateCredit);
    }
}
