package com.tests.api.serviveProvider;

import com.library.Verify;
import com.library.annotations.CoverageInfo;
import com.library.enums.MethodType;
import com.library.helpers.ApiHelper;
import com.tests.classes.api.dtos.response.serviceProvider.ServiceProviderResponseDTO;
import com.tests.enums.api.ApiUrl;
import com.tests.pageobjects.baseobjects.ApiMain;
import org.testng.annotations.Test;

public class GetSingleServiceProviderServiceTests extends ApiMain
{
    @CoverageInfo(details = "Verify getting of single serviceProvider")
    @Test
    public void getSingleServiceProviderServiceTest()
    {
        ApiUrl apiUrl = ApiUrl.serviceProvider;
        String endPoint = "/service-providers";

        ServiceProviderResponseDTO serviceProviderResponseDTO =
                ApiHelper.domApiCall(
                        MethodType.get,
                        apiUrl,
                        endPoint,
                        null,
                        ServiceProviderResponseDTO.class
                );

        ServiceProviderResponseDTO.ServiceProvider randomServiceProvider = serviceProviderResponseDTO.hydraMember[0];

        ServiceProviderResponseDTO.ServiceProvider signalServiceProviderResponseDTO =
                ApiHelper.domApiCall(
                        MethodType.get,
                        apiUrl,
                        endPoint + "/" + randomServiceProvider.id,
                        null,
                        ServiceProviderResponseDTO.ServiceProvider.class
                );

        Verify.isTrue(signalServiceProviderResponseDTO.id == randomServiceProvider.id, "Invalid service provider is displayed");
    }
}
