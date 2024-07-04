package com.tests.classes;

import com.library.Store;
import com.library.helpers.SettingsHelper;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApiUrlObject
{
    private ApiUrlPairObject prodUrlObject;
    private ApiUrlPairObject stagingObject;
    private ApiUrlPairObject devObject;
    private ApiUrlPairObject localObject;

    private String repo;

    private ApiUrlPairObject pair()
    {
        return switch (Store.getEnvironment()) {
            case prod -> prodUrlObject;
            case staging -> stagingObject;
            case dev -> devObject;
            case local -> local(localObject);
        };
    }

    public String url()
    {
        return pair().getUrl();
    }

    public String graphQl()
    {
        return pair().getUrl() + "/graphql";
    }

    public String authToken()
    {
        return pair().getAuthToken();
    }

    private static ApiUrlPairObject local(ApiUrlPairObject apiUrlPairObject)
    {
        String localApiUrl = SettingsHelper.getInstance().getProperty("localApiUrl");
        String localApiToken = SettingsHelper.getInstance().getProperty("localApiToken");
        if (localApiUrl.isEmpty() || localApiUrl.equals("{SET_IN_settings.local}") || localApiToken.equals("{SET_IN_settings.local}")) {
            return ApiUrlPairObject.builder().url(apiUrlPairObject.getUrl()).authToken(apiUrlPairObject.getAuthToken()).build();
        }
        return ApiUrlPairObject.builder().url(localApiUrl).authToken(localApiToken).build();
    }
}
