package com.demo.web.paging;

import java.util.List;

public final class PageResponse<T> {
    private final List<T> items;
    private final int page;        // 1-based
    private final int size;
    private final long totalItems;
    private final long totalPages;

    public PageResponse(List<T> items, int page, int size, long totalItems) {
        this.items = items;
        this.page = page;
        this.size = size;
        this.totalItems = totalItems;
        this.totalPages = (totalItems + size - 1) / size;
    }

    public List<T> getItems() { return items; }
    public int getPage() { return page; }
    public int getSize() { return size; }
    public long getTotalItems() { return totalItems; }
    public long getTotalPages() { return totalPages; }

    public boolean isHasPrev() { return page > 1; }
    public boolean isHasNext() { return page < totalPages; }
}
