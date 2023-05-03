package com.news.aggregator.search.dtos;

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
public class GuardianUKNewsResponseMaster {
    private int totalData;
    private int startIndex;
    private int pageSize;
    private int currentPage;
    private int pageCount;
    private String orderBy;
    private List<GuardianUKNewsResponseDto> guardianUKNewsResponseDtoList;
}
