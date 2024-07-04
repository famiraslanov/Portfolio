package com.tests.api.fund.closedendfundlifecycle;

import com.library.Verify;
import com.library.annotations.CoverageInfo;
import com.library.enums.MethodType;
import com.library.helpers.ApiHelper;
import com.tests.classes.api.dtos.response.fund.closedendfundlifecycle.FundResponseDTO;
import com.tests.enums.api.ApiUrl;
import com.tests.pageobjects.baseobjects.ApiMain;
import org.testng.annotations.Test;

public class FundGetIdTests extends ApiMain
{
    @CoverageInfo(details = "Get single fund data")
    @Test
    public void getSingleFundData()
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
        FundResponseDTO.Fund firstFund = fundResponseDTO.getMembers()[0];
        FundResponseDTO.Fund singleFundDTO =
                ApiHelper.domApiCall(
                        MethodType.get,
                        apiUrl,
                        endPoint + "/" + firstFund.getInternalId(),
                        null,
                        FundResponseDTO.Fund.class
                );

        Verify.isTrue(firstFund.equals(singleFundDTO), "Invalid service provider is displayed");
    }
}