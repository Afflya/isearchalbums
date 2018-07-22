package com.afflyas.isearchalbums.repository;

/**
 * Object that contains response of executing iTunes api requests
 *
 * @param <T> - {@link com.afflyas.isearchalbums.vo.Song} or {@link com.afflyas.isearchalbums.vo.Album}
 */
public class RepoResponse<T> {

    private final RepoResponseStatus status;
    private final T data;

    public RepoResponse(RepoResponseStatus status, T data) {
        this.status = status;
        this.data = data;
    }

    public RepoResponseStatus getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    /**
     * Create response instance with data returned by successful response(could be null)
     */
    public static <T> RepoResponse<T> success(T data){
        return new RepoResponse<>(RepoResponseStatus.SUCCESS, data);
    }

    /**
     * Create response instance that indicates that request was failed
     */
    public static <T>RepoResponse<T> error(){
        return new RepoResponse<>(RepoResponseStatus.ERROR, null);
    }

    /**
     * Create response instance that indicates that request execution started
     */
    public static <T>RepoResponse<T> loading(){
        return new RepoResponse<>(RepoResponseStatus.LOADING, null);
    }

}