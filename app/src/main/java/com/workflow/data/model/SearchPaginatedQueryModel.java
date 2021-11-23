package com.workflow.data.model;

/**
 * Created by Michael on 18/07/19.
 */

public class SearchPaginatedQueryModel {
    private String search;
    private Integer limit;
    private Integer page;

    public SearchPaginatedQueryModel() {
    }

    public SearchPaginatedQueryModel(String search, Integer limit, Integer page) {
        this.search = search;
        this.limit = limit;
        this.page = page;
    }

    public String getSearch() {
        return search;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
