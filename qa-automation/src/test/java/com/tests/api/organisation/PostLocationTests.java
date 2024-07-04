package com.tests.api.organisation;

import com.library.Log;
import com.library.Verify;
import com.library.annotations.CoverageInfo;
import com.library.enums.MethodType;
import com.library.helpers.ApiHelper;
import com.tests.classes.api.dtos.response.organization.location.LocationResponseDTO;
import com.tests.enums.api.ApiUrl;
import com.tests.helpers.api.LocationApiHelper;
import com.tests.pageobjects.baseobjects.ApiMain;
import jakarta.ws.rs.NotFoundException;
import org.testng.annotations.Test;

public class PostLocationTests extends ApiMain
{
    @CoverageInfo(details = "Verify creation new location")
    @Test
    public void postLocationTest()
    {
        Log.notRunProd("Not safe to create duplicate locations on Prod");

        ApiUrl apiUrl = ApiUrl.organisation;
        String endPoint = "/locations";

        LocationResponseDTO.Location newLocation = LocationApiHelper.createLocation();

        ApiHelper.domApiCall(
                MethodType.delete,
                apiUrl,
                endPoint + "/" + newLocation.idValue,
                null,
                LocationResponseDTO.class
        );

        LocationResponseDTO.Location singleLocationResponseDTO =
                ApiHelper.domApiCall(
                        MethodType.get,
                        apiUrl,
                        endPoint + "/" + newLocation.idValue,
                        null,
                        LocationResponseDTO.Location.class,
                        new NotFoundException()
                );
        Verify.isTrue(singleLocationResponseDTO == null, "New Location is not deleted");
    }
}
