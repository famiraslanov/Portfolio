package com.tests.enums.platform;

public enum DataSettings
{
    profilesOnly("profiles only", ""),
    iAndP("Only show profiles with I&P data", "I&P"),
    singleManager("Single Manager", "Single Manager"),
    fundOfHedgeFunds("Fund of Hedge Funds", "Hedge Funds"),
    UCITS("UCITS", "UCITS"),
    masterShareclasses("Master shareclasses", "Master shareclass"),
    BDC("BDC profiles only", "BDC"),
    liquidatedFunds("Liquidated Funds", "Liquidated"),
    activeFunds("Active Funds", ""),
    withFundDossiers("With Fund Dossiers", "Fund Dossier"),
    activelyReporting("Actively Reporting", "Actively Reporting"),
    onlyServiceMandates("Only show Service Mandates", "");

    public final String dropdownOption;
    public final String label;

    DataSettings(String dropdownOption, String label)
    {
        this.dropdownOption = dropdownOption;
        this.label = label;
    }
}
