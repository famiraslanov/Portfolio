package com.tests.api.person;

import com.library.Log;
import com.library.Verify;
import com.library.annotations.CoverageInfo;
import com.library.enums.MethodType;
import com.library.helpers.ApiHelper;
import com.tests.classes.api.dtos.response.person.PersonRequestDTO;
import com.tests.classes.api.dtos.response.person.PersonResponseDTO;
import com.tests.classes.api.dtos.response.person.PersonRoleResponseDTO;
import com.tests.enums.api.ApiUrl;
import com.tests.pageobjects.baseobjects.ApiMain;
import org.testng.annotations.Test;

import java.util.List;

public class UpdatePersonRoleTests extends ApiMain
{
    @CoverageInfo(details = "Verify updating person role for selected person")
    @Test
    public void updatePersonRoleTest()
    {
        Log.notRunProd("Not safe to updating person entity on Prod");

        ApiUrl apiUrl = ApiUrl.person;
        String endPoint = "/people";
        String endPointPersonRoles = "/person-roles";

        Log.story("Get random Person Role");
        PersonRoleResponseDTO personRoleResponseDTO =
                ApiHelper.domApiCall(
                        MethodType.get,
                        apiUrl,
                        endPointPersonRoles,
                        null,
                        PersonRoleResponseDTO.class
                );

        PersonRoleResponseDTO.Member randomPersonRole = personRoleResponseDTO.getMembers()[0];

        Log.story("Get random Person");
        PersonResponseDTO personResponseDTO =
                ApiHelper.domApiCall(
                        MethodType.get,
                        apiUrl,
                        endPoint,
                        null,
                        PersonResponseDTO.class
                );

        PersonResponseDTO.Person randomPersonOne = personResponseDTO.getMembers()[0];

        Log.story("Update person data");
        List<String> expectedPersonRole = List.of(randomPersonRole.getId());
        randomPersonOne.setPersonRoles(expectedPersonRole);
        PersonRequestDTO updatedDTO = PersonRequestDTO.copy(randomPersonOne);

        PersonResponseDTO.Person updatedPerson = ApiHelper.domApiCall(
                MethodType.put,
                apiUrl,
                endPoint + "/" + randomPersonOne.getMemberId(),
                updatedDTO,
                PersonResponseDTO.Person.class
        );
        Verify.isTrue(updatedPerson.getPersonRoles().equals(expectedPersonRole), "Person role not updated");
    }
}
