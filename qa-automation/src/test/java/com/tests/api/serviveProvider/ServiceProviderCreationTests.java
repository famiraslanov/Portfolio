package com.tests.api.serviveProvider;

import com.library.Log;
import com.library.Verify;
import com.library.annotations.CoverageInfo;
import com.library.enums.MethodType;
import com.library.helpers.ApiHelper;
import com.tests.helpers.api.LocationApiHelper;
import com.tests.classes.api.dtos.response.organization.location.LocationResponseDTO;
import com.tests.helpers.api.OrganizationApiHelper;
import com.tests.classes.api.dtos.response.organization.organization.OrganizationRequestDTO;
import com.tests.classes.api.dtos.response.organization.organization.OrganizationResponseDTO;
import com.tests.classes.api.dtos.response.serviceProvider.ServiceProviderRequestDTO;
import com.tests.classes.api.dtos.response.serviceProvider.ServiceProviderResponseDTO;
import com.tests.enums.api.ApiUrl;
import com.tests.pageobjects.baseobjects.ApiMain;
import jakarta.ws.rs.NotFoundException;
import org.testng.annotations.*;

import java.util.Map;

public class ServiceProviderCreationTests extends ApiMain
{
    String endPoint = "/service-providers";
    private LocationResponseDTO.Location newLocation;
    private OrganizationResponseDTO.Organization newOrganization;
    private ServiceProviderResponseDTO.ServiceProvider newServiceProvider;

    @BeforeMethod
    public void setUp()
    {
        Log.notRunProd("Not safe to create entity on Prod");

        Log.story("Create location and organization");
        newLocation = LocationApiHelper.createLocation();

        OrganizationResponseDTO organizationResponseDTO = ApiHelper.domApiCall(
                MethodType.get,
                ApiUrl.organisation,
                "/organisations",
                Map.ofEntries(Map.entry("orgType", "SERVICE_PROVIDER")),
                OrganizationResponseDTO.class
        );

        OrganizationResponseDTO.Organization randomOrg = organizationResponseDTO.hydraMember[0];
        OrganizationRequestDTO requestOrgDTO = OrganizationRequestDTO.copy(randomOrg);
        requestOrgDTO.setLocations(new String[]{newLocation.id});

        newOrganization = ApiHelper.domApiCall(
                MethodType.post,
                ApiUrl.organisation,
                "/organisations",
                requestOrgDTO,
                OrganizationResponseDTO.Organization.class
        );
    }

    @CoverageInfo(details = "Verify creation serviceProvider throw api service")
    @Test
    public void serviceProviderCreationTests()
    {
        Log.notRunProd("Not safe to create entity on Prod");

        Log.story("Create new saved provider from copied data");

        ServiceProviderResponseDTO serviceProviderResponseDTO =
                ApiHelper.domApiCall(
                        MethodType.get,
                        ApiUrl.serviceProvider,
                        endPoint,
                        null,
                        ServiceProviderResponseDTO.class
                );

        ServiceProviderResponseDTO.ServiceProvider randomServiceProvider = serviceProviderResponseDTO.hydraMember[0];
        randomServiceProvider.setOrganisation(newOrganization.getIdValue());
        ServiceProviderRequestDTO requestDTO = ServiceProviderRequestDTO.copy(randomServiceProvider);

        newServiceProvider = ApiHelper.domApiCall(
                MethodType.post,
                ApiUrl.serviceProvider,
                endPoint,
                requestDTO,
                ServiceProviderResponseDTO.ServiceProvider.class
        );

        Verify.isTrue(newServiceProvider.equals(randomServiceProvider), "Info for new Service providers is not expected");
    }

    @AfterMethod
    public void tearDown()
    {
        Log.notRunProd("Not safe to create entity on Prod");

        Log.story("Remove all created entities");
        Log.object("newServiceProviderId", newServiceProvider.id);

        if (newServiceProvider != null) {
            ApiHelper.domApiCall(MethodType.delete,
                    ApiUrl.serviceProvider,
                    endPoint + "/" + newServiceProvider.id,
                    null,
                    ServiceProviderResponseDTO.class);

            ServiceProviderResponseDTO.ServiceProvider singleServiceProviderResponseDTO =
                    ApiHelper.domApiCall(
                            MethodType.get,
                            ApiUrl.serviceProvider,
                            endPoint + "/" + newServiceProvider.id,
                            null,
                            ServiceProviderResponseDTO.ServiceProvider.class,
                            new NotFoundException());

            Verify.isTrue(singleServiceProviderResponseDTO == null, "New Service provider is not deleted");
        }

        if (newLocation != null) {
            Log.object("newLocationId", newLocation.idValue);
            LocationApiHelper.remove(newLocation.idValue);
        }

        if (newOrganization != null) {
            Log.object("newOrganizationId", newOrganization.getIdValue());
            OrganizationApiHelper.remove(newOrganization.getIdValue());
        }
    }
}
