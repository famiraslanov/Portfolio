package com.library.classes;

import org.testng.SkipException;

public class NotRunException extends SkipException
{
    public NotRunException(String skipMessage)
    {
        super(skipMessage);
    }
}