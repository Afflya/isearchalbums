package com.afflyas.isearchalbums.di;

import com.afflyas.isearchalbums.ui.album.AlbumViewModel;
import com.afflyas.isearchalbums.ui.search.SearchViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 *
 * Module to bind ViewModel's
 *
 */
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel.class)
    abstract ViewModel searchViewModel(SearchViewModel searchViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AlbumViewModel.class)
    abstract ViewModel albumViewModel(AlbumViewModel albumViewModel);

}
