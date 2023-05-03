package com.news.aggregator.search.utils;

import com.news.aggregator.search.dtos.*;
import com.news.aggregator.search.models.PageDetails;

import java.time.LocalDate;
import java.util.*;

public class NewsResponseUtility {

    public static NewsResponse createResponse(GuardianUKNewsResponseMaster guardianUKNewsResponseMaster,
                                              List<NYTimesUSNewsresponseDto> nyTimesUSNewsresponseDtoList){
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
        Map<LocalDate,Map<PageDetails, List<Map<String, NewsData>>>> todaysNews = new HashMap<>();
        LocalDate today = LocalDate.now();
        todaysNews.put(today, news);
        newsResponse.setTodaysNews(todaysNews);
        return newsResponse;
    }
}
