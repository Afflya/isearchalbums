package com.afflyas.isearchalbums.api;

import com.afflyas.isearchalbums.vo.Album;
import com.afflyas.isearchalbums.vo.ITunesResponse;
import com.afflyas.isearchalbums.vo.Song;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ITunesApiService {

    /**
     * search for albums by title
     *
     * @param term - string you want to search for
     * @return {@link ITunesResponse} that contains List of {@link Album's} and their count
     */
    @GET("search?entity=album&attribute=albumTerm")
    Call<ITunesResponse<Album>> getAlbumsByTerm(@Query("term") @NonNull String term);


    /**
     * get list of song for album by its id
     *
     * @param albumId - {@link Album#collectionId} of the album
     * @return {@link ITunesResponse} that contains List of {@link Song's} and their count (1st element of the list always returns album's info :\)
     */
    @GET("lookup?entity=song&attribute=songTerm")
    Call<ITunesResponse<Song>> getSongsForAlbum(@Query("id") long albumId);

}