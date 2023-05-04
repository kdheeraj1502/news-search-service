package com.news.aggregator.search.services.impl;

import com.news.aggregator.search.dtos.GuardianUKNewsResponseMaster;
import com.news.aggregator.search.services.News;
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
public class GuardianUKNews implements News {

    private BringNews bringNews;

    private GuardianUKNewsResponseMaster guardianUKNewsResponseMaster;

    public GuardianUKNews(BringNews bringNews){
        this.bringNews = bringNews;
    }

    @Override
    public void execute(String query) {
        guardianUKNewsResponseMaster = (GuardianUKNewsResponseMaster) bringNews.guardianUKNews(query);
    }
}
