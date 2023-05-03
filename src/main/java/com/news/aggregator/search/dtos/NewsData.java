package com.news.aggregator.search.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class NewsData {
    private String data;
    private String headlines;
    private String urls;
    private String source;
    private String section;
    private String subSection;
    private String newsType;
    private String publishDate;
    private String snippet;
    private String leadParaGraph;
}