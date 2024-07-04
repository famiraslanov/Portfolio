package com.tests.classes.api.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tests.classes.api.validation.Rule;
import com.tests.classes.api.validation.Rules;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HydraMappingDTO
{
    @Rules(rule = Rule.notEmpty)
    public String variable;

    @Rules(rule = Rule.notEmpty)
    public String property;

    @Rules(rule = Rule.isArrayNotEmpty)
    public boolean required;
}
