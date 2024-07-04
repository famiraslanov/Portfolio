package com.tests.enums.platform;

public enum ExportDateFormat
{
    dayFirst("DD/MM/YYYY","yyyyMMdd"),
    monthFirst("MM/DD/YYYY", "yyyyddMM");

    public final String buttonText;
    public final String dateFormatString;
    ExportDateFormat(String buttonText, String dateFormatString)
    {
        this.buttonText =buttonText;
        this.dateFormatString = dateFormatString;
    }
}
