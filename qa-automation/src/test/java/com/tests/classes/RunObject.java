package com.tests.classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.library.Settings;


@JsonIgnoreProperties(ignoreUnknown = true)
public class RunObject
{
    public String logType = "StartSuite";
    public Integer userId;
    public String environment;

    public RunObject(Settings settings)
    {
        userId = settings.getResultsUserId();
        environment = settings.getEnvironment().toString();
    }
}
