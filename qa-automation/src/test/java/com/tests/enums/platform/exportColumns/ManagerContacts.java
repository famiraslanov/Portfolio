package com.tests.enums.platform.exportColumns;

import java.util.List;
import java.util.stream.Stream;

public enum ManagerContacts
{
    managerId("Manager ID"),
    manager("Manager"),
    state("State"),
    city("City"),
    country("Country"),
    fullName("Full Name"),
    role("Role"),
    primaryPhone("Primary Phone"),
    primaryEmail("Primary Email"),
    linkedIn("LinkedIn"),
    socialMedia("Social Media"),
    managerPrimaryIndustries("Manager Primary Industries"),
    managerSecondaryIndustries("Manager Secondary Industries"),
    managerAssetClass("Manager Asset Class"),
    managerPrimaryStrategies("Manager Primary Strategies"),
    managerSecondaryStrategies("Manager Secondary Strategies"),
    managerApproach("Manager Approach(es)"),
    primeBrokers("Prime Broker(s)"),
    administrators("Administrator(s)"),
    auditors("Auditor(s)"),
    custodians("Custodian(s)"),
    legalAdvisors("Legal Advisor(s)");


    public final String value;

    ManagerContacts(String option)
    {
        this.value = option;
    }

    public static List<String> getAllColumns()
    {
        return Stream.of(ManagerContacts.values()).map(e -> e.value).toList();
    }
}
