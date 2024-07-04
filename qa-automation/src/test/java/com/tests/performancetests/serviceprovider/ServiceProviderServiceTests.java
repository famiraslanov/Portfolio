package com.tests.performancetests.serviceprovider;

import com.library.Log;
import com.library.PerformanceRunner;
import com.library.classes.PerformanceTestObject;
import com.tests.pageobjects.baseobjects.PerformanceMain;
import org.testng.annotations.Test;

import java.time.Duration;

public class ServiceProviderServiceTests extends PerformanceMain
{
    @Test
    public void testServiceProviderGroup()
    {
        Log.notRunProd(prodNotRunText);

        new PerformanceRunner().run(PerformanceTestObject.builder()
                .baseUrl("https://service-provider-stage.dom-non-prod.with.digital")
                .endpoint("/api/service-providers?page=1&itemsPerPage=1&id[0]=-1267614919")
                .bearer("sUFF67LVXW32Re6egN4p4uyWaDq6ZDXK4qnQmAdgL8P57bWkJ6XkzPhPKwYeVsXV")
                .numberOfThreads(10)
                .duration(Duration.ofSeconds(10))
                .build()
        );
    }
}
