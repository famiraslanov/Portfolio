package com.tests.classes;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class PerformanceResponseObject
{
    public LocalDateTime start;
    public LocalDateTime end;
    public long duration;
    public String url;
    public Object response;
    public boolean validJson;
}