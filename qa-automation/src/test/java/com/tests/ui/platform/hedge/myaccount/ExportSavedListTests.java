package com.tests.ui.platform.hedge.myaccount;

import com.tests.enums.platform.*;
import com.tests.helpers.platform.ExportSavedListHelper;
import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import org.testng.annotations.Test;

public class ExportSavedListTests extends HedgeMain
{
    @CoverageInfo(details = "Verify export saved list in CSV")
    @Test
    public void exportSavedListCSVTests()
    {
        ExportSavedListHelper.runner(
                AssetClass.hedge,
                EntityCard.investors,
                ExportTemplates.basicDetails,
                ExportDateFormat.dayFirst,
                ExportFileFormat.csv);
    }

    @CoverageInfo(details = "Verify export saved list in Excel")
    @Test
    public void exportSavedListExcelTests()
    {
        ExportSavedListHelper.runner(
                AssetClass.hedge,
                EntityCard.managers,
                ExportTemplates.managerOverview,
                ExportDateFormat.monthFirst,
                ExportFileFormat.excel);
    }
}
