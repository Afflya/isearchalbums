package com.afflyas.isearchalbums.di;

import com.afflyas.isearchalbums.core.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Module for main activity
 */
@Module
public abstract class MainActivityModule {
    /**
     * Generate sub component with fragment's modules in it
     */
    @ContributesAndroidInjector(modules = {FragmentBuildersModule.class})
    abstract MainActivity contributeMainActivity();
}

