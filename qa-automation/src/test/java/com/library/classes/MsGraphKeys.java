package com.library.classes;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MsGraphKeys
{
    private String tenantId;
    private String appId;
    private String secret;
}
