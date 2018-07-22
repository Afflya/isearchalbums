package com.afflyas.isearchalbums.repository;

/**
 * Status of a data from repository that is provided to the UI.
 *
 * Created by the Repository classes where they return
 * `LiveData<RepoResponse<T>>` to pass back the data to the UI with its status.
 */
public enum RepoResponseStatus {
    SUCCESS,
    ERROR,
    LOADING
}