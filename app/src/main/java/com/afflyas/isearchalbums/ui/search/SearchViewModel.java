package com.afflyas.isearchalbums.ui.search;

import com.afflyas.isearchalbums.repository.AlbumRepository;
import com.afflyas.isearchalbums.repository.RepoResponse;
import com.afflyas.isearchalbums.vo.Album;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 *
 * ViewModel that stores String to search for and
 * and api response for searching albums as LiveData
 *
 */
public class SearchViewModel extends ViewModel {

    public final MutableLiveData<String> term = new MutableLiveData<>();

    private final AlbumRepository albumRepository;

    public final LiveData<RepoResponse<List<Album>>> searchResult;

    /**
     * inject instance of {@link AlbumRepository}
     * and save link of its {@link AlbumRepository#searchResult}
     */
    @Inject
    public SearchViewModel(AlbumRepository albumRepository){
        this.albumRepository = albumRepository;
        this.searchResult = albumRepository.searchResult;
    }

    /**
     * set termString and execute search request
     * @param termString - string to search for
     */
    public void search(String termString){
        if(termString != null && !termString.isEmpty()){
            term.setValue(termString);
            albumRepository.searchForAlbums(termString);
        }
    }

    /**
     * execute request with the same termString
     */
    public void refresh(){
        search(term.getValue());
    }

}


