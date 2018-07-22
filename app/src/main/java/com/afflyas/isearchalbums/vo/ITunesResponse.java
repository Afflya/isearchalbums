package com.afflyas.isearchalbums.vo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *
 * Response data returned by {@link com.afflyas.isearchalbums.repository.RepoResponse#getData()}
 *
 * @param <T> {@link Song} or {@link Album}
 */
public class ITunesResponse<T> {

    public Long getResultCount() {
        return resultCount;
    }

    public List<T> getResults() {
        return results;
    }

    @SerializedName("resultCount")
    private Long resultCount;

    @SerializedName("results")
    private List<T> results = null;
}
