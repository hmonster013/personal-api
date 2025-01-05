package com.de013.model;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.de013.dto.FilterVO;

public class Paging {
    public static final int PAGE = 1;
    public static final int SIZE = 10;

    public static final String SORT = "id";
    public static final String DESC = Direction.DESC.name();

    private int page = PAGE;
    private int size = SIZE;
    private long totalRows = -1;
    private int totalPages = -1;

    public Paging() {

    }

    public Paging (int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        if (size >= 1) {
            this.size = size;
        }
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        if (page >= 1) {
            this.page = page;
        }
    }

    public void setTotalRows(long totalRows) {
		this.totalRows = totalRows;
	}

	public long getTotalRows() {
		return totalRows;
	}

	public int getTotalPages() {
		return totalPages > 0 ? totalPages : 1;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

    public Pageable getPageRequest(FilterVO request) {
        setPage(request.getPage());
        setSize(request.getSize());
        Pageable pageRequest = PageRequest.of(this.page - 1, this.size);
		return pageRequest;
	}
}
