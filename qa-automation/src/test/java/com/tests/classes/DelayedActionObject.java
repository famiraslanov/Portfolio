package com.tests.classes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.library.helpers.Function;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DelayedActionObject
{
    private int runId;
    private int resultId;
    private String runActionMethod;
    private String runSetupData;
    private int attempts;
    private int actionResultId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completeDt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime runAfter;

    public <T> T getRunSetupData(Class<T> classType)
    {
        return Function.fromJson(runSetupData, classType);
    }
}
