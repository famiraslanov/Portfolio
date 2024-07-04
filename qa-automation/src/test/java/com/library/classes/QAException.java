package com.library.classes;

/**
 * Used when throwing exceptions in framework and such to differentiate our thrown exceptions when
 * no other java exceptions make sense.
 */
public class QAException extends RuntimeException
{
    public QAException()
    {
        super();
    }

    public QAException(String message)
    {
        super(message);
    }

    public QAException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public QAException(Throwable cause)
    {
        super(cause);
    }
}