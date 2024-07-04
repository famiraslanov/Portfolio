package com.tests.enums.platform.userSettings;

public enum Seniority
{
    none("Add your seniority"),
    CLevel("C Level"),
    seniorManagement("Senior Management"),
    management("Management"),
    associateAnalyst("Associate/Analyst");

    public String name;

    Seniority(String name)
    {
        this.name = name;
    }

    public static Seniority getEnumByName(String name)
    {
        for (Seniority seniority : Seniority.values()) {
            if (seniority.name.equals(name)) {
                return seniority;
            }
        }
        return Seniority.none;
    }
}
