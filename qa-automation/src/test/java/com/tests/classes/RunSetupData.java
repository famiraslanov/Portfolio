package com.tests.classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.library.Settings;
import com.library.Store;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RunSetupData
{
    private Settings settings = Store.getSettings();
}
