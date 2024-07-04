package com.tests.api.manager;

import com.library.Log;
import com.library.Verify;
import com.library.annotations.CoverageInfo;
import com.library.enums.MethodType;
import com.library.helpers.ApiHelper;
import com.tests.classes.api.dtos.response.manager.ManagementFirmRequestDTO;
import com.tests.classes.api.dtos.response.manager.ManagementFirmResponseDTO;
import com.tests.classes.api.dtos.response.organization.location.LocationResponseDTO;
import com.tests.classes.api.dtos.response.organization.organization.OrganizationResponseDTO;
import com.tests.enums.api.ApiUrl;
import com.tests.helpers.api.LocationApiHelper;
import com.tests.helpers.api.OrganizationApiHelper;
import com.tests.pageobjects.baseobjects.ApiMain;
import org.testng.annotations.Test;

public class UpdateManagementFirmTests extends ApiMain
{
    @CoverageInfo(details = "Update management firm data")
    @Test
    public void updateManagementFirmTest()
    {
        Log.notRunProd("Not safe to create entity on Prod");

        ApiUrl apiUrl = ApiUrl.manager;
        String endPoint = "/management-firms";

        Log.story("Create location and organization");
        LocationResponseDTO.Location newLocation = LocationApiHelper.createLocation();

        Log.story("Create organization");
        OrganizationResponseDTO.Organization newOrg = OrganizationApiHelper.createOrgWithType(newLocation, "MANAGEMENT_FIRM");

        Log.story("Get data for copy");
        ManagementFirmResponseDTO managementFirmResponseDTO =
                ApiHelper.domApiCall(
                        MethodType.get,
                        apiUrl,
                        endPoint,
                        null,
                        ManagementFirmResponseDTO.class
                );

        ManagementFirmResponseDTO.Member randomManagementFirmOne = managementFirmResponseDTO.getMembers()[0];
        ManagementFirmResponseDTO.Member randomManagementFirmTwo = managementFirmResponseDTO.getMembers()[1];

        randomManagementFirmTwo.setOrganisation(newOrg.getIdValue());
        randomManagementFirmTwo.setYearOfIncorporation(1000);

        ManagementFirmRequestDTO requestDTO = ManagementFirmRequestDTO.copy(randomManagementFirmTwo);

        Log.story("Update management firm");
        ManagementFirmResponseDTO.Member updatedManagementFirmResponse =
                ApiHelper.domApiCall(
                        MethodType.patch,
                        apiUrl,
                        endPoint + "/" + randomManagementFirmOne.getMemberId(),
                        requestDTO,
                        ManagementFirmResponseDTO.Member.class
                );
        Verify.isTrue(updatedManagementFirmResponse.equals(randomManagementFirmTwo), "Data is not updated");

        Log.story("Remove location");
        LocationApiHelper.remove(newLocation.idValue);

        Log.story("Remove organization");
        OrganizationApiHelper.remove(newOrg.getIdValue());
    }
}
