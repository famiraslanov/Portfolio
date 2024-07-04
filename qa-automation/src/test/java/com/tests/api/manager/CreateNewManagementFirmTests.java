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
import jakarta.ws.rs.NotFoundException;
import org.testng.annotations.Test;

public class CreateNewManagementFirmTests extends ApiMain
{
    @CoverageInfo(details = "Create new management firms")
    @Test
    public void createNewManagementFirmTests()
    {
        Log.notRunProd("Not safe to create entity on Prod");

        ApiUrl apiUrl = ApiUrl.manager;
        String endPoint = "/management-firms";

        Log.story("Create location and organization");
        LocationResponseDTO.Location newLocation = LocationApiHelper.createLocation();

        Log.story("Create organization");
        OrganizationResponseDTO.Organization newOrg = OrganizationApiHelper.createOrgWithType(newLocation, "MANAGEMENT_FIRM");

        Log.story("Get random item");
        ManagementFirmResponseDTO managementFirmResponseDTO =
                ApiHelper.domApiCall(
                        MethodType.get,
                        apiUrl,
                        endPoint,
                        null,
                        ManagementFirmResponseDTO.class
                );

        ManagementFirmResponseDTO.Member randomManagementFirm = managementFirmResponseDTO.getMembers()[0];

        Log.story("Create new management firm");
        randomManagementFirm.setOrganisation(newOrg.getIdValue());
        randomManagementFirm.setYearOfIncorporation(1000);
        ManagementFirmRequestDTO requestDTO = ManagementFirmRequestDTO.copy(randomManagementFirm);

        ManagementFirmResponseDTO.Member newFirm = ApiHelper.domApiCall(
                MethodType.post,
                apiUrl,
                endPoint,
                requestDTO,
                ManagementFirmResponseDTO.Member.class
        );
        Verify.isTrue(newFirm.equals(randomManagementFirm), "Data for new firm is not equal for expected data");

        Log.story("Remove created Management firm entity: " + newFirm.getMemberId());
        ApiHelper.domApiCall(MethodType.delete,
                apiUrl,
                endPoint + "/" + newFirm.getMemberId(),
                null,
                ManagementFirmResponseDTO.class);

        ManagementFirmResponseDTO.Member singleFirm =
                ApiHelper.domApiCall(
                        MethodType.get,
                        apiUrl,
                        endPoint + "/" + newFirm.getMemberId(),
                        null,
                        ManagementFirmResponseDTO.Member.class,
                        new NotFoundException());

        Verify.isTrue(singleFirm == null, "New Management Firm is not deleted");

        Log.story("Remove location");
        LocationApiHelper.remove(newLocation.idValue);

        Log.story("Remove organization");
        OrganizationApiHelper.remove(newOrg.getIdValue());
    }
}
