package com.library.helpers;

import com.library.Log;
import com.library.listeners.QASuiteListener;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberHelper
{
    private final String testOutputLine = "_".repeat(90);

    public static String extractDigits(String string)
    {
        string = string.replace(",", "");
        String dynamicPart = "";
        Pattern pattern = Pattern.compile("(?:^-|/-|\n-|)\\d+[.]?\\d*");
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            dynamicPart = matcher.group(0);
            dynamicPart = dynamicPart.replace("/", "");
        }
        return dynamicPart;
    }

    public static Double extractDouble(String string)
    {
        debugOutput("Extract a Double from [" + string + "]");

        if (Objects.equals(string, "-")) {
            debugOutput("String is [-]. Special case return 0");
            return 0d;
        }

        try {
            return Double.parseDouble(extractDigits(string));
        } catch (Exception e) {
            return null;
        }
    }

    public static Integer extractInt(String string)
    {
        debugOutput("Extract an Integer from [" + string + "]");
        try {
            String digitsFound = extractDigits(string);
            if (digitsFound.contains(".")) {
                digitsFound = digitsFound.substring(0, digitsFound.indexOf("."));
            }
            return Integer.parseInt(digitsFound);
        } catch (Exception e) {
            return null;
        }
    }

    private static void debugOutput(String message)
    {
        if (QASuiteListener.settings == null) {
            System.out.println("DEBUG: " + message);
        } else {
            Log.debug(message);
        }
    }

    public static double round(double value, int decimalPlaces)
    {
        if (decimalPlaces < 0) {
            return value;
        }

        BigDecimal bigDecimal = new BigDecimal(Double.toString(value));
        bigDecimal = bigDecimal.setScale(decimalPlaces, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    @Test(dataProvider = "extractIntDigitsUnitTest")
    public void extractIntDigitsUnitTest(String stringValue, Integer expected)
    {
        Integer found = extractInt(stringValue);
        System.out.println(testOutputLine);
        System.out.println("String    : " + stringValue);
        System.out.println("Expected  : " + expected);
        System.out.println("Found     : " + found);
        System.out.println(testOutputLine);
        System.out.println("");
        Assert.assertEquals(found, expected, "Digits not parsed correctly");
    }

    @DataProvider(name = "extractIntDigitsUnitTest")
    public Object[][] extractIntDigitDataDataProvider()
    {
        return new Object[][]{
                {"-123", -123},
                {"ABC-123", 123},
                {"Some text mid way through the 123 string", 123},
                {"http://link.com/-123", -123},
                {"http://link.com/123", 123},
                {"", null},
                {"Same text 10,709.00 something", 10709}
        };
    }

    @Test(dataProvider = "extractDoubleDigitDataDataProvider")
    public void extractDoubleDigitsUnitTest(String stringValue, Double expected)
    {
        Double found = extractDouble(stringValue);
        System.out.println(testOutputLine);
        System.out.println("String    : " + stringValue);
        System.out.println("Expected  : " + expected);
        System.out.println("Found     : " + found);
        System.out.println(testOutputLine);
        System.out.println("");
        Assert.assertEquals(found, expected, "Digits not parsed correctly");
    }

    @DataProvider(name = "extractDoubleDigitDataDataProvider")
    public Object[][] extractDoubleDigitDataDataProvider()
    {
        return new Object[][]{
                {"-123", -123d},
                {"ABC-123", 123d},
                {"Some text mid way through the 123 string", 123d},
                {"http://link.com/-123", -123d},
                {"http://link.com/123", 123d},
                {"+23.3", 23.3d},
                {"-23.3", -23.3d},
                {"+20%", 20d},
                {"", null},
                {"-", 0d},
                {"test\n-34.4%", -34.4d},
                {"Some value 10,709.1", 10709.1d},
                {"Some value 10,709.0", 10709d}
        };
    }
}
