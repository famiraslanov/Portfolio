package com.tests.api.manager;

import com.library.Verify;
import com.library.annotations.CoverageInfo;
import com.library.enums.MethodType;
import com.library.helpers.ApiHelper;
import com.tests.classes.api.dtos.response.manager.ManagementFirmResponseDTO;
import com.tests.classes.api.validation.ValidateDTOFactory;
import com.tests.enums.api.ApiUrl;
import com.tests.pageobjects.baseobjects.ApiMain;
import org.testng.annotations.Test;

public class GetListOfManagementFirmTests extends ApiMain
{
    @CoverageInfo(details = "Get all management firms")
    @Test
    public void getListOfManagementFirmTest()
    {
        ApiUrl apiUrl = ApiUrl.manager;
        String endPoint = "/management-firms";

        ManagementFirmResponseDTO managementFirmResponseDTO =
                ApiHelper.domApiCall(
                        MethodType.get,
                        apiUrl,
                        endPoint,
                        null,
                        ManagementFirmResponseDTO.class
                );

        new ValidateDTOFactory()
                .addDTO(managementFirmResponseDTO)
                .process();

        Verify.greaterThan(0, managementFirmResponseDTO.getTotalItems(), "Management Firm array was empty");
    }
}
