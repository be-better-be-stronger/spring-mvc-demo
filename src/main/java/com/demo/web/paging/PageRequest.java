package com.demo.web.paging;

import java.util.Set;

public final class PageRequest {
	private final int page; // 1-based
	private final int size;
	private final String sort; // field
	private final String dir; // asc/desc

	private PageRequest(int page, int size, String sort, String dir) {
		this.page = page;
		this.size = size;
		this.sort = sort;
		this.dir = dir;
	}
	
	public PageRequest withPage(int newPage) {
	    return new PageRequest(newPage, this.size, this.sort, this.dir);
	}


	public static PageRequest of(Integer page, Integer size, String sort, String dir, Set<String> allowedSort) {
		int p = (page == null || page < 1) ? 1 : page;

		int s = (size == null || size < 1) ? 10 : size;
		if (s > 100) s = 100; // production cap

		String sf = (sort == null || sort.isBlank()) ? "name" : sort.trim(); //sort field
		if (!allowedSort.contains(sf)) sf = "name";

		String d = (dir != null && dir.equalsIgnoreCase("asc")) ? "asc" : "desc"; //direction field

		return new PageRequest(p, s, sf, d);
	}

	public int page() {
		return page;
	}

	public int size() {
		return size;
	}

	public String sort() {
		return sort;
	}

	public String dir() {
		return dir;
	}

	public int offset() {
		return (page - 1) * size;
	}
}
