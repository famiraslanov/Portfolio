package com.library.classes;

import com.library.enums.RequestMethod;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.HashMap;

@Builder
@Getter
@Setter
public class PerformanceTestObject
{
    private String baseUrl;
    private String endpoint;
    private String bearer;
    private HashMap<String, String> params;
    private String rawParams;

    @Builder.Default
    private RequestMethod requestMethod = RequestMethod.get;

    @Builder.Default
    private int numberOfThreads = 1;

    @Builder.Default
    private Duration duration = Duration.ofSeconds(1);

    @Builder.Default
    private double tolerancePercentage = 0.1; // within 10%
}
