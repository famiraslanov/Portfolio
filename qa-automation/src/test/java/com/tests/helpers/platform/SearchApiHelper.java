package com.tests.helpers.platform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.Log;
import com.library.enums.Environment;
import com.library.helpers.ApiHelper;
import com.library.listeners.QASuiteListener;
import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.api.SearchApiUrls;
import com.tests.helpers.platform.models.ArticleApiModel;
import com.tests.helpers.platform.models.FundApiModel;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SearchApiHelper extends ApiHelper
{

    private static Environment environment = QASuiteListener.settings.getEnvironment();

    private static String getBaseUrl()
    {
        return switch (environment) {
            case staging -> SearchApiUrls.staging.url;
            case prod -> SearchApiUrls.prod.url;
            default -> SearchApiUrls.dev.url;
        };
    }

    private static List<String> getTopEntity(Map<String, Object> jsonResponse, String entityType, int limit, AssetClass assetClass)
    {
        Map<String, Object> entity = (Map<String, Object>) ((Map<String, Object>) jsonResponse.get("aggregations")).get(entityType);
        List<Map<String, Object>> buckets = (List<Map<String, Object>>) entity.get("buckets");
        List<Map.Entry<String, Integer>> entries = new ArrayList<>();

        for (Map<String, Object> bucket : buckets) {
            String key = (String) bucket.get("key");
            int docCount = (int) bucket.get("doc_count");
            entries.add(new AbstractMap.SimpleEntry<>(key, docCount));
        }

        List<String> entities = entries.stream()
                .limit(limit)
                .map(Map.Entry::getKey)
                .toList();

        List<String> resultEntities = new ArrayList<>();
        for (String entityBase : entities) {
            if (SearchApiHelper.isAvailableEntities(Integer.parseInt(entityBase.split("\\|")[1]), entityType, assetClass)) {
                resultEntities.add(entityBase);
            }
        }
        return resultEntities;
    }

    public static List<String> getTrendingEntity(AssetClass assetClass, String entityType)
    {
        Map<String, Object> jsonResponse = null;
        LocalDateTime prevMonth = LocalDateTime.now().minusMonths(1);
        try {
            Response response = ApiHelper.getClientBuilder().build()
                    .target(getBaseUrl())
                    .path("/articles")
                    .queryParam("_aggregations", entityType)
                    .queryParam("post_date[from]", prevMonth.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .queryParam("article_type_id_not", 6)
                    .queryParam("limit", 1)
                    .request()
                    .header("website", assetClass.websiteApiHeader)
                    .get();
            if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
                jsonResponse = response.readEntity(Map.class);
            } else {
                Log.error("Error: " + response.getStatus());
            }
        } catch (Exception e) {
            Log.exception(e);
        }
        return getTopEntity(jsonResponse, entityType, 7, assetClass);
    }

    public static List<String> getTrendingProfiles(AssetClass assetClass, String entityType)
    {
        Map<String, Object> jsonResponse = null;
        try {
            Response response = ApiHelper.getClientBuilder().build()
                    .target(getBaseUrl())
                    .path("/articles")
                    .queryParam("limit", 1)
                    .queryParam("article_type_slug_not", "document_descriptor")
                    .queryParam("_aggregations", entityType)
                    .request()
                    .header("website", assetClass.websiteApiHeader)
                    .get();
            if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
                jsonResponse = response.readEntity(Map.class);
            } else {
                Log.error("Error: " + response.getStatus());
            }
        } catch (Exception e) {
            Log.exception(e);
        }
        return getTopEntity(jsonResponse, entityType, 50, assetClass);
    }

    public static List<String> getTrendingMandateProfiles(AssetClass assetClass)
    {
        Map<String, Object> jsonResponse = null;
        try {
            Response response = ApiHelper.getClientBuilder().build()
                    .target(getBaseUrl())
                    .path("/mandates")
                    .queryParam("limit", 100)
                    .queryParam("_source", "date|institutional_investor.name|status")
                    .queryParam("order", "desc")
                    .queryParam("sort", "updated_at")
                    .queryParam("status", "232|1092093512|233|777719172")
                    .request()
                    .header("website", assetClass.websiteApiHeader)
                    .get();
            if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
                jsonResponse = response.readEntity(Map.class);
            } else {
                Log.error("Error: " + response.getStatus());
            }
        } catch (Exception e) {
            Log.exception(e);
        }

        List<Map> resultsArray = (List<Map>) jsonResponse.get("results");
        List<String> institutionalInvestorNames = new ArrayList<>();

        for (int i = 0; i < resultsArray.size(); i++) {
            institutionalInvestorNames.add((String) ((Map) resultsArray.get(i).get("institutional_investor")).get("name"));
        }
        return institutionalInvestorNames;
    }

    public static double getYDT(int fundId)
    {
        String jsonResponse = null;
        try {
            Response response = ApiHelper.getClientBuilder().build()
                    .target(getBaseUrl())
                    .path("/funds")
                    .queryParam("_source", "open_end_fund_performance.compound.year_to_date|id|name|indexed_at")
                    .queryParam("id", fundId)
                    .request()
                    .get();
            if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
                jsonResponse = response.readEntity(String.class);
            } else {
                Log.error("Error: " + response.getStatus());
            }
        } catch (Exception e) {
            Log.exception(e);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(jsonResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Log.object("JsonNode", jsonNode);

        return jsonNode
                .get("results").get(0)
                .get("open_end_fund_performance")
                .get("compound")
                .get("year_to_date")
                .asDouble() * 100;
    }

    public static List<String> getEkhFunds(AssetClass assetClass)
    {
        Map<String, Object> jsonResponse = null;
        try {
            Response response = ApiHelper.getClientBuilder().build()
                    .target(getBaseUrl())
                    .path("/funds")
                    .queryParam("is_gold", true)
                    .queryParam("is_active", true)
                    .queryParam("_source", "id|name|has_ekh_fund_id|ekh_fund_id")
                    .request()
                    .header("website", assetClass.websiteApiHeader)
                    .get();
            if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
                jsonResponse = response.readEntity(Map.class);
            } else {
                Log.error("Error: " + response.getStatus());
            }
        } catch (Exception e) {
            Log.exception(e);
        }
        List<Map> resultsArray = (List<Map>) jsonResponse.get("results");
        List<String> fundsNames = new ArrayList<>();

        for (Map<String, Object> map : resultsArray) {
            Boolean hasEkhFundId = (Boolean) map.get("has_ekh_fund_id");
            int id = (Integer) map.get("id");
            if (hasEkhFundId != null && hasEkhFundId && isAvailableEntities(id, "funds", assetClass)) {
                String name = (String) map.get("name");
                fundsNames.add(name);
            }
        }
        return fundsNames;
    }

    public static List<ArticleApiModel.Result> getArticle(int postId, AssetClass assetClass)
    {
        try {
            Response response = ApiHelper.getClientBuilder().build()
                    .target(getBaseUrl())
                    .path("/articles")
                    .queryParam("post_id", postId)
                    .request()
                    .header("website", assetClass.websiteApiHeaderArticle)
                    .get();

            if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
                List<ArticleApiModel.Result> results = response.readEntity(ArticleApiModel.class).getResults();
                Log.object("ArticleApiResults", results);
                return results;
            } else {
                Log.error("Error: " + response.getStatus());
            }
        } catch (Exception e) {
            Log.exception(e);
        }
        return null;
    }

    public static List<String> getListOfNavigationTags(int postId, AssetClass assetClass)
    {
        List<String> tagNames = new ArrayList<>();
        List<ArticleApiModel.Result> results = getArticle(postId, assetClass);
        if (!results.isEmpty()){
            ArticleApiModel.Result result = results.get(0);
            List<ArticleApiModel.Navigation> navigations = result.getTerms().getNavigation();
            for (ArticleApiModel.Navigation navigation : navigations) {
                String name = navigation.getName();
                tagNames.add(name);
            }
        }
        return tagNames;
    }

    public static List<FundApiModel.Result> getFundData(int fundId)
    {
        try {
            Response response = ApiHelper.getClientBuilder().build()
                    .target(getBaseUrl())
                    .path("/funds")
                    .queryParam("_source", "id|name|has_ekh_fund_id|ekh_fund_id|latest_aum|latest_aum_date|latest_strategy_aum|latest_strategy_aum_date|show_fund_performance|show_fund_profile|is_closed_end|show_fund_aum|is_flagship|currency")
                    .queryParam("id", fundId)
                    .request()
                    .get();
            if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
                List<FundApiModel.Result> results = response.readEntity(FundApiModel.class).getResults();
                Log.object("FundApiModelResult", results);
                return results;
            } else {
                Log.error("Error: " + response.getStatus());
            }
        } catch (Exception e) {
            Log.exception(e);
        }
        return null;
    }


    public static boolean isAvailableEntities(int id, String entityType, AssetClass assetClass)
    {
        if (entityType.equals("people")){
            entityType = "persons";
        }
        try {
            Map<String, Object> jsonResponse = null;
            Response response = ApiHelper.getClientBuilder().build()
                    .target(getBaseUrl())
                    .path("/" + entityType)
                    .queryParam("id", id)
                    .request()
                    .header("website", assetClass.websiteApiHeader)
                    .get();
            if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
                jsonResponse = response.readEntity(Map.class);
                List<Map> resultsArray = (List<Map>) jsonResponse.get("results");
                return resultsArray.size() > 0;
            } else {
                Log.error("Error: " + response.getStatus());
            }
        } catch (Exception e) {
            Log.exception(e);
            return false;
        }
        return false;
    }
}
