package com.tests.classes.api.dtos.response.person;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonRoleResponseDTO
{
    @JsonProperty("hydra:member")
    public Member[] members;

    @JsonProperty("hydra:totalItems")
    public int totalItems;

    @JsonProperty("hydra:view")
    public HydraView view;

    @JsonProperty("hydra:search")
    public HydraSearch search;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Member
    {
        @JsonProperty("@context")
        private String context;

        @JsonProperty("@id")
        private String id;

        @JsonProperty("@type")
        private String type;

        @JsonProperty("id")
        private int memberId;

        @JsonProperty("organisationUrl")
        private String organisationUrl;

        @JsonProperty("organisationLocationsUrls")
        private List<String> organisationLocationsUrls;

        @JsonProperty("continentsUrls")
        private List<String> continentsUrls;

        @JsonProperty("countriesUrls")
        private List<String> countriesUrls;

        @JsonProperty("regionsUrls")
        private List<String> regionsUrls;

        @JsonProperty("subRegionsUrls")
        private List<String> subRegionsUrls;

        @JsonProperty("subMarketsUrls")
        private List<String> subMarketsUrls;

        @JsonProperty("apiUpdatedAt")
        private String apiUpdatedAt;

        @JsonProperty("person")
        private String person;

        @JsonProperty("organisation")
        private int organisation;

        @JsonProperty("jobTitle")
        private String jobTitle;

        @JsonProperty("startDate")
        private String startDate;

        @JsonProperty("endDate")
        private String endDate;

        @JsonProperty("seniority")
        private String seniority;

        @JsonProperty("function")
        private String function;

        @JsonProperty("specialisms")
        private List<String> specialisms;

        @JsonProperty("roleName")
        private String roleName;

        @JsonProperty("preferredContactMethod")
        private String preferredContactMethod;

        @JsonProperty("active")
        private boolean active;

        @JsonProperty("mainForOrganisation")
        private boolean mainForOrganisation;

        @JsonProperty("mainForPerson")
        private boolean mainForPerson;

        @JsonProperty("organisationLocations")
        private List<Integer> organisationLocations;

        @JsonProperty("primaryPhone")
        private String primaryPhone;

        @JsonProperty("primaryEmail")
        private String primaryEmail;

        @JsonProperty("continents")
        private List<Integer> continents;

        @JsonProperty("countries")
        private List<String> countries;

        @JsonProperty("regions")
        private List<Integer> regions;

        @JsonProperty("subRegions")
        private List<Integer> subRegions;

        @JsonProperty("subMarkets")
        private List<Integer> subMarkets;

        @JsonProperty("trusteePersonRole")
        private String trusteePersonRole;

        @JsonProperty("deletedAt")
        private String deletedAt;

        @JsonProperty("excludeName")
        private int excludeName;

        @JsonProperty("excludePhone")
        private int excludePhone;

        @JsonProperty("excludeEmail")
        private int excludeEmail;

        @JsonProperty("emailGrade")
        private String emailGrade;

        @JsonProperty("emailGradeDate")
        private String emailGradeDate;

        @JsonProperty("appointed")
        private String appointed;

        @JsonProperty("phoneConfirmed")
        private String phoneConfirmed;

        @JsonProperty("phoneSource")
        private String phoneSource;

        @JsonProperty("emailSource")
        private String emailSource;

        @JsonProperty("contactSource")
        private String contactSource;

        @JsonProperty("gdprNote")
        private String gdprNote;

        @JsonProperty("emailStatus")
        private String emailStatus;

        @JsonProperty("trusteeType")
        private String trusteeType;

        @JsonProperty("createdAt")
        private String createdAt;

        @JsonProperty("updatedAt")
        private String updatedAt;
    }

    @Data
    public static class HydraView
    {
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

    @Data
    public static class HydraSearch
    {
        @JsonProperty("@type")
        private String type;

        @JsonProperty("hydra:template")
        private String template;

        @JsonProperty("hydra:variableRepresentation")
        private String variableRepresentation;

        @JsonProperty("hydra:mapping")
        private List<HydraMapping> mapping;

        @Data
        public static class HydraMapping
        {
            @JsonProperty("@type")
            private String type;

            @JsonProperty("variable")
            private String variable;

            @JsonProperty("property")
            private String property;

            @JsonProperty("required")
            private boolean required;
        }
    }
}
