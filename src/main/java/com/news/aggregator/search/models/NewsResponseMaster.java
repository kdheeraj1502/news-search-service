package com.news.aggregator.search.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponseMaster extends BaseAuditModel {

    private Map<String, List<String>> URL;
    private Map<String, List<String>> headline;
    private int totalNoOfPages;
    private String userSearchKeyword;
    private String city;
    private int dataCount;
    private int previousPageNo;
    private int nextPageNo;
    private long timeTaken;

}

