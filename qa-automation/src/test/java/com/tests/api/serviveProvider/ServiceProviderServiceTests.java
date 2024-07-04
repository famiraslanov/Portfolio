package com.tests.api.serviveProvider;

import com.library.Verify;
import com.library.annotations.CoverageInfo;
import com.library.enums.MethodType;
import com.library.helpers.ApiHelper;
import com.tests.classes.api.dtos.response.serviceProvider.ServiceProviderResponseDTO;
import com.tests.classes.api.validation.ValidateDTOFactory;
import com.tests.enums.api.ApiUrl;
import com.tests.pageobjects.baseobjects.ApiMain;
import org.testng.annotations.Test;

public class ServiceProviderServiceTests extends ApiMain
{
    @CoverageInfo(details = "Verify getting of list of serviceProviders")
    @Test
    public void serviceProviderServiceTest()
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

        new ValidateDTOFactory()
                .addDTO(serviceProviderResponseDTO)
                .addDTO(serviceProviderResponseDTO.hydraSearch.hydraMapping)
                .process();

        Verify.greaterThan(0, serviceProviderResponseDTO.getHydraMember().length, "Service providers array was empty");
    }
}
