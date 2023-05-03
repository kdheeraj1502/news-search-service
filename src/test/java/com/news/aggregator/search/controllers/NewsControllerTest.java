package com.news.aggregator.search.controllers;

import com.news.aggregator.search.services.NewsSearchService;
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
    void testGetAllProducts() throws Exception{
        mockMvc.perform(get("/v1/news/article/search/apple?page=2&per_page=10"))
                .andExpect(status().isOk());
    }
}
