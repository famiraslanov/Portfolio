package com.tests.classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PendingEmail
{
    private int id;
    private int resultId;
    private String toEmail;
    private String uniqueContent;
}
