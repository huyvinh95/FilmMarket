package com.vnh.filmmarket.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HUYVINH on 15-Feb-17.
 */

public class DiscoverResponse {
    @SerializedName("page")

    private Integer page;
    @SerializedName("results")

    private List<Discover> results = null;
    @SerializedName("total_results")

    private Integer totalResults;
    @SerializedName("total_pages")

    private Integer totalPages;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Discover> getResults() {
        return results;
    }

    public void setResults(List<Discover> results) {
        this.results = results;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

}


