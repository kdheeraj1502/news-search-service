package com.news.aggregator.search.services.impl;

import com.news.aggregator.search.dtos.NYTimesUSNewsresponseDto;
import com.news.aggregator.search.services.News;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class NYTimesUSNews implements News {

    private BringNews bringNews;

    List<NYTimesUSNewsresponseDto> nyTimesUSNewsresponseDtoList;

    public NYTimesUSNews(BringNews bringNews){
        this.bringNews = bringNews;
    }

    @Override
    public  void execute(String query) {
        nyTimesUSNewsresponseDtoList = (List<NYTimesUSNewsresponseDto>) bringNews.nyTimesUSNews(query);
    }
}
