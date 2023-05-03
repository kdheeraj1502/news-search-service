package com.news.aggregator.search.models;

import com.news.aggregator.search.dtos.GuardianUKNewsResponseDto;
import com.news.aggregator.search.dtos.NYTimesUSNewsresponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class NYTimesUSNewsResponseMaster {
    private int totalData;
    private int startIndex;
    private int pageSize;
    private int currentPage;
    private int pageCount;
    private String orderBy;
    private List<NYTimesUSNewsresponseDto> nyTimesUSNewsresponseDtoList;
}
