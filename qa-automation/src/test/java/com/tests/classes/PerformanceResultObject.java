package com.tests.classes;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PerformanceResultObject
{
    private String baseUrl;
    private String endpoint;
    private int httpResponseCode;
    private long durationMs;
    private long sizeBytes;
}
