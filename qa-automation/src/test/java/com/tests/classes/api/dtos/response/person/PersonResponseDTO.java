package com.tests.classes.api.dtos.response.person;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tests.classes.api.validation.Rule;
import com.tests.classes.api.validation.Rules;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonResponseDTO
{
    @Rules(rule = Rule.isArrayNotEmpty)
    @JsonProperty("hydra:member")
    public Person[] members;

    @Rules(rule = Rule.greaterThan, value = "0")
    @JsonProperty("hydra:totalItems")
    public int totalItems;

    @Rules(rule = Rule.fieldExists)
    @JsonProperty("hydra:view")
    public HydraView view;

    @Rules(rule = Rule.fieldExists)
    @JsonProperty("hydra:search")
    public HydraSearch search;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Person
    {
        @JsonProperty("@id")
        private String id;

        @JsonProperty("@type")
        private String type;

        @JsonProperty("id")
        private int memberId;

        @JsonProperty("apiUpdatedAt")
        private String apiUpdatedAt;

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

        @JsonProperty("createdAt")
        private String createdAt;

        @JsonProperty("updatedAt")
        private String updatedAt;

        @Override
        public boolean equals(Object o)
        {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return bioAiGenerated == person.bioAiGenerated &&
                    gdprBlocked == person.gdprBlocked &&
                    Objects.equals(personRoles, person.personRoles) &&
                    Objects.equals(firstName, person.firstName) &&
                    Objects.equals(lastName, person.lastName) &&
                    Objects.equals(fullName, person.fullName) &&
                    Objects.equals(linkedInUrl, person.linkedInUrl) &&
                    Objects.equals(biography, person.biography) &&
                    Objects.equals(socialMedia, person.socialMedia) &&
                    Objects.equals(deletedAt, person.deletedAt);
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(personRoles, firstName, lastName, fullName, linkedInUrl, biography,
                    bioAiGenerated, socialMedia, gdprBlocked, deletedAt);
        }

    }

    @Data
    public static class HydraView
    {
        @JsonProperty("@id")
        private String id;

        @JsonProperty("@type")
        private String type;

        @JsonProperty("hydra:first")
        private String first;

        @JsonProperty("hydra:last")
        private String last;

        @JsonProperty("hydra:previous")
        private String previous;

        @JsonProperty("hydra:next")
        private String next;
    }

    @Data
    public static class HydraSearch
    {
        @JsonProperty("@type")
        private String type;

        @JsonProperty("hydra:template")
        private String template;

        @JsonProperty("hydra:variableRepresentation")
        private String variableRepresentation;

        @JsonProperty("hydra:mapping")
        private List<HydraMapping> mapping;

        @Data
        public static class HydraMapping
        {
            @JsonProperty("@type")
            private String type;

            @JsonProperty("variable")
            private String variable;

            @JsonProperty("property")
            private String property;

            @JsonProperty("required")
            private boolean required;
        }
    }
}
