package com.example.mvvmproj.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mvvmproj.di.DaggerApiComponent;
import com.example.mvvmproj.model.CountriesService;
import com.example.mvvmproj.model.CountryModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends ViewModel {

    public MutableLiveData<List<CountryModel>> countries = new MutableLiveData<>();
    public MutableLiveData<Boolean> countryLoadError = new MutableLiveData<>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

    @Inject
    public CountriesService countriesService;

    public ListViewModel(){
        DaggerApiComponent.create().inject(this);
    }

    private CompositeDisposable disposable = new CompositeDisposable();

    public void refresh() {
        fetchCountries();
    }

    private void fetchCountries() {
//
//        List<CountryModel> list = new ArrayList<>();
//        CountryModel countryModel1 = new CountryModel("India", "New Delhi", "");
//        CountryModel countryModel2 = new CountryModel("Pakistan", "Islamabad", "");
//        CountryModel countryModel3 = new CountryModel("Nepal", "Kathmandu", "");
//
//        list.add(countryModel1);
//        list.add(countryModel2);
//        list.add(countryModel3);
//
//        countries.setValue(list);
//        countryLoadError.setValue(false);
//        loading.setValue(false);

        loading.setValue(true);
        countryLoadError.setValue(false);
        disposable.add(countriesService.getCountries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        new DisposableSingleObserver<List<CountryModel>>() {

                                   @Override
                                   public void onSuccess(List<CountryModel> countryModels) {
                                        loading.setValue(false);
                                       countryLoadError.setValue(false);
                                        countries.setValue(countryModels);
                                   }

                                   @Override
                                   public void onError(Throwable e) {
                                       loading.setValue(false);
                                       countryLoadError.setValue(true);
                                       e.printStackTrace();
                                   }
                               }
                ));

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

}
