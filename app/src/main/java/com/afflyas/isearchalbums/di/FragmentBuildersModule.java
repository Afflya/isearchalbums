package com.afflyas.isearchalbums.di;

import com.afflyas.isearchalbums.ui.album.AlbumFragment;
import com.afflyas.isearchalbums.ui.search.SearchFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Module for fragments
 */
@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract SearchFragment contributeSearchFragment();

    @ContributesAndroidInjector
    abstract AlbumFragment contributeAlbumFragment();

}
