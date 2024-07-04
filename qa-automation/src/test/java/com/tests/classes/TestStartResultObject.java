package com.tests.classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.library.Store;
import com.library.helpers.SettingsHelper;
import com.library.listeners.QASuiteListener;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestStartResultObject
{
    @Builder.Default
    private String logType = "StartTest";
    @Builder.Default
    private Integer userId = QASuiteListener.settings.getResultsUserId();
    @Builder.Default
    private String suite = Store.getSuiteName();
    @Builder.Default
    private Integer runId = QASuiteListener.runId;
    @Builder.Default
    private String settings = SettingsHelper.cleanSettings();
    @Builder.Default
    private String environment = QASuiteListener.settings.getEnvironment().toString();

    private String method;
    private String fullClass;
    private String coverageInfo;
    private String sessionId;
}
