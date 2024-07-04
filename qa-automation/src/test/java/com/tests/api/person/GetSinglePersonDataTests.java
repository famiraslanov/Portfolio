package com.tests.api.person;

import com.library.Verify;
import com.library.annotations.CoverageInfo;
import com.library.enums.MethodType;
import com.library.helpers.ApiHelper;
import com.tests.classes.api.dtos.response.person.PersonResponseDTO;
import com.tests.enums.api.ApiUrl;
import com.tests.pageobjects.baseobjects.ApiMain;
import org.testng.annotations.Test;

import java.util.Objects;

public class GetSinglePersonDataTests extends ApiMain
{
    @CoverageInfo(details = "Get single person data")
    @Test
    public void getSinglePersonDataTest()
    {
        ApiUrl apiUrl = ApiUrl.person;
        String endPoint = "/people";

        PersonResponseDTO personResponseDTO =
                ApiHelper.domApiCall(
                        MethodType.get,
                        apiUrl,
                        endPoint,
                        null,
                        PersonResponseDTO.class
                );

        PersonResponseDTO.Person randomPerson = personResponseDTO.getMembers()[0];

        PersonResponseDTO.Person singlePersonResponseDTO =
                ApiHelper.domApiCall(
                        MethodType.get,
                        apiUrl,
                        endPoint + "/" + randomPerson.getMemberId(),
                        null,
                        PersonResponseDTO.Person.class
                );

        Verify.isTrue(Objects.equals(singlePersonResponseDTO.getId(), randomPerson.getId()), "Invalid service provider is displayed");
    }
}
