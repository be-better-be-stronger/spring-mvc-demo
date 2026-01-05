package com.demo.web.dto;

import java.util.List;

public class PageResult<T> {
    private final List<T> items;
    private final int page;      // 1-based
    private final int size;
    private final long totalItems;

    public PageResult(List<T> items, int page, int size, long totalItems) {
        this.items = items;
        this.page = page;
        this.size = size;
        this.totalItems = totalItems;
    }

    public List<T> getItems() { return items; }
    public int getPage() { return page; }
    public int getSize() { return size; }
    public long getTotalItems() { return totalItems; }

    public long getTotalPages() {
        if (size <= 0) return 0;
        return (totalItems + size - 1) / size;
    }

    public int getOffset() { // 0-based offset
        return (page - 1) * size;
    }
}
