package com.example.mvvmproj.di;

import com.example.mvvmproj.model.CountriesService;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    @Provides
    public CountriesService provideCountriesService() {
        return CountriesService.getInstance();
    }
}
