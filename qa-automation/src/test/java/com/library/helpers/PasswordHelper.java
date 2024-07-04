package com.library.helpers;

import org.jasypt.util.text.BasicTextEncryptor;
import org.testng.Assert;

public class PasswordHelper
{
    public static String decryptPassword(String key, String encrypted)
    {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(PasswordHelper.checkKey(key));
        return textEncryptor.decrypt(encrypted);
    }

    public static String encryptPassword(String key, String plainText)
    {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(PasswordHelper.checkKey(key));
        return textEncryptor.encrypt(plainText);
    }

    private static String checkKey(String key)
    {
        Assert.assertNotNull(key, "Expected passwordKey to be set");
        Assert.assertNotEquals(key, "not_set", "Expected passwordKey to be set");
        return key;
    }
}