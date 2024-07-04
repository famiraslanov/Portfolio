package com.tests.classes.api.dtos.response.serviceProvider;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceProviderRequestDTO
{
    @JsonProperty("organisation")
    private int organisation;

    @JsonProperty("services")
    private String[] services;

    @JsonProperty("type")
    private String type;

    @JsonProperty("gold")
    private boolean gold;

    @JsonProperty("websites")
    private String[] websites;

    @JsonProperty("deletedAt")
    private String deletedAt;

    @JsonProperty("merged")
    private String merged;

    @JsonProperty("dossiers")
    private String[] dossiers;

    @JsonProperty("summaryText")
    private String summaryText;

    @JsonProperty("externalId")
    private int externalId;

    public static ServiceProviderRequestDTO copy(ServiceProviderResponseDTO.ServiceProvider sp)
    {
        return new ServiceProviderRequestDTO(
                sp.getOrganisation(),
                sp.getServices(),
                sp.getType(),
                sp.isGold(),
                sp.getWebsites(),
                sp.getDeletedAt(),
                sp.getMerged(),
                sp.getDossiers(),
                sp.getSummaryText() != null ? sp.getSummaryText() : "",
                sp.getExternalId() != null ? Integer.parseInt(sp.getExternalId()) : 0);
    }
}
