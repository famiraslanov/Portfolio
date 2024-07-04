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

import java.util.Objects;

public class ManagementFirmGetSingleTests extends ApiMain
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

        ManagementFirmResponseDTO.Member randomManagementFirm = managementFirmResponseDTO.getMembers()[0];

        ManagementFirmResponseDTO.Member member =
                ApiHelper.domApiCall(
                        MethodType.get,
                        apiUrl,
                        endPoint + "/" + randomManagementFirm.getMemberId(),
                        null,
                        ManagementFirmResponseDTO.Member.class
                );

        new ValidateDTOFactory()
                .addDTO(managementFirmResponseDTO)
                .process();

        Verify.isTrue(Objects.equals(member, randomManagementFirm), "Invalid management firm is displayed");
    }
}
