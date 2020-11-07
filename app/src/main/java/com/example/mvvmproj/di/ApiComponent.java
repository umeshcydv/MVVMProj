package com.example.mvvmproj.di;

import com.example.mvvmproj.model.CountriesService;
import com.example.mvvmproj.viewmodel.ListViewModel;

import dagger.Component;

@Component(modules = {ApiModule.class, ServiceModule.class})
public interface ApiComponent {

    void inject(CountriesService service);

    void inject(ListViewModel viewModel);
}
