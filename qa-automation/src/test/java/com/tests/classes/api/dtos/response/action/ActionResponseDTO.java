package com.tests.classes.api.dtos.response.action;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tests.classes.api.dtos.response.HydraSearchDTO;
import com.tests.classes.api.validation.Rule;
import com.tests.classes.api.validation.Rules;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionResponseDTO
{
    // Some properties that match the response... with Rules

    @Rules(rule = Rule.isArrayEmpty)
    @JsonProperty("hydra:member")
    public ActionHydraMemberDTO[] hydraMember;

    @Rules(rule = Rule.equals, value = "0")
    @JsonProperty("hydra:totalItems")
    public Integer hydraTotalItems;

    @Rules(rule = Rule.noValidation)
    @JsonProperty("hydra:view")
    public Object hydraView;

    @Rules(rule = Rule.fieldExists)
    @JsonProperty("hydra:search")
    public HydraSearchDTO hydraSearch;
}
