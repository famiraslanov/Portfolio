package com.tests.helpers.api;

import com.library.Log;
import com.library.Verify;
import com.library.enums.MethodType;
import com.library.helpers.ApiHelper;
import com.tests.classes.api.dtos.response.organization.location.LocationRequestDTO;
import com.tests.classes.api.dtos.response.organization.location.LocationResponseDTO;
import com.tests.enums.api.ApiUrl;

public class LocationApiHelper
{
    static String endPoint = "/locations";
    static ApiUrl apiUrl = ApiUrl.organisation;

    public static LocationResponseDTO.Location createLocation()
    {
        Log.story("Get all ids of location");
        LocationResponseDTO locationResponseDTO =
                ApiHelper.domApiCall(
                        MethodType.get,
                        apiUrl,
                        endPoint,
                        null,
                        LocationResponseDTO.class
                );
        LocationResponseDTO.Location randomLocation = locationResponseDTO.getHydraMember()[0];

        Log.story("Create new location with copied data");
        LocationRequestDTO requestDTO = LocationRequestDTO.copy(randomLocation);
        requestDTO.setOrganisation(null);

        LocationResponseDTO.Location newLocation =
                ApiHelper.domApiCall(
                        MethodType.post,
                        apiUrl,
                        endPoint,
                        requestDTO,
                        LocationResponseDTO.Location.class
                );

        Verify.isTrue(newLocation.id != null, "Info for new Location id is not expected");
        randomLocation.idValue = newLocation.idValue;
        randomLocation.organisation = null;
        Verify.isTrue(newLocation.equals(randomLocation), "Info for new Location data is not expected");

        return newLocation;
    }

    public static void remove(int id)
    {
        ApiHelper.domApiCall(
                MethodType.delete,
                apiUrl,
                endPoint + "/" + id,
                null,
                LocationResponseDTO.class
        );
    }
}
