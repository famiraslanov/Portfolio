package com.library.enums;

import com.library.Store;

public enum Site
{
    platform("https://platform.withintelligence.com/", "https://platform-staging.withintelligence.com/", "https://platform-dev.withintelligence.com/"),
    egr("https://egr.global/", "https://staging.egr.global/", "https://dev.egr.global");

    private String prodUrl;
    private String stagingUrl;
    private String devUrl;

    Site(String prodUrl, String stagingUrl, String devUrl)
    {
        this.prodUrl = prodUrl;
        this.stagingUrl = stagingUrl;
        this.devUrl = devUrl;
    }

    public String url()
    {
        switch (Store.getEnvironment()) {
            case staging:
                return stagingUrl;
            case prod:
                return prodUrl;
            case dev:
                return devUrl;
            case local:
                return "local url not yet implemented";
            default:
                return "UNKNOWN ENVIRONMENT";
        }
    }
}
