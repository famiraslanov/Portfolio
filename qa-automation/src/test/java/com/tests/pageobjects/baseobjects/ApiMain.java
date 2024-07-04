package com.tests.pageobjects.baseobjects;

import com.library.Main;
import com.library.enums.DriverType;

public class ApiMain extends Main
{
    public ApiMain()
    {
        super("API");
        driverType = DriverType.none;
    }
}
