package com.library.helpers;

import com.library.Log;
import com.library.Settings;
import com.library.Store;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Properties;

public class SettingsHelper
{
    private static SettingsHelper instance = null;
    protected Properties settingsDefault;
    protected Properties settingsLocal;

    public SettingsHelper()
    {
        settingsDefault = loadPropertiesFromFile("settings.default");
        settingsLocal = loadPropertiesFromFile("settings.local");
    }

    public static SettingsHelper getInstance()
    {
        if (instance == null) {
            instance = new SettingsHelper();
        }
        return instance;
    }

    public String getProperty(String setting)
    {
        String property = getCLIParams(setting);

        if (property != null) {
            return convertNull(property);
        }

        if (settingsLocal != null && settingsLocal.containsKey(setting)) {
            return convertNull(settingsLocal.getProperty(setting));
        }

        if (!settingsDefault.isEmpty() && settingsDefault.containsKey(setting)) {
            return convertNull(settingsDefault.getProperty(setting));
        }

        return null;
    }

    private Properties loadPropertiesFromFile(String fileName)
    {
        Properties returnProperties = new Properties();
        try {
            InputStream inputStream = Settings.class.getClassLoader().getResourceAsStream(fileName);
            if (inputStream != null) {
                returnProperties.load(inputStream);
                inputStream.close();
            }
        } catch (IOException ioException) {
            Log.info("No settings file: " + fileName);
        }
        return returnProperties;
    }

    private String getCLIParams(String settingName)
    {
        String returnValue = null;
        String key = "Settings." + settingName;
        if (System.getProperties().containsKey(key)) {
            returnValue = System.getProperty(key);
        }
        return returnValue;
    }

    private String convertNull(String value)
    {
        if (value != null && value.equals("null")) {
            value = null;
        }
        return value;
    }

    public static String cleanSettings()
    {
        String[] toClean = {"key", "pass", "pwd", "token"};

        Settings storeSettings = new Settings();

        Field[] fields = Settings.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (!field.getName().equals("msGraphKeys")) {
                try {
                    field.set(storeSettings, field.get(Store.getSettings()));
                    if (Arrays.stream(toClean).anyMatch(f -> field.getName().toLowerCase().contains(f.toLowerCase()))) {
                        field.set(storeSettings, "{***hidden***}");
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return Function.asJson(storeSettings);
    }
}
