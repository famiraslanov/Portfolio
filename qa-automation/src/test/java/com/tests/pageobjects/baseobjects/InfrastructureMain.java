package com.tests.pageobjects.baseobjects;

import com.library.Log;
import com.library.Main;
import com.tests.enums.platform.AssetClass;
import com.tests.helpers.platform.NavigationHelper;
import com.tests.pageobjects.platform.StartingPage;

public class InfrastructureMain extends Main
{
    public InfrastructureMain()
    {
        super("Infrastructure");
    }

    public StartingPage navToStart()
    {
        return NavigationHelper.loginAndNavigateTo(AssetClass.infrastructure);
    }
}
