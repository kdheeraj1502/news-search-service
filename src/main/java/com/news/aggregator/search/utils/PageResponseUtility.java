package com.news.aggregator.search.utils;

import com.news.aggregator.search.dtos.PageResponse;
import lombok.experimental.UtilityClass;
import org.springframework.lang.NonNull;

import java.util.List;

@UtilityClass
public class PageResponseUtility {
    public static <T, K> PageResponse<T> createPageResponse(@NonNull List<K> resultPage, int page, int perPage) {
        PageResponse<T> pageResponse = new PageResponse<>();
        pageResponse.setPage(page);
        pageResponse.setPerPage(perPage);
        pageResponse.setTotal((long)resultPage.size());
        pageResponse.setNextPage(Math.min(page + 1, Math.max(resultPage.size() - 1, 0)));
        return pageResponse;
    }

}
