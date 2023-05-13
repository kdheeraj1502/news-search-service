package com.news.aggregator.search.controllers;

import com.news.aggregator.search.services.NewsSearchService;
import com.news.aggregator.search.utils.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NewsController.class)
public class NewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    NewsSearchService newsSearchService;

    @Test
    void testInjection(){
        assertNotNull(mockMvc);
    }

    @Test
    void pingTest() throws Exception{
        mockMvc.perform(get(Constants.Routes.NEWS_REQUEST + "/ping"))
                .andExpect(status().isOk());
    }

    @Test
    void searchNewsTest() throws Exception{
        mockMvc.perform(get(Constants.Routes.NEWS_REQUEST + Constants.Routes.ARTICLE_SEARCH + "/apple?page=2&per_page=10"))
                .andExpect(status().isOk());
    }

    @Test
    void searchNewsExceptionTest() throws Exception{
        mockMvc.perform(get(Constants.Routes.NEWS_REQUEST + Constants.Routes.ARTICLE_SEARCH + "/?page=2&per_page=10"))
                .andExpect(status().is4xxClientError());
    }
}
