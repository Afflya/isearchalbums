package com.afflyas.isearchalbums.ui.album;

import com.afflyas.isearchalbums.repository.RepoResponse;
import com.afflyas.isearchalbums.repository.SongRepository;
import com.afflyas.isearchalbums.vo.Album;
import com.afflyas.isearchalbums.vo.Song;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/**
 *
 * ViewModel that stores {@link Album}
 * and api response for searching songs for this album as LiveData
 *
 */
public class AlbumViewModel extends ViewModel {

    /**
     * inject instance of {@link SongRepository}
     * and save link of its {@link SongRepository#searchResult}
     */
    @Inject
    public AlbumViewModel(SongRepository songRepository){
        this.songRepository = songRepository;
        this.searchResult = songRepository.searchResult;
    }

    private final SongRepository songRepository;

    public final LiveData<RepoResponse<List<Song>>> searchResult;

    private Album album = null;

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    /**
     * execute api request to load songs for album
     */
    public void loadSongs(){
        if(album != null){
            songRepository.searchForSongs(album.getCollectionId());
        }
    }
}
