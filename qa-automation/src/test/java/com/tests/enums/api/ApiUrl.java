package com.tests.enums.api;

import com.tests.classes.ApiUrlObject;
import com.tests.classes.ApiUrlObjects;

public enum ApiUrl
{
    classification(ApiUrlObjects.classification()),
    currency(ApiUrlObjects.currency()),
    fund(ApiUrlObjects.fund()),
    investor(ApiUrlObjects.investor()),
    lpOutreach(ApiUrlObjects.lpOutreach()),
    manager(ApiUrlObjects.manager()),
    organisation(ApiUrlObjects.organisation()),
    person(ApiUrlObjects.person()),
    publishing(ApiUrlObjects.publishing()),
    serviceProvider(ApiUrlObjects.serviceProvider());

    public final String authToken;
    public final String url;
    public final String graphQl;

    ApiUrl(ApiUrlObject urlObject)
    {
        this.url = urlObject.url();
        this.graphQl = urlObject.graphQl();
        this.authToken = urlObject.authToken();
    }
}
