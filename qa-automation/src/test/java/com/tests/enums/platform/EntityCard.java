package com.tests.enums.platform;

import com.library.Find;
import com.library.FindOptions;
import com.library.classes.FindByText;
import org.openqa.selenium.By;

public enum EntityCard
{
    investors("Investors", "Investor type", "Investor location"),
    mandates("Mandates", "Investor type","Investor location"),
    funds("Funds","Manager name","Manager location"),
    managers("Managers", "Manager name", "Manager location"),
    consultants("Consultants","Consultant name", "Consultant location"),
    documentLibrary("Document Library", "Investor name", "Document type"),
    people("People", "Name", "Role"),
    fundPerformance("performance", "Fund name","Manager name"),
    industryBenchmarks("Industry benchmarks", "Fund name", "Manager name"),
    customBenchmarks("Custom benchmarks", "Fund name", "Manager name");

    public final FindOptions button;
    public final FindOptions tab;
    public final String value;
    public final String dataPoint1Text;
    public final String dataPoint2Text;

    EntityCard(String text, String dataPoint1Text, String dataPoint2Text)
    {
        this.value = text;
        this.button = Find.options().locator(By.cssSelector("[class*='EntityListstyled'] > div[label], [class^='IWowSecondaryNavigationstyled__DivLink-']")).maxWaitMS(30000).findByText(FindByText.by().contains(text)).scrollTo(true);
        this.tab = Find.options().locator(By.cssSelector("a[id*='" + text + "']"));
        this.dataPoint1Text = dataPoint1Text;
        this.dataPoint2Text = dataPoint2Text;
    }
}