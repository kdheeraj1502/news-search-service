package com.news.aggregator.search.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class NYTimesUSNewsresponseDto {

    private String abstractData;

    private String webUrl;

    private String snippet;

    private String lead_paragraph;

    private String source;

    private Object headline;

    private String pub_date;
    private String news_desk;
    private String section_name;
    private String subsection_name;


}
