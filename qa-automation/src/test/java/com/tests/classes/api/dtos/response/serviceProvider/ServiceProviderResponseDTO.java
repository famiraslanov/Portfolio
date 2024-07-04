package com.tests.classes.api.dtos.response.serviceProvider;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tests.classes.api.dtos.response.HydraSearchDTO;
import com.tests.classes.api.validation.Rule;
import com.tests.classes.api.validation.Rules;
import lombok.Data;

import java.util.Arrays;
import java.util.Objects;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceProviderResponseDTO
{
    @Rules(rule = Rule.fieldExists)
    @JsonProperty("@context")
    public String context;

    @Rules(rule = Rule.fieldExists)
    @JsonProperty("@id")
    public String id;

    @Rules(rule = Rule.fieldExists)
    @JsonProperty("@type")
    public String type;

    @Rules(rule = Rule.isArrayNotEmpty)
    @JsonProperty("hydra:member")
    public ServiceProvider[] hydraMember;

    @Rules(rule = Rule.greaterThan, value = "0")
    @JsonProperty("hydra:totalItems")
    public int hydraTotalItems;

    @Rules(rule = Rule.fieldExists)
    @JsonProperty("hydra:view")
    public HydraView hydraView;

    @Rules(rule = Rule.fieldExists)
    @JsonProperty("hydra:search")
    public HydraSearchDTO hydraSearch;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ServiceProvider
    {
        @JsonProperty("@type")
        @Rules(rule = Rule.fieldExists)
        public String serviceProviderType;

        @JsonProperty("@id")
        @Rules(rule = Rule.fieldExists)
        public String serviceProviderId;

        @JsonProperty("id")
        @Rules(rule = Rule.fieldExists)
        public int id;

        @JsonProperty("organisationUrl")
        @Rules(rule = Rule.fieldExists)
        public String organisationUrl;

        @JsonProperty("serviceMandates")
        @Rules(rule = Rule.fieldExists)
        public String[] serviceMandates;

        @JsonProperty("mergedAt")
        @Rules(rule = Rule.fieldExists)
        public String mergedAt;

        @JsonProperty("mergedEntities")
        @Rules(rule = Rule.fieldExists)
        public String[] mergedEntities;

        @JsonProperty("apiUpdatedAt")
        @Rules(rule = Rule.fieldExists)
        public String apiUpdatedAt;

        @JsonProperty("organisation")
        @Rules(rule = Rule.fieldExists)
        public int organisation;

        @JsonProperty("services")
        @Rules(rule = Rule.fieldExists)
        public String[] services;

        @JsonProperty("type")
        @Rules(rule = Rule.fieldExists)
        public String type;

        @JsonProperty("gold")
        @Rules(rule = Rule.fieldExists)
        public boolean gold;

        @JsonProperty("websites")
        @Rules(rule = Rule.fieldExists)
        public String[] websites;

        @JsonProperty("deletedAt")
        @Rules(rule = Rule.fieldExists)
        public String deletedAt;

        @JsonProperty("merged")
        @Rules(rule = Rule.fieldExists)
        public String merged;

        @JsonProperty("dossiers")
        @Rules(rule = Rule.fieldExists)
        public String[] dossiers;

        @JsonProperty("summaryText")
        @Rules(rule = Rule.fieldExists)
        public String summaryText;

        @JsonProperty("externalId")
        @Rules(rule = Rule.fieldExists)
        public String externalId;

        @JsonProperty("createdAt")
        @Rules(rule = Rule.fieldExists)
        public String createdAt;

        @JsonProperty("updatedAt")
        @Rules(rule = Rule.fieldExists)
        public String updatedAt;

        @Override
        public boolean equals(Object o)
        {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ServiceProvider that = (ServiceProvider) o;
            return organisation == that.organisation &&
                    gold == that.gold &&
                    (Objects.equals(externalId, that.externalId) ||
                            (externalId != null && externalId.equals("0") && that.externalId == null) ||
                            (externalId == null && Objects.equals(that.externalId, "0"))) &&
                    Arrays.equals(services, that.services) &&
                    Objects.equals(type, that.type) &&
                    Arrays.equals(websites, that.websites) &&
                    Objects.equals(deletedAt, that.deletedAt) &&
                    Objects.equals(merged, that.merged) &&
                    Arrays.equals(dossiers, that.dossiers) &&
                    (Objects.equals(summaryText, that.summaryText) ||
                            (summaryText == null && that.summaryText != null && that.summaryText.isEmpty()) ||
                            (summaryText != null && summaryText.isEmpty() && that.summaryText == null));
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HydraView
    {
        @JsonProperty("@id")
        public String id;

        @JsonProperty("@type")
        public String type;

        @JsonProperty("hydra:first")
        public String first;

        @JsonProperty("hydra:last")
        public String last;

        @JsonProperty("hydra:next")
        public String next;
    }
}
