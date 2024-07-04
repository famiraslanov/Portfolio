package com.library.helpers;

public class StringHelper
{
    public static String removeLabelsFromProfileName(String string)
    {
        return string.replace("I&P", "").trim();
    }

    public static String toKebabCase(String string)
    {
        return string.replaceAll("[^a-zA-Z0-9]", "-")
                .replaceAll("-+", "-")
                .replaceAll(" ", "-")
                .replaceAll("^-|-$", "").toLowerCase();
    }

    public static String toSnakeCase(String string)
    {
        return toKebabCase(string).replaceAll("-", "_");
    }
}
