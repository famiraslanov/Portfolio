package com.tests.pageobjects.baseobjects;

import com.library.*;
import com.library.helpers.Function;
import com.library.helpers.HtmlSourceHelper;
import com.library.helpers.NumberHelper;
import com.library.helpers.ScreenshotHelper;
import com.library.listeners.QASuiteListener;
import com.tests.pageobjects.platform.BenchmarkProfilePage;
import com.tests.pageobjects.platform.PlatformArticlePage;
import com.tests.pageobjects.platform.IndexPage;
import com.tests.pageobjects.platform.ProfilePage;
import com.tests.quickwatch.QuickWatchItems;
import org.openqa.selenium.By;

public class BasePageObject
{
    private String pageName;

    public BasePageObject()
    {
        pageName = this.getClass().getSimpleName();
        addQuickWatch(QuickWatchItems.pendoClose());
        addQuickWatch(QuickWatchItems.pendoButton());
    }

    public void correctPage(By locator)
    {
        correctPage(Find.options().locator(locator).visible(true).checkForNoSpinner(true).maxWaitMS(Store.getSettings().getDefaultTimeoutMS() * 2));
    }

    public void correctPage(FindOptions options)
    {
        Log.story("Verify the correct page is: " + pageName);
        Verify.isFound(options, "Correct page not found for " + pageName);

        if (QASuiteListener.settings.getCorrectPageScreenshots()) {
            ScreenshotHelper.take(pageName);
            HtmlSourceHelper.save(pageName);
        }
    }

    public void addQuickWatch(QuickWatchObject quickWatchObject)
    {
        Store.addQuickWatch(quickWatchObject);
    }

    public int getId()
    {
        Log.story("Get entity id");
        if (this.getClass().equals(PlatformArticlePage.class)) {
            return Integer.parseInt(Function.getCurrentUrl().split("/?=")[1]);
        } else if (this.getClass().equals(IndexPage.class)) {
            return NumberHelper.extractInt(Function.getCurrentUrl().split("&typeId")[0]);
        } else if (this.getClass().equals(ProfilePage.class) || this.getClass().equals(BenchmarkProfilePage.class)) {
            return NumberHelper.extractInt(Function.getCurrentUrl());
        }
        return 0;
    }
}
