package com.tests.classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class QAToolsResponseObject
{
    private boolean ok;
    private Integer runId;
    private Integer testId;
    private String response;
    private String error;
}
