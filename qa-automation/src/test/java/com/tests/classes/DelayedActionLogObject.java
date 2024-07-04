package com.tests.classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.library.Store;
import com.library.helpers.Function;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DelayedActionLogObject extends ResultLogObject
{
    private LocalDateTime runAfter;
    private String runActionMethod;
    private Object runSetupData;

    public DelayedActionLogObject(String runActionMethod, LocalDateTime runAfterDate, Object runSetupData)
    {
        setLogLevel("delayedAction");
        setResultId(Store.getTestId());
        setStack(Function.getStackTrace());
        setRunActionMethod(runActionMethod);
        setRunAfter(runAfterDate);
        setRunSetupData(runSetupData);
    }
}

