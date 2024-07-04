package com.tests.pageobjects.platform.forms.components;

import com.library.Find;
import com.library.Log;
import com.library.Verify;
import com.library.classes.FindByText;
import com.library.helpers.StringHelper;
import com.tests.pageobjects.baseobjects.BasePageObject;
import org.openqa.selenium.By;

public class ProfileComponent extends BasePageObject
{
    private final By basicLocator;
    private final By exportButton = By.cssSelector("button[class*='export-button']");
    private final By exportOptions = By.cssSelector("[class*='ExportPopoverstyled__ViewByListItem']");

    public ProfileComponent(String section)
    {
        super();
        basicLocator = By.cssSelector("[id*='" + StringHelper.toKebabCase(section) + "']");
        correctPage(Find.options().locator(this.basicLocator).returnFirst(true));
    }

    public void clickOnExport()
    {
        Log.story("Click on export button for table");
        Find.click(Find.options().locator(exportButton)
                .scrollTo(true)
                .visible(true)
                .clickable(true)
                .parentLocator(this.basicLocator)
                .checkForNoSpinner(true));
    }

    public void clickOnExportWithOption(String option)
    {
        clickOnExport();
        Find.click(Find.options().locator(exportOptions).findByText(FindByText.by().contains(option)));
    }

    public void verifyThatFileDownload(String indexName, String fileExtension)
    {
        Verify.isFileDownloaded(indexName, fileExtension);
    }

}
