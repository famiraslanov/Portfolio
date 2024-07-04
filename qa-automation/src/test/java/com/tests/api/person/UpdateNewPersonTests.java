package com.tests.api.person;

import com.library.Log;
import com.library.Verify;
import com.library.annotations.CoverageInfo;
import com.library.enums.MethodType;
import com.library.helpers.ApiHelper;
import com.tests.classes.api.dtos.response.person.PersonRequestDTO;
import com.tests.classes.api.dtos.response.person.PersonResponseDTO;
import com.tests.enums.api.ApiUrl;
import com.tests.pageobjects.baseobjects.ApiMain;
import org.testng.annotations.Test;

public class UpdateNewPersonTests extends ApiMain
{
    @CoverageInfo(details = "Verify updating new person")
    @Test
    public void updateNewPersonTest()
    {
        Log.notRunProd("Not safe to updating person entity on Prod");

        ApiUrl apiUrl = ApiUrl.person;
        String endPoint = "/people";

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
        PersonResponseDTO.Person randomPersonTwo = personResponseDTO.getMembers()[1];

        Log.story("Create new Person");
        PersonRequestDTO requestDTO = PersonRequestDTO.copy(randomPersonOne);

        PersonResponseDTO.Person newPerson = ApiHelper.domApiCall(
                MethodType.post,
                apiUrl,
                endPoint,
                requestDTO,
                PersonResponseDTO.Person.class
        );

        Log.story("Update person data");
        PersonRequestDTO updatedDTO = PersonRequestDTO.copy(randomPersonTwo);

        PersonResponseDTO.Person updatedPerson = ApiHelper.domApiCall(
                MethodType.patch,
                apiUrl,
                endPoint + "/" + newPerson.getMemberId(),
                updatedDTO,
                PersonResponseDTO.Person.class
        );

        Verify.isTrue(updatedPerson.equals(randomPersonTwo), "Data for person is not updated");

        Log.story("Remove created Person entity: " + newPerson.getMemberId());
        ApiHelper.domApiCall(MethodType.delete,
                apiUrl,
                endPoint + "/" + newPerson.getMemberId(),
                null,
                PersonResponseDTO.class);

    }
}
