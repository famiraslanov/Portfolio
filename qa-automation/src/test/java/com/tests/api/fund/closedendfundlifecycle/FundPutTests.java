package com.tests.api.fund.closedendfundlifecycle;

import com.library.Log;
import com.library.Verify;
import com.library.annotations.CoverageInfo;
import com.library.enums.MethodType;
import com.library.helpers.ApiHelper;
import com.tests.classes.api.dtos.response.fund.closedendfundlifecycle.FundRequestDTO;
import com.tests.classes.api.dtos.response.fund.closedendfundlifecycle.FundResponseDTO;
import com.tests.enums.api.ApiUrl;
import com.tests.pageobjects.baseobjects.ApiMain;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

public class FundPutTests extends ApiMain
{
    @CoverageInfo(details = "Verify updating fund name")
    @Test
    public void updateFundTest()
    {
        Log.notRunProd("Not safe to updating Fund entity on Prod");

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

        firstFund.setComment(RandomStringUtils.randomAlphabetic(5));

        FundRequestDTO secondFund = FundRequestDTO.copy(fundResponseDTO.getMembers()[1]);
        FundRequestDTO requestDTO = FundRequestDTO.copy(firstFund);

        FundResponseDTO.Fund updatedFund =
                ApiHelper.domApiCall(
                        MethodType.put,
                        apiUrl,
                        endPoint + "/" + firstFund.getInternalId(),
                        requestDTO,
                        FundResponseDTO.Fund.class
                );

        Verify.isTrue(updatedFund.getComment().equals(firstFund.getComment()), "Fund is not updated");
    }
}
