package com.tests.classes.api.dtos.response.person;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tests.classes.api.dtos.response.serviceProvider.ServiceProviderRequestDTO;
import com.tests.classes.api.dtos.response.serviceProvider.ServiceProviderResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonRequestDTO
{
    @JsonProperty("personRoles")
    private List<String> personRoles;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("linkedInUrl")
    private String linkedInUrl;

    @JsonProperty("biography")
    private String biography;

    @JsonProperty("bioAiGenerated")
    private boolean bioAiGenerated;

    @JsonProperty("socialMedia")
    private String socialMedia;

    @JsonProperty("gdprBlocked")
    private boolean gdprBlocked;

    @JsonProperty("deletedAt")
    private String deletedAt;


    public static PersonRequestDTO copy(PersonResponseDTO.Person person)
    {
        return new PersonRequestDTO(
                person.getPersonRoles(),
                person.getFirstName(),
                person.getLastName(),
                person.getFullName(),
                person.getLinkedInUrl(),
                person.getBiography(),
                person.isBioAiGenerated(),
                person.getSocialMedia(),
                person.isGdprBlocked(),
                person.getDeletedAt()
        );
    }
}
