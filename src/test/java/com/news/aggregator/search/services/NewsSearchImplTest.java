package com.news.aggregator.search.services;

import com.news.aggregator.search.dtos.GuardianUKNewsResponseMaster;
import com.news.aggregator.search.dtos.NYTimesUSNewsresponseDto;
import com.news.aggregator.search.dtos.NewsData;
import com.news.aggregator.search.dtos.NewsResponse;
import com.news.aggregator.search.models.Key;
import com.news.aggregator.search.models.NewsResponseMaster;
import com.news.aggregator.search.models.PageDetails;
import com.news.aggregator.search.services.impl.*;
import com.news.aggregator.search.utils.NewsResponseUtility;
import com.news.aggregator.search.utils.PaginationUtility;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NewsSearchImplTest {

    @Test
    public void searchNewsTest() {
        final int page = 0;
        final int perPage = 10;
        final String query = "apple";

        GuardianUKNewsResponseMaster guardianUKNewsResponseMaster = Mockito.spy(GuardianUKNewsResponseMaster.class);
        RestTemplate restTemplate = new RestTemplate();
        BringNews bringNews = new BringNews();
        NewsBroker newsBroker = Mockito.mock(NewsBroker.class);
        Map<Key, Map<PageDetails, List<Map<String, NewsData>>>> todaysNews = Mockito.mock(Map.class);
        GuardianUKNews guardianUKNews = Mockito.mock(GuardianUKNews.class);
        NYTimesUSNews nyTimesUSNews = Mockito.mock(NYTimesUSNews.class);
        NewsResponseMaster newsResponseMaster = Mockito.mock(NewsResponseMaster.class);

        NewsSearchService newsSearchService = new NewsSearchImpl(
                guardianUKNewsResponseMaster,
                restTemplate,
                bringNews,
                todaysNews,
                newsBroker,
                guardianUKNews,
                nyTimesUSNews
        );
        List<Map<String, NewsData>> result = Mockito.mock(List.class);
        NewsResponse newsResponse = Mockito.spy(NewsResponse.class);
        newsResponse.setTodaysNews(todaysNews);
        List<NYTimesUSNewsresponseDto> nyTimesList = new ArrayList<>();
        List<NYTimesUSNewsresponseDto> nyTimesUSNewsresponseDtoList = Mockito.spy(nyTimesList);
        Map<PageDetails, List<Map<String, NewsData>>> data = Mockito.mock(Map.class);
        Set<Map.Entry<PageDetails, List<Map<String, NewsData>>>> set = new HashSet<>();
        Mockito.when(data.entrySet()).thenReturn(set);
        Mockito.when(todaysNews.get(Mockito.any())).thenReturn(data);

        Mockito.when(nyTimesUSNews.getNyTimesUSNewsresponseDtoList()).thenReturn(nyTimesUSNewsresponseDtoList);
        Mockito.when(guardianUKNews.getGuardianUKNewsResponseMaster()).thenReturn(guardianUKNewsResponseMaster);
        Mockito.mockStatic(NewsResponseUtility.class);
        Mockito.mockStatic(PaginationUtility.class);
        Mockito.when(PaginationUtility.getPage(Mockito.anyList(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(Collections.singletonList(result));
        Mockito.when(NewsResponseUtility.createResponse(Mockito.anyList(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString()))
                .thenReturn(newsResponseMaster);
        Mockito.when(NewsResponseUtility.createResponse(guardianUKNewsResponseMaster, nyTimesUSNewsresponseDtoList, query))
                .thenReturn(newsResponse);
        Mockito.doNothing().when(newsBroker).bringNews(query);

        assertEquals(newsResponseMaster, newsSearchService.searchNews(page, perPage, query));
    }
}
