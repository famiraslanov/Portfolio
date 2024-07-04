package com.tests.classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PerformanceAnalysis
{
    public PerformanceAnalysisObject total;
    public PerformanceAnalysisObject withoutMax;
}
