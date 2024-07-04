package com.tests.helpers.api;

import com.library.Log;
import com.library.enums.MethodType;
import com.library.helpers.ApiHelper;
import com.tests.classes.api.dtos.response.organization.location.LocationResponseDTO;
import com.tests.classes.api.dtos.response.organization.organization.OrganizationRequestDTO;
import com.tests.classes.api.dtos.response.organization.organization.OrganizationResponseDTO;
import com.tests.enums.api.ApiUrl;

import java.util.Map;

public class OrganizationApiHelper
{
    static String endPoint = "/organisations";

    public static void remove(int id)
    {
        ApiHelper.domApiCall(
                MethodType.delete,
                ApiUrl.organisation,
                endPoint + "/" + id,
                null,
                LocationResponseDTO.class
        );
    }

    public static OrganizationResponseDTO.Organization createOrgWithType(LocationResponseDTO.Location location, String type)
    {
        Log.notRunProd("Not safe to create entity on Prod");

        OrganizationResponseDTO organizationResponseDTO = ApiHelper.domApiCall(
                MethodType.get,
                ApiUrl.organisation,
                endPoint,
                Map.ofEntries(Map.entry("orgType", type)),
                OrganizationResponseDTO.class
        );

        OrganizationResponseDTO.Organization randomOrg = organizationResponseDTO.hydraMember[0];
        OrganizationRequestDTO requestOrgDTO = OrganizationRequestDTO.copy(randomOrg);
        requestOrgDTO.setLocations(new String[]{location.id});

        return ApiHelper.domApiCall(
                MethodType.post,
                ApiUrl.organisation,
                "/organisations",
                requestOrgDTO,
                OrganizationResponseDTO.Organization.class
        );
    }
}
