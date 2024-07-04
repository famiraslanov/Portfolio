package com.library;

import com.tests.classes.PerformanceAnalysis;
import com.tests.classes.PerformanceAnalysisObject;
import com.tests.classes.PerformanceResponseObject;
import com.tests.classes.PerformanceResultObject;
import com.library.classes.PerformanceTestObject;
import com.library.helpers.ApiHelper;
import com.library.helpers.PerformanceRunTest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PerformanceRunner
{
    private final ArrayList<PerformanceResponseObject> performanceResponseObjects = new ArrayList<>();
    private final ArrayList<PerformanceResultObject> performanceResultObjects = new ArrayList<>();

    public void run(PerformanceTestObject performanceTestObject)
    {
        boolean isSmoke = Store.getSettings().isSimpleSmoke();

        if (isSmoke) {
            Log.debug("Running as simpleSmoke");
            performanceTestObject.setDuration(Duration.ofSeconds(1));
            performanceTestObject.setNumberOfThreads(1);
        }

        ExecutorService executor = Executors.newFixedThreadPool(performanceTestObject.getNumberOfThreads());
        LocalDateTime end = LocalDateTime.now().plus(performanceTestObject.getDuration());

        Log.object("Url to call", performanceTestObject.getBaseUrl() + performanceTestObject.getEndpoint());

        while (end.isAfter(LocalDateTime.now())) {
            Runnable worker = new PerformanceRunTest(performanceTestObject, performanceResponseObjects, performanceResultObjects);
            executor.execute(worker);
        }
        executor.shutdown();

        //noinspection StatementWithEmptyBody
        while (!executor.isTerminated()) {
            // Final check to make sure all threads finished
        }

        Log.debug("Total calls made: " + performanceResponseObjects.size());

        if (!isSmoke) {
            Log.performance(performanceResultObjects);
        }

        if (Store.getSettings().isVerbosePerformanceLogging()) {
            for (PerformanceResponseObject performanceResponseObject : performanceResponseObjects) {
                Log.object("Call", performanceResponseObject);
            }
        }
        
        processResults(performanceTestObject, isSmoke);
    }

    private void processResults(PerformanceTestObject performanceTestObject, boolean isSmoke)
    {
        PerformanceAnalysisObject total = summaryToObject(
                performanceResultObjects.stream()
                        .map(PerformanceResultObject::getDurationMs)
                        .mapToLong(l -> l)
                        .summaryStatistics()
        );

        PerformanceAnalysisObject without = summaryToObject(performanceResultObjects.stream()
                .map(PerformanceResultObject::getDurationMs)
                .mapToLong(l -> l)
                .sorted()
                .filter(l -> l < total.max)
                .summaryStatistics()
        );

        DoubleSummaryStatistics pastPerformance = Arrays.stream(Objects.requireNonNull(ApiHelper.fetchPreviousPerformanceData()))
                .map(o -> o.total.average)
                .mapToDouble(o -> o)
                .summaryStatistics();

        if (!isSmoke) {
            Log.object("PastPerformance", new PerformanceAnalysisObject(
                    pastPerformance.getCount(),
                    (long) pastPerformance.getMin(),
                    (long) pastPerformance.getMax(),
                    pastPerformance.getAverage()
            ));
        }

        PerformanceAnalysis performanceAnalysis = new PerformanceAnalysis(
                total,
                without
        );

        if (!isSmoke) {
            Log.object("PerformanceAnalysis", performanceAnalysis);

            double under = pastPerformance.getAverage() - (pastPerformance.getAverage() * performanceTestObject.getTolerancePercentage());
            double over = pastPerformance.getAverage() + (pastPerformance.getAverage() * performanceTestObject.getTolerancePercentage());

            Log.story("Expecting the average response time to be between " + String.format("%.2f", under) + "ms and " + String.format("%.2f", over) + "ms (or under) Actual: " + String.format("%.2f", performanceAnalysis.total.average));
            Verify.isTrue(performanceAnalysis.total.average <= over, "The average response time was not within " + performanceTestObject.getTolerancePercentage() + " of the past average of " + String.format("%.2f", pastPerformance.getAverage()) + " Over by: " + String.format("%.2f", over - pastPerformance.getAverage()) + "ms");
        }

        checkAllCallsValid();

        Verify.isTrue(performanceAnalysis.total.calls > 0, "No successful calls made in the duration");
    }

    private void checkAllCallsValid()
    {
        long validCalls = performanceResponseObjects.stream().filter(r -> r.validJson).count();
        Log.story("Total calls: " + performanceResponseObjects.size() + " Valid Json calls: " + validCalls);
        Verify.isTrue(performanceResponseObjects.size() == validCalls, "Not all calls produced valid Json");
    }

    private PerformanceAnalysisObject summaryToObject(LongSummaryStatistics statistics)
    {
        return new PerformanceAnalysisObject(
                statistics.getCount(),
                statistics.getMin(),
                statistics.getMax(),
                statistics.getAverage()
        );
    }
}


