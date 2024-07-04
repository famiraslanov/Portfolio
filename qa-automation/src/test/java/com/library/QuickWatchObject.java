package com.library;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.library.enums.QuickWatchAction;
import lombok.Builder;
import lombok.Getter;
import org.openqa.selenium.By;

import java.time.Duration;

@Builder
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuickWatchObject
{
    private String uniqueName;
    @Builder.Default
    private QuickWatchAction action = QuickWatchAction.click;
    private By holderLocator;
    private By actionElementLocator;

    @JsonIgnore // For some reason duration is not serializable
    private Duration smallDelay;
}
