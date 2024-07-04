package com.tests.api;

import com.library.Verify;
import com.library.annotations.CoverageInfo;
import com.library.enums.MethodType;
import com.library.helpers.ApiHelper;
import com.tests.classes.api.dtos.response.classification.ClassificationsResponseDTO;
import com.tests.classes.api.validation.ValidateDTOFactory;
import com.tests.enums.api.ApiUrl;
import com.tests.pageobjects.baseobjects.ApiMain;
import org.testng.annotations.Test;

public class ClassificationGetTests extends ApiMain
{
    @CoverageInfo(details = "Verify the classification api service")
    @Test
    public void classificationServiceTest()
    {
        ApiUrl apiUrl = ApiUrl.classification;
        String endPoint = "/approaches";

        ClassificationsResponseDTO classificationsResponseDTO = ApiHelper.domApiCall(
                MethodType.get,
                apiUrl,
                endPoint,
                null,
                ClassificationsResponseDTO.class
        );

        new ValidateDTOFactory()
                .addDTO(classificationsResponseDTO)
                .addDTO(classificationsResponseDTO.hydraSearch)
                .addDTO(classificationsResponseDTO.hydraSearch.hydraMapping)
                .process();

        Verify.greaterThan(0, classificationsResponseDTO.hydraSearch.hydraMapping.length, "Hydra mapping array was empty");
    }
}
