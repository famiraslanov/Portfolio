package com.tests.performancetests.manager;

import com.library.Log;
import com.library.PerformanceRunner;
import com.library.classes.PerformanceTestObject;
import com.tests.pageobjects.baseobjects.PerformanceMain;
import org.testng.annotations.Test;

import java.time.Duration;

public class ManagerServiceTests extends PerformanceMain
{
    @Test
    public void testManagerGroup()
    {
        Log.notRunProd(prodNotRunText);

        new PerformanceRunner().run(PerformanceTestObject.builder()
                .baseUrl("https://manager-stage.dom-non-prod.with.digital")
                .endpoint("/api/management-firms?page=1&itemsPerPage=17&id[0]=2145848573&id[1]=-1894110496&id[2]=1234496423&id[3]=27617499&id[4]=-1853929557&id[5]=2145851082&id[6]=-1069036568&id[7]=2145858758&id[8]=2145851773&id[9]=2145859307&id[10]=2145858391&id[11]=2145858316&id[12]=2145845676&id[13]=2145867346&id[14]=2145866482&id[15]=2145846170&id[16]=2145863357")
                .bearer("a59G7FY4SUhq4PnVD65jtwmMVWvUqsuEsTakv6hHp3ma62E4DX4Txwu3MEjaRp7G")
                .numberOfThreads(10)
                .duration(Duration.ofSeconds(10))
                .build()
        );
    }
}
