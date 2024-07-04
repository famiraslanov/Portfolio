package com.tests.classes.api.dtos.response.fund.closedendfundlifecycle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tests.classes.api.validation.Rule;
import com.tests.classes.api.validation.Rules;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FundResponseDTO
{
    @Rules(rule = Rule.isArrayNotEmpty)
    @JsonProperty("hydra:member")
    public FundResponseDTO.Fund[] members;

    @Rules(rule = Rule.greaterThan, value = "0")
    @JsonProperty("hydra:totalItems")
    public int totalItems;

    @Rules(rule = Rule.fieldExists)
    @JsonProperty("hydra:view")
    public FundResponseDTO.HydraView view;

    @Rules(rule = Rule.fieldExists)
    @JsonProperty("hydra:search")
    public FundResponseDTO.HydraSearch search;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Fund
    {
        @JsonProperty("@id")
        private String id;

        @JsonProperty("@type")
        private String type;

        @JsonProperty("id")
        private int internalId;

        @JsonProperty("committedCapitalUsd")
        private double committedCapitalUsd;

        @JsonProperty("committedCapitalEur")
        private double committedCapitalEur;

        @JsonProperty("apiUpdatedAt")
        private String apiUpdatedAt;

        @JsonProperty("closedEndFund")
        private String closedEndFund;

        @JsonProperty("lifecycle")
        private String lifecycle;

        @JsonProperty("currency")
        private String currency;

        @JsonProperty("committedCapital")
        private double committedCapital;

        @JsonProperty("date")
        private String date;

        @JsonProperty("estimate")
        private boolean estimate;

        @JsonProperty("comment")
        private String comment;

        @JsonProperty("deletedAt")
        private String deletedAt;

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
