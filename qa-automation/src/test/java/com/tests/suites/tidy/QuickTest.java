package com.tests.suites.tidy;

import com.library.Settings;
import com.library.enums.DriverType;
import com.library.helpers.PasswordHelper;
import com.tests.pageobjects.baseobjects.HedgeMain;

public class QuickTest extends HedgeMain
{
    public QuickTest()
    {
        super();
        driverType = DriverType.none;
    }

    // @Test
    public void quickTest()
    {

    }

    // @Test
    public void passwordHelp()
    {
        System.out.println(PasswordHelper.decryptPassword(new Settings().getPasswordKey(),""));
    }
}


