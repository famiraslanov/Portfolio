package com.tests.helpers.platform.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FundApiModel
{
    @JsonProperty("results")
    private List<Result> results;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result
    {
        @JsonProperty("latest_aum_date")
        private String latestAumDate;
        @JsonProperty("latest_strategy_aum")
        private double latestStrategyAum;
        @JsonProperty("show_fund_performance")
        private boolean showFundPerformance;
        @JsonProperty("show_fund_aum")
        private boolean showFundAum;
        @JsonProperty("has_ekh_fund_id")
        private boolean hasEkhFundId;
        @JsonProperty("is_flagship")
        private boolean isFlagship;
        @JsonProperty("show_fund_profile")
        private boolean showFundProfile;
        @JsonProperty("ekh_fund_id")
        private int ekhFundId;
        @JsonProperty("latest_aum")
        private double latestAum;
        @JsonProperty("latest_strategy_aum_date")
        private String latestStrategyAumDate;
        @JsonProperty("is_closed_end")
        private boolean isClosedEnd;
        @JsonProperty("name")
        private String name;
        @JsonProperty("currency")
        private Currency currency;
        @JsonProperty("id")
        private int id;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Currency
    {
        @JsonProperty("short_name")
        private String shortName;
        @JsonProperty("id")
        private int id;
    }
}
