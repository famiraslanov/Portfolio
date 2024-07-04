package com.tests.performancetests.investor;

import com.library.Log;
import com.library.PerformanceRunner;
import com.library.classes.PerformanceTestObject;
import com.tests.pageobjects.baseobjects.PerformanceMain;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

public class InvestorServiceTests extends PerformanceMain
{
    @Test(dataProvider = "investorDataProvider")
    public void testInvestorGroup(String dataSet, String endPoint)
    {
        Log.notRunProd(prodNotRunText);

        new PerformanceRunner().run(PerformanceTestObject.builder()
                .baseUrl("https://investor-stage.dom-non-prod.with.digital")
                .endpoint(endPoint)
                .bearer("YLtshb7U2PCXmKVTA8dhfG4idzhYEoYNSfwCZzosqZLBDLAug93LEvTA8scTs9QD")
                .numberOfThreads(10)
                .duration(Duration.ofSeconds(10))
                .build()
        );
    }

    @DataProvider(name = "investorDataProvider")
    public Object[][] investorDataProvider()
    {
        return new Object[][]{
                {"asset-mandate-histories", "/api/asset-mandate-histories?page=1&itemsPerPage=13&id%5B0%5D=142548&id%5B1%5D=142550&id%5B2%5D=35194&id%5B3%5D=47875&id%5B4%5D=154342&id%5B5%5D=62778&id%5B6%5D=158684&id%5B7%5D=160073&id%5B8%5D=65815&id%5B9%5D=160210&id%5B10%5D=66759&id%5B11%5D=170372&id%5B12%5D=173087"},
                {"investors", "/api/investors?page=1&itemsPerPage=1&id%5B0%5D=524435248"},
                {"asset-mandate-management-firms", "/api/asset-mandate-management-firms?page=1&itemsPerPage=13&id%5B0%5D=145553&id%5B1%5D=45186&id%5B2%5D=44507&id%5B3%5D=48498&id%5B4%5D=147183&id%5B5%5D=55190&id%5B6%5D=156517&id%5B7%5D=57632&id%5B8%5D=147720&id%5B9%5D=57628&id%5B10%5D=57630&id%5B11%5D=65363&id%5B12%5D=148601"},
                {"investors", "/api/investors?page=1&itemsPerPage=1&id%5B0%5D=524435248"},
                {"investor-aums", "/api/investor-aums?page=1&itemsPerPage=1&id%5B0%5D=14808"}
        };
    }
}
