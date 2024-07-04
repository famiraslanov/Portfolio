package com.tests.api.fund.closedendfundlifecycle;

import com.library.Verify;
import com.library.annotations.CoverageInfo;
import com.library.enums.MethodType;
import com.library.helpers.ApiHelper;
import com.tests.classes.api.dtos.response.fund.closedendfundlifecycle.FundResponseDTO;
import com.tests.classes.api.validation.ValidateDTOFactory;
import com.tests.enums.api.ApiUrl;
import com.tests.pageobjects.baseobjects.ApiMain;
import org.testng.annotations.Test;

public class FundGetTests extends ApiMain
{
    @CoverageInfo(details = "Get all funds test")
    @Test
    public void getAllFunds()
    {
        ApiUrl apiUrl = ApiUrl.fund;
        String endPoint = "/closed-end-fund-lifecycles";

        FundResponseDTO fundResponseDTO =
                ApiHelper.domApiCall(
                        MethodType.get,
                        apiUrl,
                        endPoint,
                        null,
                        FundResponseDTO.class
                );

        new ValidateDTOFactory()
                .addDTO(fundResponseDTO)
                .process();

        Verify.greaterThan(0, fundResponseDTO.getTotalItems(), "Fund array was empty");
    }
}
