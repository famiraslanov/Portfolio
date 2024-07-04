package com.tests.classes.api.dtos.response.organization.organization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tests.classes.api.dtos.response.HydraSearchDTO;
import com.tests.classes.api.validation.Rule;
import com.tests.classes.api.validation.Rules;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrganizationResponseDTO
{
    @JsonProperty("hydra:member")
    @Rules(rule = Rule.isArrayNotEmpty)
    public Organization[] hydraMember;

    @JsonProperty("hydra:totalItems")
    @Rules(rule = Rule.greaterThan, value = "0")
    public int hydraTotalItems;

    @Rules(rule = Rule.fieldExists)
    @JsonProperty("hydra:view")
    public HydraView view;

    @JsonProperty("hydra:search")
    @Rules(rule = Rule.fieldExists)
    public HydraSearchDTO search;


    @Data
    @EqualsAndHashCode
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Organization
    {
        @JsonProperty("@id")
        @EqualsAndHashCode.Exclude
        private String id;

        @JsonProperty("@type")
        private String type;

        @JsonProperty("id")
        @EqualsAndHashCode.Exclude
        private int idValue;

        @JsonProperty("apiUpdatedAt")
        @EqualsAndHashCode.Exclude
        private String apiUpdatedAt;

        @JsonProperty("children")
        private String[] children;

        @JsonProperty("locations")
        private String[] locations;

        @JsonProperty("alternativeNames")
        private String[] alternativeNames;

        @JsonProperty("name")
        private String name;

        @JsonProperty("website")
        private String website;

        @JsonProperty("orgType")
        private String orgType;

        @JsonProperty("orgEntityId")
        private int orgEntityId;

        @JsonProperty("parent")
        private String parent;

        @JsonProperty("summary")
        private String summary;

        @JsonProperty("acronym")
        private String acronym;

        @JsonProperty("ein")
        private String[] ein;

        @JsonProperty("active")
        private boolean active;

        @JsonProperty("deletedAt")
        @EqualsAndHashCode.Exclude
        private String deletedAt;

        @JsonProperty("createdAt")
        @EqualsAndHashCode.Exclude
        private String createdAt;

        @JsonProperty("updatedAt")
        @EqualsAndHashCode.Exclude
        private String updatedAt;

        @JsonProperty("lft")
        private int lft;

        @JsonProperty("rgt")
        private int rgt;

        @JsonProperty("root")
        private int root;

        @JsonProperty("lvl")
        private int lvl;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HydraView
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
