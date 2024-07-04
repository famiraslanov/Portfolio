package com.tests.api.organisation;

import com.library.Verify;
import com.library.annotations.CoverageInfo;
import com.library.enums.MethodType;
import com.library.helpers.ApiHelper;
import com.tests.classes.api.dtos.response.organization.location.LocationResponseDTO;
import com.tests.enums.api.ApiUrl;
import com.tests.pageobjects.baseobjects.ApiMain;
import org.testng.annotations.Test;

public class GetSingleLocationTests extends ApiMain
{
    @CoverageInfo(details = "Verify getting one location entity")
    @Test
    public void getSingleLocationTest()
    {
        ApiUrl apiUrl = ApiUrl.organisation;
        String endPoint = "/locations";

        LocationResponseDTO locationResponseDTO =
                ApiHelper.domApiCall(
                        MethodType.get,
                        apiUrl,
                        endPoint,
                        null,
                        LocationResponseDTO.class
                );

        LocationResponseDTO.Location randomLocation = locationResponseDTO.getHydraMember()[0];

        LocationResponseDTO.Location singleLocationResponseDTO =
                ApiHelper.domApiCall(
                        MethodType.get,
                        apiUrl,
                        endPoint + "/" + randomLocation.idValue,
                        null,
                        LocationResponseDTO.Location.class
                );

        Verify.isTrue(singleLocationResponseDTO.equals(randomLocation), "Invalid location is displayed");
    }
}
