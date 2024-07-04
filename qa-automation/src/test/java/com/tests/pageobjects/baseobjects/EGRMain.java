package com.tests.pageobjects.baseobjects;

import com.library.QuickWatchObject;
import com.library.enums.QuickWatchAction;
import com.tests.helpers.egr.NavigationHelper;
import com.tests.pageobjects.egr.HomePage;
import com.library.Main;
import org.openqa.selenium.By;

public class EGRMain extends Main
{

    public EGRMain()
    {
        super("EGR");
        addBaseQuickWatch(QuickWatchObject.builder()
                .uniqueName("ActivationModal")
                .holderLocator(By.cssSelector(".activation-modal-container"))
                .action(QuickWatchAction.click)
                .actionElementLocator(By.cssSelector("button"))
                .build());
    }

    public static HomePage navToStart()
    {
        return NavigationHelper.startingPage();
    }

    public static HomePage navigateAndLogin()
    {
        return NavigationHelper.navigateAndLogin();
    }
}
