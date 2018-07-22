package com.afflyas.isearchalbums.di;

import com.afflyas.isearchalbums.api.ITunesApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Module to provide single Retrofit instance and ViewModel's modules
 */
@Module(includes = {ViewModelModule.class})
public class AppModule {

    /**
     *
     * Provide single retrofit instance of {@link ITunesApiService}
     *
     * @return instance of {@link ITunesApiService}
     */
    @Singleton
    @Provides
    public ITunesApiService provideITunesApiService(){
        return new Retrofit.Builder()
                .baseUrl("https://itunes.apple.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ITunesApiService.class);
    }
}
