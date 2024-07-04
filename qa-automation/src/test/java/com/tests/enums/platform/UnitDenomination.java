package com.tests.enums.platform;

public enum UnitDenomination
{
    billion(1000000000),
    million(1000000);

    private double factor;

    UnitDenomination(double factor)
    {
        this.factor = factor;
    }

    public static double convert(double value, UnitDenomination inputDenomination, UnitDenomination outputDenomination)
    {
        return (value * inputDenomination.factor) / outputDenomination.factor;
    }
}
