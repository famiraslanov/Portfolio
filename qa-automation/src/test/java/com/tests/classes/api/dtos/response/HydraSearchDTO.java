package com.tests.classes.api.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tests.classes.api.validation.Rule;
import com.tests.classes.api.validation.Rules;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HydraSearchDTO
{
    @Rules(rule = Rule.notEmpty)
    @JsonProperty("hydra:template")
    public String hydraTemplate;

    @Rules(rule = Rule.notEmpty)
    @JsonProperty("hydra:variableRepresentation")
    public String hydraVariableRepresentation;

    @Rules(rule = Rule.isArrayNotEmpty)
    @JsonProperty("hydra:mapping")
    public HydraMappingDTO[] hydraMapping;
}
