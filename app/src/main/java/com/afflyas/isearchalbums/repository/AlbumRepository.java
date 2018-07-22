package com.afflyas.isearchalbums.repository;


import android.util.Log;

import com.afflyas.isearchalbums.api.ITunesApiService;
import com.afflyas.isearchalbums.core.App;
import com.afflyas.isearchalbums.vo.Album;
import com.afflyas.isearchalbums.vo.ITunesResponse;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * Repository that handles {@link Album} objects.
 *
 * Request result to iTunes api stores in {@link AlbumRepository#searchResult}
 * that should be observed by {@link com.afflyas.isearchalbums.ui.search.SearchViewModel}
 *
 * Response contains {@link RepoResponseStatus} with current status
 * and {@link List<Album>} with the list of albums found
 *
 */
public class AlbumRepository{

    private final ITunesApiService iTunesApiService;

    @Inject
    public AlbumRepository(ITunesApiService iTunesApiService){
        this.iTunesApiService = iTunesApiService;
    }

    public MutableLiveData<RepoResponse<List<Album>>> searchResult = new MutableLiveData<>();

    /**
     * Execute GET request in the worker thread
     *
     * Also set {@link RepoResponse#loading()} to {@link AlbumRepository#searchResult}
     *
     * @param term - string to search for
     */
    public void searchForAlbums(String term){
        searchResult.postValue(RepoResponse.loading());

        iTunesApiService.getAlbumsByTerm(term).enqueue(responseCallback);
    }

    /**
     * Handle response result by posting new value to {@link AlbumRepository#searchResult}
     *
     * send {@link RepoResponse#error()} when request failed
     *
     * send {@link RepoResponse#success(Object)}} with sorted by name {@link Album} list
     *
     * send {@link RepoResponse#success(Object)}} with null when response has no items
     */
    private Callback<ITunesResponse<Album>> responseCallback = new Callback<ITunesResponse<Album>>() {
        @Override
        public void onResponse(Call<ITunesResponse<Album>> call, Response<ITunesResponse<Album>> response) {

            ITunesResponse<Album> responseBody = response.body();

            if(responseBody == null || responseBody.getResultCount() == 0){
                searchResult.postValue(RepoResponse.success(null));
            }else{
                //Sort albums by title
                Collections.sort(responseBody.getResults());
                searchResult.postValue(RepoResponse.success(responseBody.getResults()));
            }
        }

        @Override
        public void onFailure(Call<ITunesResponse<Album>> call, Throwable t) {
            Log.d(App.DEV_TAG, getClass().getSimpleName() + ": SEARCHING FAILURE");
            searchResult.postValue(RepoResponse.error());
        }
    };
}
