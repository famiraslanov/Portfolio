package com.library.helpers;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.library.Log;
import com.library.classes.QAException;
import com.library.classes.ThreadStack;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AsyncOperations<ClassT>
{
    private final ExecutorService execPool;
    private final ExecutorService completionPool;

    private final Object lastRunLock = new Object();
    private LocalTime lastRun = LocalTime.now();

    @Getter
    private final String prefix;
    private final boolean throwException;
    private final Duration slowDuration;

    private final List<CompletableFuture<ClassT>> futures = Lists.newArrayList();

    @SuppressWarnings("unchecked")
    public AsyncOperations(int poolSize, String prefix)
    {
        this((AsyncOperationsBuilder<ClassT>) builder()
                .poolSize(poolSize)
                .prefix(prefix));
    }

    public AsyncOperations(AsyncOperationsBuilder<ClassT> builder)
    {
        this.prefix = builder.prefix;
        this.throwException = builder.throwException;
        this.slowDuration = builder.slowDuration;

        execPool = Executors.newFixedThreadPool(builder.poolSize, new ThreadFactoryBuilder()
                .setNameFormat(prefix + "-%d").build());
        completionPool = Executors.newCachedThreadPool(new ThreadFactoryBuilder()
                .setNameFormat(prefix + "Complete-%d").build());
    }

    public static <BuilderT> AsyncOperationsBuilder<BuilderT> builder()
    {
        return new AsyncOperationsBuilder<>();
    }

    public static class AsyncOperationsBuilder<BuilderT>
    {
        private int poolSize = 10;
        private String prefix;
        private final boolean throwException = true;
        private final Duration slowDuration = Duration.ZERO;

        public AsyncOperationsBuilder<BuilderT> poolSize(int poolSize)
        {
            this.poolSize = poolSize;
            return this;
        }

        public AsyncOperationsBuilder<BuilderT> prefix(String prefix)
        {
            this.prefix = prefix;
            return this;
        }

        public AsyncOperations<BuilderT> build()
        {
            if (StringUtils.isEmpty(prefix)) {
                throw new QAException("Prefix must be supplied for AsyncOperations");
            }
            return new AsyncOperations<>(this);
        }

    }

    public interface Submittable
    {
    }

    public interface BasicSubmittable<T> extends Submittable
    {
        T run();
    }

    public interface DetailsSubmittable<T> extends Submittable
    {
        T run(AsyncOperations<T> asyncOperations);
    }

    @SuppressWarnings("UnusedReturnValue")
    public CompletableFuture<ClassT> submitRunnable(BasicSubmittable<ClassT> supplier)
    {
        return privateSubmitRunnable(supplier);
    }

    @SuppressWarnings("unchecked")
    private CompletableFuture<ClassT> privateSubmitRunnable(Submittable supplier)
    {
        Thread parentThread = Thread.currentThread();
        String parentThreadName = parentThread.getName();
        ThreadStack parentThreadStack = ThreadStack.getInstance();

        if (parentThreadName.contains("." + prefix + "-")) {
            Log.debug(String.format("Cannot nest AsyncOperations with itself %s :: %s", prefix, parentThreadName), true);
        }

        CompletableFuture<ClassT> future = CompletableFuture.supplyAsync(() -> {
            ThreadStack.addParentThreadStack(parentThreadStack);
            Thread currentThread = Thread.currentThread();
            String oldName = currentThread.getName();
            if (oldName.contains(".")) {
                Log.error("Old Thread name contained '.' " + oldName, true);
                throw new QAException("Old Thread name contained '.' " + oldName);
            }
            currentThread.setName(parentThreadName + "." + currentThread.getName());
            try {
                if (!slowDuration.equals(Duration.ZERO)) {
                    synchronized (lastRunLock) {
                        Duration duration = Duration.between(lastRun, LocalTime.now());
                        if (duration.compareTo(Duration.ZERO) < 0) {
                            duration = Duration.ZERO;
                        }
                        if (duration.compareTo(slowDuration) <= 0) {
                            Duration sleepTime = slowDuration.minus(duration);
                            Log.debug(String.format("%s -- sleeping for %d ms", prefix, sleepTime.toMillis()), true);
                            Function.sleep(sleepTime, "Async sleeping");
                            lastRun = LocalTime.now();
                        }
                    }
                }

                if (supplier instanceof BasicSubmittable) {
                    return ((BasicSubmittable<ClassT>) supplier).run();
                } else if (supplier instanceof DetailsSubmittable) {
                    return ((DetailsSubmittable<ClassT>) supplier).run(this);
                } else {
                    throw new IllegalArgumentException("AsyncOperations doesn't understand " + supplier.getClass().getSimpleName());
                }
            } catch (Throwable t) {
                Log.error(String.format("Caught throwable with %s :: %s", prefix, t.getMessage()), true);
                if (!throwException) {
                    return null;
                }
                throw t;
            } finally {
                ThreadStack.clearStack();
                currentThread.setName(oldName);
            }
        }, execPool);
        synchronized (futures) {
            futures.add(future);
        }
        return future;
    }

    public void waitForComplete(Duration futureDuration)
    {
        Thread parentThread = Thread.currentThread();
        String parentThreadName = parentThread.getName();

        List<CompletableFuture<Void>> completionList = Lists.newArrayList();
        Lists.newArrayList(futures)
                .stream()
                .filter(f -> !f.isDone() || f.isCompletedExceptionally())
                .forEach(f -> completionList.add(CompletableFuture.runAsync(() -> {
                    Thread currentThread = Thread.currentThread();
                    String oldName = currentThread.getName();
                    Thread.currentThread().setName(parentThreadName + "." + oldName);
                    try {
                        f.get(futureDuration.toMillis(), TimeUnit.MILLISECONDS);
                    } catch (Exception ex) {
                        Log.exception(ex, true);
                    } finally {
                        currentThread.setName(oldName);
                        Log.debug("Thread " + currentThread.getId() + " Reset thread name to " + Thread.currentThread().getName(), true);
                    }
                }, completionPool)));

        CompletableFuture<Void> allCompletable = CompletableFuture.allOf(completionList.toArray(new CompletableFuture[]{}));
        try {
            allCompletable.get();
            Log.info(String.format("Done waiting for each %s (%d)", prefix, completionList.size()), true);
        } catch (Exception e) {
            Log.error(String.format("Error waiting for all %s futures (%d) to complete: Error: %s", prefix, completionList.size(), e.getMessage()), true);
        }
    }

    public long fetchQueueSize()
    {
        return Lists.newArrayList(futures)
                .stream()
                .filter(f -> !f.isDone())
                .count();
    }
}