package com.library.helpers;

import com.library.Log;
import com.library.Store;
import com.library.enums.MethodType;
import com.library.listeners.QASuiteListener;
import com.microsoft.graph.models.Message;
import com.tests.classes.*;
import com.tests.enums.api.ApiUrl;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.jersey.logging.LoggingFeature;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

public class ApiHelper
{
    private static ApiHelper instance;
    private ClientBuilder clientBuilder;

    private static ApiHelper getInstance()
    {
        if (instance == null) {
            instance = new ApiHelper();
            instance.clientBuilder = ClientBuilder.newBuilder();

            if (QASuiteListener.settings.isApiLogging()) {
                Logger logger = Logger.getLogger("loggers");
                logger.addHandler(new StreamHandler(System.out, new SimpleFormatter()));

                Feature feature = new LoggingFeature(logger, Level.INFO, LoggingFeature.Verbosity.PAYLOAD_ANY, null);
                instance.clientBuilder.register(feature);
            }
        }
        return instance;
    }

    public static ClientBuilder getClientBuilder()
    {
        return ApiHelper.getInstance().clientBuilder;
    }

    public static QAToolsResponseObject log(Object postObject)
    {
        QAToolsResponseObject qaToolsResponseObject = null;
        String plainResponse = null;
        try {
            plainResponse = getClientBuilder().build()
                    .target(QASuiteListener.settings.getResultsHost())
                    .path("/automationResults/log/" + UUID.randomUUID())
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.entity(postObject, MediaType.APPLICATION_JSON), String.class);

            qaToolsResponseObject = Function.fromJson(plainResponse, QAToolsResponseObject.class);
        } catch (Exception e) {
            Log.error(e.getMessage());
            Log.error("PlainResponse: " + plainResponse);
            Log.exception(e);
        }

        if (qaToolsResponseObject == null || !qaToolsResponseObject.isOk()) {
            Log.nonTrackLog("Unable to add the log. Response: " + (qaToolsResponseObject != null ? qaToolsResponseObject.getError() : "NULL"));

            if (qaToolsResponseObject == null) {
                qaToolsResponseObject = new QAToolsResponseObject();
                qaToolsResponseObject.setOk(false);
                qaToolsResponseObject.setResponse("Unkown error");
            }
        }

        return qaToolsResponseObject;
    }

    public static DelayedActionObject fetchNextDelayedAction(boolean retry)
    {
        try {
            return getClientBuilder().build()
                    .target(QASuiteListener.settings.getResultsHost())
                    .path("/automationResults/nextDelayedAction/" + UUID.randomUUID())
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.entity(new HashMap<>()
                            {{
                                put("runId", QASuiteListener.runId);
                                put("retry", retry ? 1 : 0);
                                put("actionResultId", Store.getTestId());
                            }}, MediaType.APPLICATION_JSON),
                            DelayedActionObject.class);
        } catch (Exception e) {
            Log.exception(e);
        }

        return null;
    }

    public static void writeAPIData()
    {
        try {
            Map<String, ApiUrlObject> apiUrlObjects = new HashMap<>();
            for (Method localMethod : Class.forName("com.tests.classes.ApiUrlObjects").getMethods()) {
                if (Modifier.isStatic(localMethod.getModifiers())) {
                    apiUrlObjects.put(localMethod.getName(), (ApiUrlObject) localMethod.invoke(null));
                }
            }

            Log.object("Response", getClientBuilder().build()
                    .target(QASuiteListener.settings.getResultsHost())
                    .path("/automationResults/writeAPIData")
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.entity(new HashMap<>()
                            {
                                {
                                    put("data", apiUrlObjects);
                                }
                            }, MediaType.APPLICATION_JSON),
                            Map.class));
        } catch (Exception e) {
            Log.exception(e);
        }
    }

    public static PendingEmail[] fetchPendingEmails()
    {
        try {
            return getClientBuilder().build()
                    .target(QASuiteListener.settings.getResultsHost())
                    .path("/automationResults/fetchPendingEmails")
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.entity(new HashMap<>()
                            {{
                                put("runId", QASuiteListener.runId);
                            }}, MediaType.APPLICATION_JSON),
                            PendingEmail[].class);
        } catch (Exception e) {
            Log.exception(e);
        }

        return null;
    }

    public static boolean markEmailReceived(int rowId, Message message)
    {
        try {
            return getClientBuilder().build()
                    .target(QASuiteListener.settings.getResultsHost())
                    .path("/automationResults/markEmailReceived")
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.entity(new HashMap<>()
                            {{
                                put("rowId", rowId);
                                if (message.body != null) {
                                    put("emailContent", Function.asJson(message.body.content));
                                }
                            }}, MediaType.APPLICATION_JSON),
                            QAToolsResponseObject.class)
                    .isOk();
        } catch (Exception e) {
            Log.exception(e);
        }

        return false;
    }

    public static PerformanceAnalysis[] fetchPreviousPerformanceData()
    {
        try {
            return getClientBuilder().build()
                    .target(QASuiteListener.settings.getResultsHost())
                    .path("/automationResults/fetchPerformanceData")
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.entity(new HashMap<>()
                            {{
                                put("rowId", Store.getTestId());
                                put("showOther", 1);
                            }}, MediaType.APPLICATION_JSON),
                            PerformanceAnalysis[].class);
        } catch (Exception e) {
            Log.exception(e);
        }

        return null;
    }

    public static <T> T domApiCall(MethodType methodType, ApiUrl apiUrl, String path, Object requestDto, Class<T> responseDtoType, WebApplicationException expectException)
    {
        String auth = "Bearer " + apiUrl.authToken;
        WebTarget webTarget = ApiHelper.getClientBuilder().build()
                .target(apiUrl.url)
                .path(path);

        if (methodType == MethodType.get) {
            webTarget = addRequestParam(webTarget, requestDto);
        }

        Map<String, Object> logObject = new HashMap<>();
        logObject.put("MethodType", methodType);
        logObject.put("Target", apiUrl.url);
        logObject.put("Path", path);
        logObject.put("AuthToken", auth);
        logObject.put("RequestDto", requestDto);
        logObject.put("FullUrl", webTarget.getUri());
        Log.object("ApiCall", logObject);

        Invocation.Builder builder = webTarget.request("application/ld+json")
                .header("Authorization", auth);

        T response = null;
        try {
            response = switch (methodType) {
                case get -> builder.get(responseDtoType);
                case post -> builder.post(Entity.entity(requestDto, MediaType.APPLICATION_JSON), responseDtoType);
                case put -> builder.put(Entity.entity(requestDto, MediaType.APPLICATION_JSON), responseDtoType);
                case patch -> builder.put(Entity.entity(requestDto, MediaType.APPLICATION_JSON), responseDtoType);
                case delete -> builder.delete(responseDtoType);
            };
            Log.object("ApiResponse", response);
            return response;
        }
        catch (WebApplicationException exception){
            Log.object("ApiResponse", response);
            if (expectException != null && exception.getClass().isAssignableFrom(expectException.getClass())) {
                return null;
            }
            throw exception;
        }
    }

    public static <T> T domApiCall(MethodType methodType, ApiUrl apiUrl, String path, Object requestDto, Class<T> responseDtoType)
    {
        return domApiCall(methodType, apiUrl, path, requestDto, responseDtoType, null);
    }

    private static WebTarget addRequestParam(WebTarget webTarget, Object o)
    {
        if (o != null) {
            try {
                if (o instanceof Map) {
                    return addRequestParam(webTarget, (Map<String, Object>) o);
                }
                for (Field f : o.getClass().getDeclaredFields()) {
                    f.setAccessible(true);
                    webTarget = webTarget.queryParam(f.getName(), f.get(o).toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return webTarget;
    }

    private static WebTarget addRequestParam(WebTarget webTarget, Map<String, Object> params)
    {
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String paramName = entry.getKey();
                Object paramValue = entry.getValue();
                if (paramValue != null) {
                    webTarget = webTarget.queryParam(paramName, paramValue.toString());
                }
            }
        }
        return webTarget;
    }
}
