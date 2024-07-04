package com.tests.classes;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApiUrlPairObject
{
    private String url;
    private String authToken;
}
