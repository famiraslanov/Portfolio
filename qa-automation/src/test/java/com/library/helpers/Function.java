package com.library.helpers;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.io.Resources;
import com.library.*;
import com.library.enums.DriverType;
import org.openqa.selenium.WebElement;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class Function
{
    public static void load(String url)
    {
        Log.info("Load url: " + url);
        Store.getDriver().get(url);
    }

    public static String getCurrentUrl()
    {
        String currentUrl = null;
        if (Store.getSettings().getDriverType() != DriverType.none && Store.getDriver() != null) {
            try {
                currentUrl = Store.getDriver().getCurrentUrl();
            } catch (Exception e) {
                Log.exception(e);
                currentUrl = "Unable to retrieve";
            }
        }
        return currentUrl;
    }

    public static void closeCurrentTab()
    {
        Store.getDriver().close();
    }

    public static String getOriginalWindow()
    {
        return Store.getDriver().getWindowHandle();
    }

    public static void switchToSelectedWindow(String winHandle)
    {
        Store.getDriver().switchTo().window(winHandle);
    }

    public static void switchWindow(FindOptions lookFor)
    {
        WebElement pageFound = null;
        for (String winHandle : Store.getDriver().getWindowHandles()) {
            Store.getDriver().switchTo().window(winHandle);
            if (lookFor != null) {
                lookFor.failOnNotFound(false);
                pageFound = Find.element(lookFor);
                if (pageFound != null) {
                    Log.debug("Page found");
                    return;
                }
            }
        }

        if (lookFor != null) {
            Verify.fail("Switch window did not find the expected element. Windows checked: " + Store.getDriver().getWindowHandles().size());
        }
    }

    public static void switchFrame(FindOptions lookFor)
    {
        Store.getDriver().switchTo().frame(Find.element(lookFor));
    }

    public static void switchDefaultFrame()
    {
        Store.getDriver().switchTo().defaultContent();
    }

    public static String asJson(Object object)
    {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Unable to convert Object to String";
        }
    }

    public static <T> T fromJson(String json, Class<T> classType)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, classType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            Log.error("Unable to convert String to Object");
            Log.error("String parsed: " + json);
        }
        return null;
    }

    public static void slowEnvironmentWait()
    {
        slowEnvironmentWait(Duration.ofMillis(Store.getSettings().getSlowEnvironmentWaitMS()));
    }

    public static void slowEnvironmentWait(Duration duration)
    {
        duration = duration.plus(Duration.ofMillis(Store.getSettings().getSlowEnvironmentWaitMS()));
        slowEnvironmentWait(duration, "Slow environment sleep");
    }

    public static void slowEnvironmentWait(Duration duration, String message)
    {
        sleep(duration, message);
    }

    public static void sleep(Duration duration, String message)
    {
        if (duration.toMillis() > 100) {
            Log.warning("Sleeping for: " + duration + " " + message);
        }
        try {
            Thread.sleep(duration.toMillis());
        } catch (Exception e) {
            Log.exception(e);
        }
    }

    public static List<StackTraceElement> getStackTrace()
    {
        return getStackTrace(Thread.currentThread().getStackTrace());
    }

    public static List<StackTraceElement> getStackTrace(StackTraceElement[] stack)
    {
        return Arrays.stream(stack).filter(se ->
                se.getClassLoaderName() != null &&
                        se.getClassLoaderName().equals("app") &&
                        !se.getFileName().equals("Log.java") &&
                        !se.getMethodName().equals("getStackTrace")
        ).toList();
    }

    public static boolean isAppleM2()
    {
        String details;
        if ("Mac OS X".equals(System.getProperty("os.name"))) {
            ProcessBuilder pb = new ProcessBuilder("sysctl", "-n", "machdep.cpu.brand_string");
            try {
                Process p = pb.start();
                BufferedReader br = p.inputReader();
                details = br.readLine();
                int status = p.waitFor();
                if (status == 0) {
                    return details.equals("Apple M2");
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static String readResourcesFile(String fileName) throws IOException
    {
        URL url = Resources.getResource(fileName);
        return Resources.toString(url, StandardCharsets.UTF_8);
    }
}
