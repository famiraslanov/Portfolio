package com.tests.helpers.platform.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleApiModel
{
    @JsonProperty("results")
    private List<Result> results;


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result {
        @JsonProperty("post_id")
        private int postId;

        @JsonProperty("ID")
        private int id;

        @JsonProperty("terms")
        private Terms terms;

        @JsonProperty("acf")
        private ACF acf;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Terms {
        @JsonProperty("navigation")
        private List<Navigation> navigation;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Navigation {
        @JsonProperty("term_id")
        private int termId;
        private String slug;
        private String name;
        private int parent;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ACF {
        @JsonProperty("related_articles")
        private List<RelatedArticle> relatedArticles;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RelatedArticle {
        @JsonProperty("ID")
        private int id;

        @JsonProperty("post_date")
        private String postDate;

        @JsonProperty("post_modified")
        private String postModified;

        @JsonProperty("post_title")
        private String postTitle;
    }

}
