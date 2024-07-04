package com.tests.classes.api.validation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class ValidateFieldProcessObject
{
    private Object object;
    private String fieldName;
    private Rule fieldRule;
    private String expectedValue;
    private Boolean fatal;
}
