package com.afflyas.isearchalbums.repository;

import android.util.Log;

import com.afflyas.isearchalbums.api.ITunesApiService;
import com.afflyas.isearchalbums.core.App;
import com.afflyas.isearchalbums.vo.ITunesResponse;
import com.afflyas.isearchalbums.vo.Song;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * Repository that handles {@link Song} objects.
 *
 * Request result to iTunes api stores in {@link SongRepository#searchResult}
 * that should be observed by {@link com.afflyas.isearchalbums.ui.search.SearchViewModel}
 *
 * Response contains {@link RepoResponseStatus} with current status
 * and {@link List<Song>} with the list of songs found
 *
 */
public class SongRepository {

    private final ITunesApiService iTunesApiService;

    @Inject
    public SongRepository(ITunesApiService iTunesApiService){
        this.iTunesApiService = iTunesApiService;
    }

    public MutableLiveData<RepoResponse<List<Song>>> searchResult = new MutableLiveData<>();

    /**
     * Execute GET request in the worker thread
     *
     * Also set {@link RepoResponse#loading()} to {@link AlbumRepository#searchResult}
     *
     * @param albumId - {@link com.afflyas.isearchalbums.vo.Album#collectionId} of the album
     */
    public void searchForSongs(long albumId){

        searchResult.postValue(RepoResponse.loading());

        iTunesApiService.getSongsForAlbum(albumId).enqueue(responseCallback);
    }

    /**
     * Handle response result by posting new value to {@link SongRepository#searchResult}
     *
     * send {@link RepoResponse#error()} when request failed
     *
     * send {@link RepoResponse#success(Object)}} with {@link Song} list
     *
     * send {@link RepoResponse#success(Object)}} with null when response has no items
     */
    private Callback<ITunesResponse<Song>> responseCallback = new Callback<ITunesResponse<Song>>() {
        @Override
        public void onResponse(Call<ITunesResponse<Song>> call, Response<ITunesResponse<Song>> response) {

            ITunesResponse<Song> responseBody = response.body();

            if(responseBody == null || responseBody.getResultCount() <= 1){
                searchResult.postValue(RepoResponse.success(null));
            }else{
                //remove 1st element that contains with album's info
                responseBody.getResults().remove(0);
                searchResult.postValue(RepoResponse.success(responseBody.getResults()));
            }
        }

        @Override
        public void onFailure(Call<ITunesResponse<Song>> call, Throwable t) {
            searchResult.postValue(RepoResponse.error());
        }
    };

}
