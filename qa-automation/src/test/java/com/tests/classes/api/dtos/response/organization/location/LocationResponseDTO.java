package com.tests.classes.api.dtos.response.organization.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tests.classes.api.dtos.response.HydraSearchDTO;
import com.tests.classes.api.validation.Rule;
import com.tests.classes.api.validation.Rules;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationResponseDTO
{
    @JsonProperty("hydra:member")
    @Rules(rule = Rule.isArrayNotEmpty)
    public Location[] hydraMember;

    @JsonProperty("hydra:totalItems")
    @Rules(rule = Rule.greaterThan, value = "0")
    public int hydraTotalItems;

    @Rules(rule = Rule.noValidation)
    @JsonProperty("hydra:view")
    public HydraView view;

    @JsonProperty("hydra:search")
    @Rules(rule = Rule.fieldExists)
    public HydraSearchDTO search;

    @Data
    @EqualsAndHashCode
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Location
    {
        @JsonProperty("@id")
        @EqualsAndHashCode.Exclude
        public String id;

        @JsonProperty("@type")
        public String type;

        @JsonProperty("id")
        @EqualsAndHashCode.Exclude
        public int idValue;

        @JsonProperty("countryUrl")
        public String countryUrl;

        @JsonProperty("subMarketUrl")
        public String subMarketUrl;

        @JsonProperty("cityUrl")
        public String cityUrl;

        @JsonProperty("apiUpdatedAt")
        @EqualsAndHashCode.Exclude
        public String apiUpdatedAt;

        @JsonProperty("country")
        public String country;

        @JsonProperty("subMarket")
        public int subMarket;

        @JsonProperty("city")
        public int city;

        @JsonProperty("address1")
        public String address1;

        @JsonProperty("address2")
        public String address2;

        @JsonProperty("address3")
        public String address3;

        @JsonProperty("email")
        public String email;

        @JsonProperty("phone")
        public String phone;

        @JsonProperty("postcode")
        public String postcode;

        @JsonProperty("main")
        public boolean main;

        @JsonProperty("organisation")
        public String organisation;

        @JsonProperty("deletedAt")
        @EqualsAndHashCode.Exclude
        public String deletedAt;

        @JsonProperty("createdAt")
        @EqualsAndHashCode.Exclude
        public String createdAt;

        @JsonProperty("updatedAt")
        @EqualsAndHashCode.Exclude
        public String updatedAt;
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

        @JsonProperty("hydra:previous")
        public String previous;

        @JsonProperty("hydra:next")
        public String next;
    }
}
