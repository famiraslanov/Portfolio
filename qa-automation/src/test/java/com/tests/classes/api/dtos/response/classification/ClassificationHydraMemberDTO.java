package com.tests.classes.api.dtos.response.classification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tests.classes.api.validation.Rule;
import com.tests.classes.api.validation.Rules;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClassificationHydraMemberDTO
{
    @Rules(rule = Rule.greaterThan, value = "0")
    public int id;

    @Rules(rule = Rule.notEmpty)
    public String name;
}
