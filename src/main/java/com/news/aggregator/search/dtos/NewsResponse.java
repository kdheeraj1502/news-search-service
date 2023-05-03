package com.news.aggregator.search.dtos;

import com.news.aggregator.search.models.PageDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponse {
    private Map<LocalDate,Map<PageDetails, List<Map<String, NewsData>>>> todaysNews;

}


