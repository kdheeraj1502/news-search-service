package com.news.aggregator.search.utils;

import java.util.*;

public class PaginationUtility {

    public static <T> List<T> getPage(List<T> sourceList, int page, int pageSize) {
        if(pageSize <= 0 || page <= 0) {
            throw new IllegalArgumentException("invalid page size: " + pageSize);
        }

        int fromIndex = (page - 1) * pageSize;
        if(sourceList == null || sourceList.size() < fromIndex){
            return Collections.emptyList();
        }
        return sourceList.subList(fromIndex, Math.min(fromIndex + pageSize, sourceList.size()));
    }

    public static <T> void doPaginated(Collection<T> fullList, Integer pageSize, Page<T> pageInterface) {

        final List<T> list = new ArrayList<T>(fullList);
        if (pageSize == null || pageSize <= 0 || pageSize > list.size()){
            pageSize = list.size();
        }

        final int numPages = (int) Math.ceil((double)list.size() / (double)pageSize);
        for (int pageNum = 0; pageNum < numPages;){
            final List<T> page = list.subList(pageNum * pageSize, Math.min(++pageNum * pageSize, list.size()));
            pageInterface.run(page);
        }
    }

    public interface Page<T> {
        void run(List<T> item);
    }
}
