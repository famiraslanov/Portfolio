package com.library.classes;

import com.library.helpers.AsyncOperations;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Stack;

/**
 * This is used for parent threads to set themselves to the child threads for tracking stack trace
 * elements.  This actually tracks all the threads in a Stack so the get() returns the top of the Stack
 * to get the last registered thread (hopefully the calling thread)
 *
 * @see AsyncOperations
 */

public class ThreadStack
{
    private static final ThreadLocal<ThreadStack> localThreadStack = new ThreadLocal<>();
    @Getter
    private final ThreadInfo currentThread = ThreadInfo.fromThread(Thread.currentThread());
    private final Stack<ThreadInfo> threadStack = new Stack<>();

    public static ThreadStack getInstance()
    {
        if (localThreadStack.get() == null) {
            localThreadStack.set(new ThreadStack());
        }
        ThreadStack ts = localThreadStack.get();
        ts.currentThread.updateStackTrace();

        return ts;
    }

    public static void addParentThreadStack(ThreadStack parentThreadStack)
    {
        ThreadStack ts = getInstance();
        ts.threadStack.clear();
        ts.threadStack.addAll(parentThreadStack.threadStack);
        ts.threadStack.add(parentThreadStack.getCurrentThread());
    }

    public static ThreadInfo getParentThread()
    {
        ThreadStack ts = getInstance();
        Stack<ThreadInfo> currentList = ts.threadStack;
        if (CollectionUtils.isNotEmpty(currentList)) {
            return currentList.peek();
        }
        return null;
    }

    public static Stack<ThreadInfo> getThreadStack()
    {
        Stack<ThreadInfo> returningStack = new Stack<>();
        Stack<ThreadInfo> currentList = getInstance().threadStack;

        if (CollectionUtils.isEmpty(currentList)) {
            return returningStack;
        }

        returningStack.addAll(getInstance().threadStack);
        return returningStack;
    }

    public static void clearStack()
    {
        // Nothing done here currently
    }
}
