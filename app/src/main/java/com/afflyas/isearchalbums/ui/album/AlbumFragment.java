package com.afflyas.isearchalbums.ui.album;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.afflyas.isearchalbums.R;
import com.afflyas.isearchalbums.api.ITunesApiService;
import com.afflyas.isearchalbums.core.App;
import com.afflyas.isearchalbums.core.MainActivity;
import com.afflyas.isearchalbums.databinding.FragmentAlbumBinding;
import com.afflyas.isearchalbums.di.ViewModelFactory;
import com.afflyas.isearchalbums.ui.common.RetryCallback;
import com.afflyas.isearchalbums.vo.Album;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.AndroidSupportInjection;

/**
 * Fragment to display general information about album and its list of songs
 */
public class AlbumFragment extends Fragment implements RetryCallback {

    private FragmentAlbumBinding fragmentBinding;

    @Inject
    MainActivity mainActivity;

    @Inject
    ITunesApiService iTunesApiService;

    /**
     * Custom factory to inject into ViewModel's
     */
    @Inject
    ViewModelFactory viewModelFactory;

    private AlbumViewModel mViewModel;

    private SongsAdapter songsAdapter;

    /**
     * Enable injections
     */
    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    /**
     * Create view with data binding and set {@link RetryCallback} to it
     *
     * set {@link Album} object passed as argument to binding
     *
     * Subscribe toolbar to display back arrow button
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Bundle args = getArguments();
        if(args == null) throw new IllegalArgumentException("arguments must contain Album object");
        Album album = AlbumFragmentArgs.fromBundle(args).getAlbum();

        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_album, container, false);
        fragmentBinding.setCallback(this);
        fragmentBinding.setAlbum(album);

        mainActivity.setSupportActionBar(fragmentBinding.toolbar);

        ActionBar actionBar = mainActivity.getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setHasOptionsMenu(true);

        return fragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(AlbumViewModel.class);
        subscribeUI();

    }

    /**
     * set adapter to recyclerView
     *
     * set {@link Album} object passed as argument to {@link AlbumViewModel}
     *
     * subscribe observer for {@link AlbumViewModel#searchResult}
     * to change data in the view's binding
     *
     * call to search request to load songs if it is not have been called before
     *
     */
    private void subscribeUI(){
        Log.d(App.DEV_TAG, getClass().getSimpleName() + ": subscribeUI()");
        songsAdapter = new SongsAdapter();
        fragmentBinding.recyclerView.setAdapter(songsAdapter);

        if(mViewModel.getAlbum() == null){
            Bundle args = getArguments();
            if(args == null) throw new IllegalArgumentException("arguments must contain Album object");
            Album album = AlbumFragmentArgs.fromBundle(args).getAlbum();
            mViewModel.setAlbum(album);
        }

        mViewModel.searchResult.observe(this, listResponse -> {
            Log.d(App.DEV_TAG, getClass().getSimpleName() + ": searchResult onChanged");

            fragmentBinding.setResponse(listResponse);
            songsAdapter.setSongsList(listResponse.getData());
        });

        if(mViewModel.searchResult.getValue() == null){
            mViewModel.loadSongs();
        }
    }

    /**
     * {@link RetryCallback} call to repeat search request
     *
     * Retry button displayed only when api request was failed
     */
    @Override
    public void retry() {
        mViewModel.loadSongs();
    }

    /**
     * Subscribe arrow back button in toolbar
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) mainActivity.onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
