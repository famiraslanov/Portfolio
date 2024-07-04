package com.tests.classes.api.dtos.response.classification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tests.classes.api.dtos.response.HydraSearchDTO;
import com.tests.classes.api.validation.Rule;
import com.tests.classes.api.validation.Rules;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClassificationsResponseDTO
{
    // Some properties that match the response... with Rules

    @Rules(rule = Rule.isArrayNotEmpty)
    @JsonProperty("hydra:member")
    public ClassificationHydraMemberDTO[] hydraMember;

    @Rules(rule = Rule.greaterThan, value = "0")
    @JsonProperty("hydra:totalItems")
    public Integer hydraTotalItems;

    @Rules(rule = Rule.fieldExists)
    @JsonProperty("hydra:search")
    public HydraSearchDTO hydraSearch;
}
