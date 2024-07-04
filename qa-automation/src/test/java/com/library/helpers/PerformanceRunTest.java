package com.library.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tests.classes.PerformanceResponseObject;
import com.tests.classes.PerformanceResultObject;
import com.library.Log;
import com.library.Store;
import com.library.classes.PerformanceTestObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.CountingInputStream;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.UUID;

public class PerformanceRunTest extends Thread
{
    private ArrayList<PerformanceResponseObject> performanceResponseObjects;
    private ArrayList<PerformanceResultObject> performanceResultObjects;

    public PerformanceRunTest(PerformanceTestObject performanceTestObject, ArrayList<PerformanceResponseObject> performanceResponseObjects, ArrayList<PerformanceResultObject> performanceResultObjects)
    {
        this.performanceResponseObjects = performanceResponseObjects;
        this.performanceResultObjects = performanceResultObjects;
        internalRun(performanceTestObject);
    }

    public void internalRun(PerformanceTestObject performanceTestObject)
    {
        try {
            LocalDateTime start = LocalDateTime.now();
            URL url = new URI(performanceTestObject.getBaseUrl() + performanceTestObject.getEndpoint()).toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(String.valueOf(performanceTestObject.getRequestMethod()).toUpperCase());
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("User-Agent", "Agent");
            con.setRequestProperty("Accept", "application/ld+json");
            con.setRequestProperty("Authorization", "Bearer " + performanceTestObject.getBearer());
            con.setConnectTimeout(5000);
            con.setInstanceFollowRedirects(true);

            CountingInputStream countingInputStream = new CountingInputStream(con.getInputStream());
            String content = IOUtils.toString(countingInputStream, StandardCharsets.UTF_8);
            long size = countingInputStream.getByteCount();
            con.disconnect();
            LocalDateTime stop = LocalDateTime.now();
            long duration = ChronoUnit.MILLIS.between(start, stop);

            Object responseContent = content;
            boolean isJsonParsed = false;
            try {
                responseContent = new ObjectMapper().readTree(content);
                isJsonParsed = true;
            } catch (JsonProcessingException e) {
                Log.exception(e);
            }

            performanceResponseObjects.add(new PerformanceResponseObject(
                            start,
                            stop,
                            duration,
                            performanceTestObject.getBaseUrl() + performanceTestObject.getEndpoint(),
                            responseContent,
                            isJsonParsed
                    )
            );

            if (Store.getSettings().isVerbosePerformanceLogging()) {
                TextFileHelper.save("perf_" + UUID.randomUUID(), content);
            }

            performanceResultObjects.add(PerformanceResultObject.builder()
                    .baseUrl(performanceTestObject.getBaseUrl())
                    .endpoint(performanceTestObject.getEndpoint())
                    .httpResponseCode(con.getResponseCode())
                    .durationMs(duration)
                    .sizeBytes(size)
                    .build()
            );
        } catch (Exception e) {
            Log.exception(e);
        }
    }
}
