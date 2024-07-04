package com.tests.performancetests.person;

import com.library.Log;
import com.library.PerformanceRunner;
import com.library.classes.PerformanceTestObject;
import com.tests.pageobjects.baseobjects.PerformanceMain;
import org.testng.annotations.Test;

import java.time.Duration;

public class PersonServiceTests extends PerformanceMain
{
    @Test
    public void testPersonGroup()
    {
        Log.notRunProd(prodNotRunText);

        new PerformanceRunner().run(PerformanceTestObject.builder()
                .baseUrl("https://person-stage.dom-non-prod.with.digital")
                .endpoint("/api/people?page=1&itemsPerPage=12&id[0]=1760724&id[1]=1818836&id[2]=1825040&id[3]=1829285&id[4]=1829286&id[5]=1829287&id[6]=1829288&id[7]=1829289&id[8]=1829290&id[9]=1829291&id[10]=1829292&id[11]=1829293")
                .bearer("RdUBn3L5hW7uhFjfg2WM27kbhU6BqDQaznckvyZ7o5biZ6uVcgs9GY7Q2oPeDymC")
                .numberOfThreads(10)
                .duration(Duration.ofSeconds(10))
                .build()
        );
    }
}
