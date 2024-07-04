package com.tests.quickwatch;

import com.library.QuickWatchObject;
import com.library.enums.QuickWatchAction;
import org.openqa.selenium.By;

public class QuickWatchItems
{
    public static QuickWatchObject cookieBanner()
    {
        return QuickWatchObject.builder()
                .uniqueName("cookie_banner")
                .actionElementLocator(By.id("onetrust-accept-btn-handler"))
                .build();
    }

    public static QuickWatchObject pendoClose()
    {
        // Close any that have a close button
        return QuickWatchObject.builder()
                .uniqueName("pendo_popup-close")
                .holderLocator(By.id("pendo-base"))
                .actionElementLocator(By.cssSelector("[aria-label=\"Close\"][id*='pendo']"))
                .action(QuickWatchAction.click)
                .build();
    }

    public static QuickWatchObject pendoButton()
    {
        // Remove any that are still left showing
        return QuickWatchObject.builder()
                .uniqueName("pendo_popup-button")
                .holderLocator(By.id("pendo-base"))
                .action(QuickWatchAction.remove)
                .build();
    }
}
