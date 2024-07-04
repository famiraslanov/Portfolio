package com.tests.performancetests.lpoutreach;

import com.library.Log;
import com.library.PerformanceRunner;
import com.library.classes.PerformanceTestObject;
import com.tests.pageobjects.baseobjects.PerformanceMain;
import org.testng.annotations.Test;

import java.time.Duration;

public class LpOutReachServiceTests extends PerformanceMain
{
    @Test
    public void testLpOutReachGroup()
    {
        Log.notRunProd(prodNotRunText);

        new PerformanceRunner().run(PerformanceTestObject.builder()
                .baseUrl("https://lp-outreach-stage.dom-non-prod.with.digital")
                .endpoint("/api/submissions?type=investor&itemsPerPage=1&page=1&order[date]=desc&entityId=524435248")
                .bearer("8YTzgstAMLCCdFcsLJJrxVaqZUAabAJSndyM9Yke2hQyoXpiBDBf2obPMpkXLZzM")
                .numberOfThreads(10)
                .duration(Duration.ofSeconds(10))
                .build()
        );
    }
}
