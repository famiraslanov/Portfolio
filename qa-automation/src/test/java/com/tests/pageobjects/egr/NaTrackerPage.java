package com.tests.pageobjects.egr;

import com.library.Find;
import com.library.Log;
import com.library.Verify;
import com.library.helpers.Function;
import com.tests.pageobjects.baseobjects.BasePageObject;
import org.openqa.selenium.By;

public class NaTrackerPage extends BasePageObject
{
    private By infogramBlock = By.cssSelector(".egr-north-america-tracker");
    private By mapUSBlock = By.cssSelector("[data-lookup='map-content']");
    private By mapFrame = By.cssSelector(".egr-north-america-tracker iframe:nth-of-type(1)");
    private By belowMap = By.cssSelector(".egr-north-america-tracker iframe:nth-of-type(2)");

    public NaTrackerPage()
    {
        super();
        correctPage(infogramBlock);
    }

    public void verifyThatUSMapIsDisplayed()
    {
        Log.story("Verify that infogram map is displayed ");
        Function.slowEnvironmentWait();
        Find.scrollToElement(Find.element(belowMap)); // Map doesn't load unless the iframe is in view
        Function.slowEnvironmentWait();
        Function.switchFrame(Find.options().locator(mapFrame));
        Verify.isFound(mapUSBlock, "US Map infography is not displayed");
        Function.switchDefaultFrame();
    }
}

