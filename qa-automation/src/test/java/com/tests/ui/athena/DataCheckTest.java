package com.tests.ui.athena;

import com.tests.helpers.athena.AthenaHelper;
import com.tests.pageobjects.baseobjects.AthenaMain;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class DataCheckTest extends AthenaMain
{
    /*
        PLEASE NOTE:

        These tests are not intended to be run daily. They are excluded from the XML files
        This is a stub for a one-off test case... committed as it might be usefull for future testing
     */

    @Test
    public void dataCheckTest()
    {
        AthenaHelper.login();

        for (Map.Entry<String, Object> line : loadData().entrySet()) {
            AthenaHelper.loadId(line.getKey());
            verify(line.getValue());
        }
    }

    private Map<String, Object> loadData()
    {
        // Do something to load the data from Excel

        return new HashMap<>()
        {{
            put("4ad48554-b499-ee11-be37-6045bd0c1d6f", new Object());
        }};
    }

    private void verify(Object data)
    {
        // Do some verification with the data object
    }
}
