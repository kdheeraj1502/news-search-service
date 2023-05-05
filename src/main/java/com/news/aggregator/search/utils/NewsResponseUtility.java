package com.news.aggregator.search.utils;

import com.news.aggregator.search.dtos.*;
import com.news.aggregator.search.models.Key;
import com.news.aggregator.search.models.NewsResponseMaster;
import com.news.aggregator.search.models.PageDetails;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.*;

@UtilityClass
public class NewsResponseUtility {
    private Map<Key, Map<PageDetails, List<Map<String, NewsData>>>> todaysNews = new HashMap<>();

    public static NewsResponse createResponse(GuardianUKNewsResponseMaster guardianUKNewsResponseMaster,
                                              List<NYTimesUSNewsresponseDto> nyTimesUSNewsresponseDtoList,
                                              String query){
        List<GuardianUKNewsResponseDto> guardianUKNewsResponseDtoList = guardianUKNewsResponseMaster.getGuardianUKNewsResponseDtoList();

        NewsResponse newsResponse = new NewsResponse();

        PageDetails pageDetails = new PageDetails();
        pageDetails.setPageCount(guardianUKNewsResponseMaster.getPageCount());
        pageDetails.setCurrentPage(guardianUKNewsResponseMaster.getCurrentPage());
        pageDetails.setPageSize(guardianUKNewsResponseMaster.getPageSize());
        pageDetails.setOrderBy(guardianUKNewsResponseMaster.getOrderBy());
        pageDetails.setStartIndex(guardianUKNewsResponseMaster.getStartIndex());
        pageDetails.setTotalData(guardianUKNewsResponseMaster.getTotalData());

        Map<PageDetails, List<Map<String, NewsData>>> news = new HashMap<>();
        List<Map<String, NewsData>> newsList = new ArrayList<>();
        for(GuardianUKNewsResponseDto guardianUKNewsResponseDto : guardianUKNewsResponseDtoList){
            NewsData data = new NewsData();
            Map<String, NewsData> map = new HashMap<>();
            data.setHeadlines(guardianUKNewsResponseDto.getId());
            data.setSection(guardianUKNewsResponseDto.getSectionId());
            data.setSubSection(guardianUKNewsResponseDto.getSectionName());
            data.setData(guardianUKNewsResponseDto.getWebTitle());
            data.setNewsType(guardianUKNewsResponseDto.getPillarName());
            data.setPublishDate(guardianUKNewsResponseDto.getWebPublicationDate());
            data.setSource("Guardian");
            map.put(guardianUKNewsResponseDto.getWebUrl(), data);
            newsList.add(map);
        }
        for(NYTimesUSNewsresponseDto nyTimesUSNewsresponseDto : nyTimesUSNewsresponseDtoList){
            NewsData data = new NewsData();
            Map<String, NewsData> map = new HashMap<>();

            data.setData(nyTimesUSNewsresponseDto.getAbstractData());
            data.setSnippet(nyTimesUSNewsresponseDto.getSnippet());
            data.setLeadParaGraph(nyTimesUSNewsresponseDto.getLead_paragraph());
            data.setNewsType(nyTimesUSNewsresponseDto.getNews_desk());
            data.setSection(nyTimesUSNewsresponseDto.getSection_name());
            data.setSubSection(nyTimesUSNewsresponseDto.getSubsection_name());
            data.setSource(nyTimesUSNewsresponseDto.getSource());
            data.setPublishDate(nyTimesUSNewsresponseDto.getPub_date());
            data.setHeadlines((String)  ((LinkedHashMap) nyTimesUSNewsresponseDto.getHeadline()).get("main"));
            map.put(nyTimesUSNewsresponseDto.getWebUrl(), data);
            newsList.add(map);
        }
        int pageSize = pageDetails.getPageSize() + nyTimesUSNewsresponseDtoList.size() ;
        pageDetails.setPageSize(pageSize);
        news.put(pageDetails, newsList);
        Key key = createCacheKey(query);
        todaysNews.put(key, news);
        newsResponse.setTodaysNews(todaysNews);

        return newsResponse;
    }

    public static NewsResponseMaster createResponse(
            List<Map<String, NewsData>> result,
            List<PageDetails> pageDetails,
            int page, int perPage,
            String query){
        NewsResponseMaster newsResponseMaster = new NewsResponseMaster();
        newsResponseMaster.setCreatedAt(new Date());
        newsResponseMaster.setTotalNoOfPages(pageDetails.get(0).getPageSize());
        newsResponseMaster.setUserSearchKeyword(query);
        newsResponseMaster.setDataCount(pageDetails.get(0).getTotalData());
        newsResponseMaster.setPreviousPageNo(page);
        newsResponseMaster.setNextPageNo(page + 1);
        Map<String, List<String>> urlMap = new HashMap<>();
        List<String> urls = new ArrayList<>();
        Map<String, List<String>> headlineMap = new HashMap<>();
        List<String> headlines = new ArrayList<>();
        for(Map<String, NewsData> res : result){
            Set<Map.Entry<String, NewsData>> dataSet = res.entrySet();
            Iterator<Map.Entry<String, NewsData>> it = dataSet.iterator();
            while(it.hasNext()){
                Map.Entry<String, NewsData> entry = it.next();
                urls.add(entry.getKey());
                headlines.add(entry.getValue().getHeadlines());
                newsResponseMaster.setCity(entry.getValue().getSection());
            }
        }
        urlMap.put("URLs", urls);
        headlineMap.put("Headlines", headlines);
        newsResponseMaster.setURL(urlMap);
        newsResponseMaster.setHeadline(headlineMap);
        newsResponseMaster.setUpdatedAt(new Date());
        return newsResponseMaster;
    }

    public static Key createCacheKey(String query){
        LocalDate today = LocalDate.now();
        return new Key(query, today);
    }
}
