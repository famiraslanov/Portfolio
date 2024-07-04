package com.tests.pageobjects.baseobjects;

import com.library.Main;
import com.library.enums.DriverType;

public class PerformanceMain extends Main
{
    public String prodNotRunText = "Performance tests are not run against Prod";

    public PerformanceMain()
    {
        super("Performance");
        driverType = DriverType.none;
    }
}
