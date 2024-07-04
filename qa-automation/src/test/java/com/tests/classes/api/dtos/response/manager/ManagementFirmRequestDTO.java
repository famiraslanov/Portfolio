package com.tests.classes.api.dtos.response.manager;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class ManagementFirmRequestDTO
{
    @JsonProperty("serviceProviders")
    private List<String> serviceProviders;

    @JsonProperty("websites")
    private List<String> websites;

    @JsonProperty("types")
    private List<String> types;

    @JsonProperty("private")
    private boolean isPrivate;

    @JsonProperty("yearOfIncorporation")
    private int yearOfIncorporation;

    @JsonProperty("secRegisteredFirm")
    private boolean secRegisteredFirm;

    @JsonProperty("secExemption")
    private String secExemption;

    @JsonProperty("secNumber")
    private String secNumber;

    @JsonProperty("centralIndexKey")
    private String centralIndexKey;

    @JsonProperty("salesforceId")
    private String salesforceId;

    @JsonProperty("leiCode")
    private String leiCode;

    @JsonProperty("leiLegalName")
    private String leiLegalName;

    @JsonProperty("countrySpecificIdentifiers")
    private String countrySpecificIdentifiers;

    @JsonProperty("centralRegistrationDepositoryNumber")
    private String centralRegistrationDepositoryNumber;

    @JsonProperty("organisation")
    private int organisation;

    @JsonProperty("founded")
    private String founded;

    @JsonProperty("deletedAt")
    private String deletedAt;

    @JsonProperty("gold")
    private boolean gold;

    @JsonProperty("merged")
    private String merged;

    @JsonProperty("externalId")
    private int externalId;

    public static ManagementFirmRequestDTO copy(ManagementFirmResponseDTO.Member original) {
        ManagementFirmRequestDTO copy = new ManagementFirmRequestDTO();
        copy.setServiceProviders(original.getServiceProviders());
        copy.setWebsites(original.getWebsites());
        copy.setTypes(original.getTypes());
        copy.setPrivate(original.isPrivate());
        copy.setYearOfIncorporation(original.getYearOfIncorporation());
        copy.setSecRegisteredFirm(original.isSecRegisteredFirm());
        copy.setSecExemption(original.getSecExemption());
        copy.setSecNumber(original.getSecNumber());
        copy.setCentralIndexKey(original.getCentralIndexKey());
        copy.setSalesforceId(original.getSalesforceId());
        copy.setLeiCode(original.getLeiCode());
        copy.setLeiLegalName(original.getLeiLegalName());
        copy.setCountrySpecificIdentifiers(original.getCountrySpecificIdentifiers());
        copy.setCentralRegistrationDepositoryNumber(original.getCentralRegistrationDepositoryNumber());
        copy.setOrganisation(original.getOrganisation());
        copy.setFounded(original.getFounded());
        copy.setDeletedAt(original.getDeletedAt());
        copy.setGold(original.isGold());
        copy.setMerged(original.getMerged());
        copy.setExternalId(original.getExternalId());
        return copy;
    }
}
