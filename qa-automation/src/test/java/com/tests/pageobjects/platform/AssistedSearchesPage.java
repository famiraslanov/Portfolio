package com.tests.pageobjects.platform;

import com.library.Log;
import com.library.Verify;
import com.library.helpers.Function;
import com.tests.pageobjects.baseobjects.BasePageObject;
import org.openqa.selenium.By;

public class AssistedSearchesPage extends BasePageObject
{
    private final By pageLoadIdentifier = By.cssSelector("a[title='CAMRADATA']");

    public AssistedSearchesPage()
    {
        super();
        Function.switchWindow(null);
        correctPage(pageLoadIdentifier);
    }

    public void verifyThatAssistedSearchesIsOpen()
    {
        Log.story("Verify that Assisted Searches page is open");
        Verify.contains(Function.getCurrentUrl(), "https://www.camradata.com/assisted-search-registration", "Assisted Searches pag is not opened ");
    }
}
