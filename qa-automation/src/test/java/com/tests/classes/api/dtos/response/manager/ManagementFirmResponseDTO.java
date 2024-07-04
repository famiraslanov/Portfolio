package com.tests.classes.api.dtos.response.manager;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tests.classes.api.dtos.response.HydraSearchDTO;
import com.tests.classes.api.validation.Rule;
import com.tests.classes.api.validation.Rules;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ManagementFirmResponseDTO
{
    @Rules(rule = Rule.isArrayNotEmpty)
    @JsonProperty("hydra:member")
    public Member[] members;

    @Rules(rule = Rule.greaterThan, value = "0")
    @JsonProperty("hydra:totalItems")
    public int totalItems;

    @Rules(rule = Rule.fieldExists)
    @JsonProperty("hydra:view")
    public HydraView view;

    @Rules(rule = Rule.fieldExists)
    @JsonProperty("hydra:search")
    public HydraSearchDTO search;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Member {

        @JsonProperty("@id")
        private String id;

        @JsonProperty("@type")
        private String type;

        @JsonProperty("id")
        private int memberId;

        @JsonProperty("organisationUrl")
        private String organisationUrl;

        @JsonProperty("aums")
        private List<String> aums;

        @JsonProperty("billionDollarClubRankings")
        private List<String> billionDollarClubRankings;

        @JsonProperty("headCounts")
        private List<String> headCounts;

        @JsonProperty("mergedAt")
        private String mergedAt;

        @JsonProperty("mergedEntities")
        private List<String> mergedEntities;

        @JsonProperty("apiUpdatedAt")
        private String apiUpdatedAt;

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

        @JsonProperty("createdAt")
        private String createdAt;

        @JsonProperty("updatedAt")
        private String updatedAt;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Member member = (Member) o;
            return isPrivate == member.isPrivate &&
                    yearOfIncorporation == member.yearOfIncorporation &&
                    secRegisteredFirm == member.secRegisteredFirm &&
                    organisation == member.organisation &&
                    gold == member.gold &&
                    externalId == member.externalId &&
                    Objects.equals(aums, member.aums) &&
                    Objects.equals(billionDollarClubRankings, member.billionDollarClubRankings) &&
                    Objects.equals(headCounts, member.headCounts) &&
                    Objects.equals(mergedAt, member.mergedAt) &&
                    Objects.equals(mergedEntities, member.mergedEntities) &&
                    Objects.equals(serviceProviders, member.serviceProviders) &&
                    Objects.equals(websites, member.websites) &&
                    Objects.equals(types, member.types) &&
                    Objects.equals(secExemption, member.secExemption) &&
                    Objects.equals(secNumber, member.secNumber) &&
                    Objects.equals(centralIndexKey, member.centralIndexKey) &&
                    Objects.equals(salesforceId, member.salesforceId) &&
                    Objects.equals(leiCode, member.leiCode) &&
                    Objects.equals(leiLegalName, member.leiLegalName) &&
                    Objects.equals(countrySpecificIdentifiers, member.countrySpecificIdentifiers) &&
                    Objects.equals(centralRegistrationDepositoryNumber, member.centralRegistrationDepositoryNumber) &&
                    Objects.equals(founded, member.founded) &&
                    Objects.equals(merged, member.merged);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, organisationUrl, aums, billionDollarClubRankings, headCounts, mergedAt, mergedEntities, serviceProviders, websites, types, isPrivate, yearOfIncorporation, secRegisteredFirm, secExemption, secNumber, centralIndexKey, salesforceId, leiCode, leiLegalName, countrySpecificIdentifiers, centralRegistrationDepositoryNumber, organisation, founded, deletedAt, gold, merged, externalId);
        }
    }

    @Data
    public static class HydraView {
        @JsonProperty("@id")
        private String id;

        @JsonProperty("@type")
        private String type;

        @JsonProperty("hydra:first")
        private String first;

        @JsonProperty("hydra:last")
        private String last;

        @JsonProperty("hydra:previous")
        private String previous;

        @JsonProperty("hydra:next")
        private String next;
    }
}
