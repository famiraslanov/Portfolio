package com.tests.classes.api.dtos.response.organization.organization;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Arrays;

@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationRequestDTO
{
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
    private String deletedAt;

    public static OrganizationRequestDTO copy(OrganizationResponseDTO.Organization organization)
    {
        return OrganizationRequestDTO.builder()
                .children(organization.getChildren())
                .locations(organization.getLocations())
                .alternativeNames(organization.getAlternativeNames())
                .name(organization.getName())
                .website(organization.getWebsite())
                .orgType(organization.getOrgType())
                .orgEntityId(organization.getOrgEntityId())
                .parent(organization.getParent())
                .summary(organization.getSummary())
                .acronym(organization.getAcronym())
                .ein(organization.getEin())
                .active(organization.isActive())
                .deletedAt(organization.getDeletedAt())
                .build();
    }
}
