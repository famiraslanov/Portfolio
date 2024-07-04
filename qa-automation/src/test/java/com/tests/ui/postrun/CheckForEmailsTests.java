package com.tests.ui.postrun;

import com.library.enums.DriverType;
import com.library.helpers.EmailHelper;
import com.tests.pageobjects.baseobjects.MiscMain;
import org.testng.annotations.Test;

public class CheckForEmailsTests extends MiscMain
{
    public CheckForEmailsTests()
    {
        super();
        driverType = DriverType.none;
    }

    @Test
    public void testCheckForEmails()
    {
        EmailHelper.checkForEmails();
    }
}
