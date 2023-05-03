package com.news.aggregator.search.services;

import com.news.aggregator.search.dtos.NewsData;
import com.news.aggregator.search.models.NewsResponseMaster;
import lombok.NonNull;

import java.util.List;
import java.util.Map;

public interface NewsSearchService {

    /**
     * Get all the products based on provided filters
     * @param page Current page count
     * @param perPage Total response per page
     * @return List of products matching the criteria
     */
    NewsResponseMaster searchNews(int page, int perPage, @NonNull String query) ;

}