package com.tests.enums.platform;

public enum ExportTemplates
{
    //investor templates
    basicDetails("Basic Details"),
    investorsWithAssetAllocation("Investors with Asset Allocation"),
    investorsWithManagerRoster("Investors with Manager Roster"),
    investorsWithConsultantRoster("Investors with Consultant Roster"),
    investorsWithFullContacts("Investors with Full Contacts"),

    //manager templates
    mandateDetails("Mandate Details"),
    managersHiredDetails("Managers Hired Details"),
    onWatchTerminatedDetails("On Watch & Terminated Details"),
    allFields("All Fields"),

    //fund templates
    fundOverview("Fund Overview"),

    //mandates templates
    managerOverview("Manager Overview"),
    managerContacts("Manager Contacts"),

    //bencmark templates
    industryBenchmark("Industry Benchmark"),
    benchmarkConstituents("Benchmark Constituents");

    public final String value;

    ExportTemplates(String option)
    {
        this.value = option;
    }
}
