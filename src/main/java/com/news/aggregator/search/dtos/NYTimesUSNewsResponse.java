package com.news.aggregator.search.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class NYTimesUSNewsResponse {

    @JsonProperty("status")
    private String status;

    @JsonProperty("copyright")
    private String copyright;

    @JsonProperty("response")
    private Map<String, Object> responses;
}