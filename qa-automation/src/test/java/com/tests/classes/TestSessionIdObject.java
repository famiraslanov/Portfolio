package com.tests.classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.library.Store;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestSessionIdObject
{
    @Builder.Default
    private String logType = "SaveSessionId";
    @Builder.Default
    private String sessionId = Store.getSessionId();
    @Builder.Default
    private Integer testId = Store.getTestId();
}
