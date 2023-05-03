package com.news.aggregator.search.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    @UtilityClass
    public static class NewsURLs {
        public static final String NY_TIMES_US_URL = "https://api.nytimes.com/svc/search/v2/articlesearch.json?q=";
        public static final String GUARDIAN_UK_URL = "https://content.guardianapis.com/search?q=";

    }



    @UtilityClass
    public static class UrlParameters {
        // Pagination
        public static final String PAGE = "page";
        public static final String PER_PAGE = "per_page";
    }

    /**
     * Utility Class for routes constants
     */
    @UtilityClass
    public static class Routes {
        // Base Paths
        public static final String NEWS_REQUEST = "/v1/news";

        // Resources
        public static final String ARTICLE_SEARCH = "/article/search";
    }

}
