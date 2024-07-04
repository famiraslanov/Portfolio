package com.tests.enums.platform;


public enum ThirdLevelNavigationItem
{
    performance("Performance"),
    markets("Markets"),
    strategy("Strategy"),
    shorting("Shorting"),
    leagueTables("League Tables"),
    aumGrowthTracker("AuM Growth Tracker"),
    brandNamesTracker("Brand Names Tracker"),
    managerPeopleMoves("Manager People Moves"),
    investorPeopleMoves("Investor People Moves");

    public final String text;

    ThirdLevelNavigationItem(String text)
    {
        this.text = text;
    }
}
