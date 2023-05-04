package com.news.aggregator.search.services;

import org.springframework.stereotype.Service;

@Service
public interface News {
    void execute(String query);
}
