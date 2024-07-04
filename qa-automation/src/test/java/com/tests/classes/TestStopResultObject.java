package com.tests.classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.library.Store;
import com.library.helpers.Function;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestStopResultObject
{
    @Builder.Default
    private String logType = "StopTest";
    @Builder.Default
    private Integer testId = Store.getTestId();
    @Builder.Default
    private List<StackTraceElement> stack = Function.getStackTrace();

    private String result;
    private String reason;
    private String url;
}
