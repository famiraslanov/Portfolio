package com.library;

public class SoftFailException extends RuntimeException
{
    public SoftFailException(String message)
    {
        super(message);
    }
}

