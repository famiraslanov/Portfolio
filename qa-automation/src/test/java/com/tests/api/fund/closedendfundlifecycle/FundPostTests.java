package com.tests.api.fund.closedendfundlifecycle;

import jakarta.ws.rs.NotFoundException;
import com.library.Log;
import com.library.Verify;
import com.library.annotations.CoverageInfo;
import com.library.enums.MethodType;
import com.library.helpers.ApiHelper;
import com.tests.classes.api.dtos.response.fund.closedendfundlifecycle.FundRequestDTO;
import com.tests.classes.api.dtos.response.fund.closedendfundlifecycle.FundResponseDTO;
import com.tests.enums.api.ApiUrl;
import com.tests.pageobjects.baseobjects.ApiMain;
import org.testng.annotations.Test;

public class FundPostTests extends ApiMain
{
    @CoverageInfo(details = "Verify creation new Fund")
    @Test
    public void createNewFundTest()
    {
        Log.notRunProd("Not safe to create Fund entity on Prod");

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
        Log.story("Creating new fund");
        FundRequestDTO requestDTO = FundRequestDTO.copy((firstFund));

        FundResponseDTO.Fund newFund =
                ApiHelper.domApiCall(
                        MethodType.post,
                        apiUrl,
                        endPoint,
                        requestDTO,
                        FundResponseDTO.Fund.class
                );

        FundRequestDTO fundWithRequestBody = FundRequestDTO.copy(newFund);

        Verify.isTrue(requestDTO.equals(fundWithRequestBody), "Data for new fund is not equal for expected data");

        Log.story("Remove created Fund entity: " + newFund.getInternalId());
        ApiHelper.domApiCall(
                MethodType.delete,
                apiUrl,
                endPoint + "/" + newFund.getInternalId(),
                null,
                FundResponseDTO.class
        );
        FundResponseDTO.Fund singleFund =
                ApiHelper.domApiCall(
                        MethodType.get,
                        apiUrl,
                        endPoint + "/" + newFund.getInternalId(),
                        null,
                        FundResponseDTO.Fund.class,
                        new NotFoundException()
                );
        Verify.isTrue(singleFund == null, "New Fund is not deleted");
    }

}
