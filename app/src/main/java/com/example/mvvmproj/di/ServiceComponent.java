package com.example.mvvmproj.di;

import com.example.mvvmproj.viewmodel.ListViewModel;

import dagger.Component;

//@Component(modules = {ServiceModule.class})
public interface ServiceComponent {
    void inject(ListViewModel viewModel);
}
