package com.library.classes;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Getter
@Builder(access = AccessLevel.PACKAGE)
public class ThreadInfo
{
    private String name;
    @Builder.Default
    private long id = -1;
    private StackTraceElement[] stackTrace;

    public static ThreadInfo fromThread(Thread t)
    {
        return builder()
                .name(t.getName())
                .id(t.getId())
                .stackTrace(t.getStackTrace())
                .build();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("id", id)
                .toString();
    }

    public void updateStackTrace()
    {
        stackTrace = Thread.currentThread().getStackTrace();
    }
}
