package com.news.aggregator.search.services.impl;

import com.news.aggregator.search.services.News;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class NewsBroker {
    private List<News> newsList = new ArrayList<>();

    public void takeSearch(News order){
        newsList.add(order);
    }

    public void bringNews(String query){
        for (News news : newsList) {
            news.execute(query);
        }
        newsList.clear();
    }
}
