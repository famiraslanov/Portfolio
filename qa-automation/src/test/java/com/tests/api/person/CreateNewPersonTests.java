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
import jakarta.ws.rs.NotFoundException;
import org.testng.annotations.Test;

public class CreateNewPersonTests extends ApiMain
{
    @CoverageInfo(details = "Verify creation new person")
    @Test
    public void createNewPersonTest()
    {
        Log.notRunProd("Not safe to create person entity on Prod");

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

        PersonResponseDTO.Person randomPerson = personResponseDTO.getMembers()[0];

        Log.story("Create new Person");
        PersonRequestDTO requestDTO = PersonRequestDTO.copy(randomPerson);

        PersonResponseDTO.Person newPerson = ApiHelper.domApiCall(
                MethodType.post,
                apiUrl,
                endPoint,
                requestDTO,
                PersonResponseDTO.Person.class
        );

        Verify.isTrue(newPerson.equals(randomPerson), "Data for new person is not equal for expected data");

        Log.story("Remove created Person entity: " + newPerson.getMemberId());
        ApiHelper.domApiCall(MethodType.delete,
                apiUrl,
                endPoint + "/" + newPerson.getMemberId(),
                null,
                PersonResponseDTO.class);

        PersonResponseDTO.Person singlePerson =
                ApiHelper.domApiCall(
                        MethodType.get,
                        apiUrl,
                        endPoint + "/" + newPerson.getMemberId(),
                        null,
                        PersonResponseDTO.Person.class,
                        new NotFoundException());

        Verify.isTrue(singlePerson == null, "New Person is not deleted");
    }
}
