package com.tests.classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.library.listeners.QASuiteListener;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultLogObject
{
    private String token = QASuiteListener.settings.getApiToken();
    private Integer userId = QASuiteListener.settings.getResultsUserId();
    private Integer runId = QASuiteListener.runId;
    private Integer resultId;
    private String logType = "ResultLog";
    private Integer displayIndex;
    private String logLevel;
    private String context;
    private List<StackTraceElement> stack;
    private Integer found;
    private String label;
}
