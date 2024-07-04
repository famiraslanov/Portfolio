package com.tests.classes.api.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.Log;
import com.library.Verify;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ValidateDTOFactory
{
    private List<Object> dtoObjects = new ArrayList<>();
    private List<ValidateFieldProcessObject> fields = new ArrayList<>();
    private List<String> validateInTestSafety = new ArrayList<>();

    public ValidateDTOFactory addDTO(Object object)
    {
        dtoObjects.add(object);
        return this;
    }

    public ValidateDTOFactory addField(Object object, String fieldName, Rule fieldRule, Date expectedValue)
    {
        addField(object, fieldName, fieldRule, expectedValue.toString());
        return this;
    }

    public ValidateDTOFactory addField(Object object, String fieldName, Rule fieldRule, String expectedValue)
    {
        fields.add(new ValidateFieldProcessObject(object, fieldName, fieldRule, expectedValue, false));
        return this;
    }

    public void process()
    {
        // Validate all standard DTO checks
        for (Object object : dtoObjects) {
            validateDTO(object);
        }

        // Validate all fields passed in from tests (expected value checks)
        for (ValidateFieldProcessObject validateFieldProcessObject : fields) {
            validateField(validateFieldProcessObject);
            validateInTestSafety.remove(validateFieldProcessObject.getFieldName());
        }

        safetyCheck();
    }

    private void safetyCheck()
    {
        // Ensure all inTestValidation fields are tested
        if (validateInTestSafety.size() > 0) {
            Log.debug("inTestValidation fields missing:-");
            validateInTestSafety.forEach(Log::debug);
        }

        if (!validateInTestSafety.isEmpty()) {
            for (String fieldName : validateInTestSafety) {
                System.out.println("inTestValidation needed for: " + fieldName);
            }
        }

        Verify.isTrue(validateInTestSafety.isEmpty(), "Not all fields with 'inTestValidation' have been checked");
    }

    private void validateDTO(Object object)
    {
        try {
            Class cls = object.getClass();
            for (Field field : cls.getDeclaredFields()) {
                field.setAccessible(true);
                Rules annotation = field.getAnnotation(Rules.class);
                Rule fieldRule = Rule.noRule;
                String ruleValue = "";
                boolean fatal = false;

                if (annotation != null) {
                    fieldRule = annotation.rule();
                    ruleValue = annotation.value();
                    fatal = annotation.fatal();
                }

                validateField(new ValidateFieldProcessObject(object, field.getName(), fieldRule, ruleValue, fatal));
            }
        } catch (Exception e) {
            Log.error(e.getMessage());
            Verify.fail("Unable to process the DTO");
        }

    }

    private void validateField(ValidateFieldProcessObject validateFieldProcessObject)
    {
        Object object = validateFieldProcessObject.getObject();
        String fieldName = validateFieldProcessObject.getFieldName();
        Rule fieldRule = validateFieldProcessObject.getFieldRule();
        String expectedValue = validateFieldProcessObject.getExpectedValue();
        boolean fatal = validateFieldProcessObject.getFatal();

        ValidationObject validationObject = new ValidationObject();

        try {
            Class cls = object.getClass();
            Field field = cls.getDeclaredField(fieldName);

            String logStory = "Validate field '" + fieldName + "' " + fieldRule;
            if (fieldRule != Rule.noValidation) {
                logStory += (!expectedValue.isEmpty() ? " '" + expectedValue + "'" : "")
                    + " --- ReturnedValue = '" + new ObjectMapper().writeValueAsString(field.get(object)) + "'";
            }
            Log.info(logStory);

            switch (fieldRule) {
                case isNull:
                    checkNull(object, fieldName, validationObject, field);
                    break;
                case notIsNull:
                    checkNotNull(object, fieldName, validationObject, field);
                    break;
                case isTrue:
                    checkTrue(object, fieldName, validationObject, field);
                    break;
                case isFalse:
                    checkFalse(object, fieldName, validationObject, field);
                    break;
                case equals:
                    checkEquals(object, fieldName, expectedValue, validationObject, field);
                    break;
                case notEquals:
                    checkNotEquals(object, fieldName, expectedValue, validationObject, field);
                    break;
                case isEmpty:
                    checkEmpty(object, fieldName, validationObject, field);
                    break;
                case notEmpty:
                    checkNotEmpty(object, fieldName, expectedValue, validationObject, field);
                    break;
                case isListEmpty:
                    checkListEmpty(object, fieldName, validationObject, field);
                    break;
                case isListNotEmpty:
                    checkListNotEmpty(object, fieldName, validationObject, field);
                    break;
                case isArrayEmpty:
                    checkArrayEmpty(object, fieldName, validationObject, field);
                    break;
                case isArrayNotEmpty:
                    checkArrayNotEmpty(object, fieldName, validationObject, field);
                    break;
                case isMapNotEmpty:
                    checkMapNotEmpty(object, fieldName, validationObject, field);
                    break;
                case greaterThan:
                    checkGreaterThan(object, fieldName, expectedValue, validationObject, field);
                    break;
                case noValidation:
                    validationObject.condition = true;
                    validationObject.message = "No validation";
                    break;
                case noRule:
                    validationObject.message = "No rule annotation for field: " + fieldName;
                    break;
                case fieldExists:
                    checkFieldExists(fieldName, validationObject, field);
                    break;
                case fieldContains:
                    checkFieldContains(object, fieldName, expectedValue, validationObject, field);
                    break;
                case inTestValidation:
                    // Add the field to the safety check to ensure all fields that state they are tested in the test are actually tested
                    validateInTestSafety.add(validateFieldProcessObject.getFieldName());
                    break;
                default:
                    // Ok to do nothing
            }

        } catch (Exception e) {
            validationObject.condition = false;
            validationObject.message = "Error parsing DTO field: " + fieldName + " (" + e.getMessage() + ") Class: " + object.getClass().getName();
            validationObject.allowSoftFail = false;
            Log.error(e.getMessage());
        }

        if (fatal) {
            validationObject.allowSoftFail = false;
            if (!validationObject.condition) {
                Log.info("This was a check with a fatal flag so no further checks carried out");
            }
        }

        if (fieldRule != Rule.inTestValidation) {
            reportValidation(validationObject, fieldName);
        }
    }

    private void reportValidation(ValidationObject validationObject, String fieldName)
    {
        // Pass and log the results
        if (!validationObject.condition && validationObject.allowSoftFail) {
            System.out.println("SOFTFAIL: " + validationObject.message);
        }

        if (!validationObject.message.contains(fieldName)) {
            validationObject.message = fieldName + ": " + validationObject.message;
        }

        Verify.isTrue(validationObject.condition, validationObject.message, validationObject.allowSoftFail);
    }

    /*[*************]
     * The checks. *
     **************/

    private void checkGreaterThan(Object object, String fieldName, String expectedValue, ValidationObject validationObject, Field field) throws IllegalAccessException
    {
        validationObject.condition = Double.parseDouble(String.valueOf(field.get(object))) > Double.parseDouble(expectedValue);
        validationObject.message = fieldName + " value was not greater. Expected: " + expectedValue + " Found: " + field.get(object);
        validationObject.allowSoftFail = true;
    }

    private void checkNotEmpty(Object object, String fieldName, String expectedValue, ValidationObject validationObject, Field field) throws IllegalAccessException
    {
        validationObject.condition = field.get(object) != null && !field.get(object).equals("");
        validationObject.message = fieldName + " value was empty. Expected: " + expectedValue;
        validationObject.allowSoftFail = true;
    }

    private void checkEmpty(Object object, String fieldName, ValidationObject validationObject, Field field) throws IllegalAccessException
    {
        validationObject.condition = field.get(object) == null || field.get(object).equals("");
        validationObject.message = fieldName + " value not empty. Found: " + field.get(object);
        validationObject.allowSoftFail = true;
    }

    private void checkListEmpty(Object object, String fieldName, ValidationObject validationObject, Field field) throws IllegalAccessException
    {
        if (!(field.get(object) instanceof List)) {
            validationObject.condition = false;
            validationObject.message = fieldName + " is not a list";
            return;
        }
        List list = (List) (field.get(object));
        validationObject.condition = list.isEmpty();

        validationObject.message = fieldName + " is not an empty list. Found: " + list.size() + " elements";
        validationObject.allowSoftFail = true;
    }

    private void checkListNotEmpty(Object object, String fieldName, ValidationObject validationObject, Field field) throws IllegalAccessException
    {
        if (!(field.get(object) instanceof List)) {
            validationObject.condition = false;
            validationObject.message = fieldName + " is not a list";
            return;
        }
        List list = (List) (field.get(object));
        validationObject.condition = list.size() > 0;

        validationObject.message = fieldName + " is an empty list. Found: " + list.size() + " elements";
        validationObject.allowSoftFail = true;
    }

    private void checkArrayEmpty(Object object, String fieldName, ValidationObject validationObject, Field field) throws IllegalAccessException
    {
        if (!(field.get(object) instanceof Object[])) {
            validationObject.condition = false;
            validationObject.message = fieldName + " is not an array";
            return;
        }
        Object[] list = (Object[])field.get(object);
        validationObject.condition = list.length == 0;

        validationObject.message = fieldName + " is not an empty array. Found: " + list.length + " elements";
        validationObject.allowSoftFail = true;
    }

    private void checkArrayNotEmpty(Object object, String fieldName, ValidationObject validationObject, Field field) throws IllegalAccessException
    {
        if (!(field.get(object) instanceof Object[])) {
            validationObject.condition = false;
            validationObject.message = fieldName + " is not an Array";
            return;
        }
        Object[] list = (Object[]) (field.get(object));
        validationObject.condition = list.length > 0;

        validationObject.message = fieldName + " is an empty array. Found: " + list.length + " elements";
        validationObject.allowSoftFail = true;
    }

    private void checkMapNotEmpty(Object object, String fieldName, ValidationObject validationObject, Field field) throws IllegalAccessException
    {
        if (!(field.get(object) instanceof Map)) {
            validationObject.condition = false;
            validationObject.message = fieldName + " is not a map";
            return;
        }
        Map map = (Map) (field.get(object));
        validationObject.condition = map.keySet().size() > 0;

        validationObject.message = fieldName + " is an empty map";
        validationObject.allowSoftFail = true;
    }

    private void checkNotEquals(Object object, String fieldName, String expectedValue, ValidationObject validationObject, Field field) throws IllegalAccessException
    {
        validationObject.condition = !String.valueOf(field.get(object)).equals(expectedValue);
        validationObject.message = fieldName + " value not equal. Expected: " + expectedValue + " Found: " + field.get(object);
        validationObject.allowSoftFail = true;
    }

    private void checkEquals(Object object, String fieldName, String expectedValue, ValidationObject validationObject, Field field) throws IllegalAccessException
    {
        validationObject.condition = String.valueOf(field.get(object)).equals(expectedValue);
        validationObject.message = fieldName + " value not equal. Expected: " + expectedValue + " Found: " + field.get(object);
        validationObject.allowSoftFail = true;
    }

    private void checkFalse(Object object, String fieldName, ValidationObject validationObject, Field field) throws IllegalAccessException
    {
        validationObject.condition = !field.getBoolean(object);
        validationObject.message = fieldName + " value was false. Found: " + field.get(object);
        validationObject.allowSoftFail = true;
    }

    private void checkTrue(Object object, String fieldName, ValidationObject validationObject, Field field) throws IllegalAccessException
    {
        validationObject.condition = field.getBoolean(object);
        validationObject.message = fieldName + " value was not true. Found: " + field.get(object);
        validationObject.allowSoftFail = true;
    }

    private void checkNotNull(Object object, String fieldName, ValidationObject validationObject, Field field) throws IllegalAccessException
    {
        validationObject.condition = field.get(object) != null && field.get(object) != "null";
        validationObject.message = fieldName + " value was null. Expected: not null";
        validationObject.allowSoftFail = true;
    }

    private void checkNull(Object object, String fieldName, ValidationObject validationObject, Field field) throws IllegalAccessException
    {
        validationObject.condition = field.get(object) == null || field.get(object) == "null";
        validationObject.message = fieldName + " value was not null. Found: " + field.get(object);
        validationObject.allowSoftFail = true;
    }

    private void checkFieldExists(String fieldName, ValidationObject validationObject, Field field) throws IllegalAccessError
    {
        validationObject.condition = field != null;
        validationObject.message = fieldName + " does not exist.";
        validationObject.allowSoftFail = true;
    }

    private void checkFieldContains(Object object, String fieldName, String expectedValue, ValidationObject validationObject, Field field) throws IllegalAccessException
    {
        validationObject.condition = String.valueOf(field.get(object)).contains(expectedValue);
        validationObject.message = fieldName + " does not contain Expected: " + expectedValue + " Found: " + field.get(object);
        validationObject.allowSoftFail = true;
    }
}
