package com.tests.classes.api.dtos.response.organization.location;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationRequestDTO
{

    @JsonProperty("country")
    private String country;

    @JsonProperty("subMarket")
    private Integer subMarket;

    @JsonProperty("city")
    private int city;

    @JsonProperty("address1")
    private String address1;

    @JsonProperty("address2")
    private String address2;

    @JsonProperty("address3")
    private String address3;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("postcode")
    private String postcode;

    @JsonProperty("main")
    private boolean main;

    @JsonProperty("organisation")
    private String organisation;

    @JsonProperty("deletedAt")
    private String deletedAt;

    public static LocationRequestDTO copy(LocationResponseDTO.Location location) {
        return LocationRequestDTO.builder()
                .country(location.country)
                .subMarket(location.subMarket == 0 ? null : location.subMarket)
                .city(location.city)
                .address1(location.address1)
                .address2(location.address2)
                .address3(location.address3)
                .email(location.email)
                .phone(location.phone)
                .postcode(location.postcode)
                .main(location.main)
                .organisation(location.organisation)
                .deletedAt(location.deletedAt)
                .build();
    }
}
