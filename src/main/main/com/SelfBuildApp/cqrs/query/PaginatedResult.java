package com.SelfBuildApp.cqrs.query;


import com.SelfBuildApp.ddd.Support.infrastructure.PropertyAccess;
import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("serial")
public class PaginatedResult<T> implements Serializable {

    @JsonView(PropertyAccess.Public.class)
    private final List<T> items;
    @JsonView(PropertyAccess.Public.class)
    private final int pageSize;
    @JsonView(PropertyAccess.Public.class)
    private final int pageNumber;
    @JsonView(PropertyAccess.Public.class)
    private final int pagesCount;
    @JsonView(PropertyAccess.Public.class)
    private final Long totalItemsCount;

    public PaginatedResult(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        items = Collections.emptyList();
        pagesCount = 0;
        totalItemsCount = Long.valueOf(0);
    }

    public PaginatedResult(List<T> items, int pageNumber, int pageSize, Long totalItemsCount) {
        this.items = items;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.pagesCount = countPages(pageSize, totalItemsCount);
        this.totalItemsCount = totalItemsCount;
    }

    private int countPages(int size, Long itemsCount) {
        return (int) Math.ceil((double) itemsCount / size);
    }

    public List<T> getItems() {
        return items;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public Long getTotalItemsCount() {
        return totalItemsCount;
    }
}
