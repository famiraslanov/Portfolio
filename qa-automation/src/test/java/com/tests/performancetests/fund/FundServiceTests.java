package com.tests.performancetests.fund;

import com.library.Log;
import com.library.PerformanceRunner;
import com.library.classes.PerformanceTestObject;
import com.tests.pageobjects.baseobjects.PerformanceMain;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

public class FundServiceTests extends PerformanceMain
{
    @Test(dataProvider = "fundDataProvider")
    public void testFundGroup1(String dataSet, String endpoint)
    {
        Log.notRunProd(prodNotRunText);

        new PerformanceRunner().run(PerformanceTestObject.builder()
                .baseUrl("https://fund-stage.dom-non-prod.with.digital")
                .endpoint(endpoint)
                .bearer("EdFeRegPttqsedG5bvRSfNSjspEHTC4JvdZvnEUg8ExG5BXDBdbaSjA9ER5o3D3m")
                .numberOfThreads(10)
                .duration(Duration.ofSeconds(10))
                .tolerancePercentage(0.3)
                .build()
        );
    }

    @DataProvider(name = "fundDataProvider")
    public Object[][] fundDataProvider()
    {
        return new Object[][]{
                {"funds1", "/api/funds?page=1&itemsPerPage=22&id%5B0%5D=120227&id%5B1%5D=132134&id%5B2%5D=132135&id%5B3%5D=132136&id%5B4%5D=132137&id%5B5%5D=132138&id%5B6%5D=132139&id%5B7%5D=132140&id%5B8%5D=132141&id%5B9%5D=39893&id%5B10%5D=46852&id%5B11%5D=41870&id%5B12%5D=16766&id%5B13%5D=209739&id%5B14%5D=209740&id%5B15%5D=209861&id%5B16%5D=212983&id%5B17%5D=212984&id%5B18%5D=55736&id%5B19%5D=272444&id%5B20%5D=37983&id%5B21%5D=284053"},
                {"funds2", "/api/funds?page=1&itemsPerPage=13&id%5B0%5D=342928&id%5B1%5D=342930&id%5B2%5D=344144&id%5B3%5D=348462&id%5B4%5D=351424&id%5B5%5D=354469&id%5B6%5D=354738&id%5B7%5D=355865&id%5B8%5D=355866&id%5B9%5D=355975&id%5B10%5D=356350&id%5B11%5D=363942&id%5B12%5D=365953"}
        };
    }
}
