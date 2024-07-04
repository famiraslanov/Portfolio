package com.tests.performancetests.classification;

import com.library.Log;
import com.library.PerformanceRunner;
import com.library.classes.PerformanceTestObject;
import com.tests.pageobjects.baseobjects.PerformanceMain;
import org.testng.annotations.Test;

import java.time.Duration;

public class ClassificationServiceTests extends PerformanceMain
{
    @Test
    public void testClassificationGroup()
    {
        Log.notRunProd(prodNotRunText);

        new PerformanceRunner().run(PerformanceTestObject.builder()
                .baseUrl("https://classification-stage.dom-non-prod.with.digital")
                .endpoint("/api/asset-classes?page=1&itemsPerPage=4&pagination=false&id[0]=1&id[1]=6&id[2]=12&id[3]=13")
                .bearer("SuRRB6u7PfB9ezkQstBiZ9nZMgyNKbYWFZVESGqKQLHkAeg3eXrqTKJU3zeCBGiy")
                .numberOfThreads(10)
                .duration(Duration.ofSeconds(10))
                .build()
        );
    }
}
