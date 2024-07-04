package com.tests.classes.api.validation;

public enum Rule
{
    isNull,
    notIsNull,

    isTrue,
    isFalse,

    isEmpty,
    notEmpty,

    isListEmpty,
    isListNotEmpty,
    isArrayEmpty,
    isArrayNotEmpty,
    isMapNotEmpty,

    equals,
    notEquals,

    greaterThan,

    fieldContains,

    inTestValidation,
    noValidation,
    fieldExists,
    noRule
}
