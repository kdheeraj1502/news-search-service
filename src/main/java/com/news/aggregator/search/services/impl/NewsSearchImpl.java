package com.news.aggregator.search.services.impl;

import com.news.aggregator.search.dtos.GuardianUKNewsResponseMaster;
import com.news.aggregator.search.dtos.NYTimesUSNewsresponseDto;
import com.news.aggregator.search.dtos.NewsData;
import com.news.aggregator.search.dtos.NewsResponse;
import com.news.aggregator.search.exceptions.RecordNotFoundException;
import com.news.aggregator.search.models.Key;
import com.news.aggregator.search.models.NewsResponseMaster;
import com.news.aggregator.search.models.PageDetails;
import com.news.aggregator.search.services.NewsSearchService;
import com.news.aggregator.search.utils.NewsResponseUtility;
import com.news.aggregator.search.utils.PaginationUtility;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NewsSearchImpl implements NewsSearchService {
    private final Logger LOG = LoggerFactory.getLogger(NewsSearchImpl.class);

    private static final String API_KEY = "RC0IVXfM38l8G0QRyd0CCbkEQQxOW8mr";
    private static final String A_API_KY = "4c494de1-e09d-45b9-914a-8cf86657c045";

    @Autowired
    private GuardianUKNewsResponseMaster guardianUKNewsResponseMaster;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BringNews bringNews;

    private Map<Key, Map<PageDetails, List<Map<String, NewsData>>>> todaysNews;


    @PostConstruct
    public void initialize() {
        this.todaysNews = new HashMap<>();
    }

    public NewsResponseMaster searchNews(int page, int perPage, String query) {
        try {
            GuardianUKNews guardianUKNews = new GuardianUKNews(bringNews);
            NYTimesUSNews nyTimesUSNews = new NYTimesUSNews(bringNews);
            NewsBroker newsBroker = new NewsBroker();
            long startTime = System.currentTimeMillis();
            List<NYTimesUSNewsresponseDto> nyTimesUSNewsresponseDtoList = null;
            GuardianUKNewsResponseMaster guardianUKNewsResponseMaster = null;
            NewsResponse newsResponse = null;
            Key cacheKey = NewsResponseUtility.createCacheKey(query);
            if (!this.todaysNews.containsKey(cacheKey)) {

                newsBroker.takeSearch(guardianUKNews);
                newsBroker.takeSearch(nyTimesUSNews);
                newsBroker.bringNews(query);
                nyTimesUSNewsresponseDtoList = nyTimesUSNews.getNyTimesUSNewsresponseDtoList();
                guardianUKNewsResponseMaster = guardianUKNews.getGuardianUKNewsResponseMaster();
                newsResponse = NewsResponseUtility.createResponse(guardianUKNewsResponseMaster, nyTimesUSNewsresponseDtoList, query);
                this.todaysNews = newsResponse.getTodaysNews();
                LOG.info("Data from NYTimes and Guardings");
            }

            Set<Map.Entry<PageDetails, List<Map<String, NewsData>>>> set = this.todaysNews.get(cacheKey).entrySet();
            Iterator<Map.Entry<PageDetails, List<Map<String, NewsData>>>> iterator = set.iterator();
            List<Map<String, NewsData>> source = new ArrayList<>();
            List<PageDetails> pageDetails = this.todaysNews.get(cacheKey).keySet().stream().collect(Collectors.toList());
            while (iterator.hasNext()) {
                Map.Entry<PageDetails, List<Map<String, NewsData>>> entry = iterator.next();
                source.addAll(entry.getValue());
            }
            List<Map<String, NewsData>> result = PaginationUtility.getPage(source, page, perPage);
            NewsResponseMaster newsResponseMaster = NewsResponseUtility.createResponse(result, pageDetails, page, perPage, query);
            long endTime = System.currentTimeMillis();
            long timeDifference = (endTime - startTime) / 1000;
            newsResponseMaster.setTimeTaken(timeDifference + " S");
            return newsResponseMaster;
        } catch (Exception exception) {
            LOG.error("News for :: [ " + query + " ] does no exist. Please check your search query");
            throw new RecordNotFoundException("News for :: [ " + query + " ] does no exist. Please check your search query");
        }
    }
}