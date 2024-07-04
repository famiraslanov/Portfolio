package com.tests.classes;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FilterObject
{
    private String name;
    private int indexToSelect;
}
