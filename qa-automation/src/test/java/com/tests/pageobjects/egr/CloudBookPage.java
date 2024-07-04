package com.tests.pageobjects.egr;

import com.library.Find;
import com.library.Log;
import com.library.Verify;
import com.library.helpers.Function;
import com.tests.pageobjects.baseobjects.BasePageObject;
import org.openqa.selenium.By;

public class CloudBookPage extends BasePageObject
{

    private final By pageLoadIdentifier = By.id("pgbk");
    private final By digitalBookPage = By.id("a8c");
    private final By cloudSpinner = By.id("plimg");

    public CloudBookPage()
    {
        super();
        Function.switchWindow(null);
        correctPage(pageLoadIdentifier);
    }

    public void verifyOpeningCloudBook()
    {
        Log.story("Verify opening digital cloud book page");
        Verify.isNotFound(cloudSpinner, "Cloud book is not loaded");
        Verify.isFound(digitalBookPage, "Cloud book is not opened");
    }

}
