package com.tests.api.person;

import com.library.Verify;
import com.library.annotations.CoverageInfo;
import com.library.enums.MethodType;
import com.library.helpers.ApiHelper;
import com.tests.classes.api.dtos.response.person.PersonResponseDTO;
import com.tests.classes.api.validation.ValidateDTOFactory;
import com.tests.enums.api.ApiUrl;
import com.tests.pageobjects.baseobjects.ApiMain;
import org.testng.annotations.Test;

public class GetAllPersonsTests extends ApiMain
{
    @CoverageInfo(details = "Get all persons list")
    @Test
    public void getAllPersonsTest()
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

        new ValidateDTOFactory()
                .addDTO(personResponseDTO)
                .process();

        Verify.greaterThan(0, personResponseDTO.getTotalItems(), "Person array was empty");
    }
}
