package com.tests.enums.platform.api;

public enum SearchApiUrls
{
    dev("https://search-staging-api.search-non-prod.with.digital/api/v1"),
    staging("https://search-staging-api.search-non-prod.with.digital/api/v1"),
    prod("https://search-prod-api.search-prod.with.digital/api/v1");

    public final String url;

    SearchApiUrls(String url)
    {
        this.url = url;
    }
}
