package com.news.aggregator.search.models;

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
public class PageDetails {
    private int totalData;
    private int startIndex;
    private int pageSize;
    private int currentPage;
    private int pageCount;
    private String orderBy;
}
