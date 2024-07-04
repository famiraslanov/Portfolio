package com.tests.enums.platform.userSettings;

public enum JobFunction
{
    none("Add your job function"),
    academiaLibraryServices("Academia/Library Services"),
    analystResearcher("Analyst/Researcher"),
    clientRelationsServices("Client Relations/Services"),
    marketingCommunicationsPR("Marketing/Communications/PR"),
    complianceLegal("Compliance/Legal"),
    consultancy("Consultancy"),
    finance("Finance"),
    fundPortfolioInvestmentManagement("Fund/Portfolio/Investment Management"),
    gheneralAdministration("General Administration"),
    gheneralManagement("General Management"),
    insurance("Insurance"),
    investor("Investor"),
    operations("Operations"),
    other("Other"),
    risk("Risk"),
    salesBusinessDevelopment("Sales/Business Development"),
    tech("Tech"),
    marketDataResearcher("Market/Data Researcher"),
    productDevelopment("Product Development");

    public String name;

    JobFunction(String name)
    {
        this.name = name;
    }

    public static JobFunction getEnumByName(String name) {
        for (JobFunction jobFunction : JobFunction.values()) {
            if (jobFunction.name.equals(name)) {
                return jobFunction;
            }
        }
        return JobFunction.none;
    }
}
