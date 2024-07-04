package com.tests.api.organisation;

import com.library.Verify;
import com.library.annotations.CoverageInfo;
import com.library.enums.MethodType;
import com.library.helpers.ApiHelper;
import com.tests.classes.api.dtos.response.organization.location.LocationResponseDTO;
import com.tests.classes.api.validation.ValidateDTOFactory;
import com.tests.enums.api.ApiUrl;
import com.tests.pageobjects.baseobjects.ApiMain;
import org.testng.annotations.Test;

public class GetLocationTests extends ApiMain
{
    @CoverageInfo(details = "Verify getting all list of location entities")
    @Test
    public void getLocationTest()
    {
        ApiUrl apiUrl = ApiUrl.organisation;
        String endPoint = "/locations";

        LocationResponseDTO locationResponseDTO =
                ApiHelper.domApiCall(
                        MethodType.get,
                        apiUrl,
                        endPoint,
                        null,
                        LocationResponseDTO.class,
                        null
                );

        new ValidateDTOFactory()
                .addDTO(locationResponseDTO)
                .addDTO(locationResponseDTO.getSearch().hydraMapping)
                .process();

        Verify.greaterThan(0, locationResponseDTO.getHydraMember().length, "Location array was empty");
    }
}
