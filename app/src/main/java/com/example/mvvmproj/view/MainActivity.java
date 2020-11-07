package com.example.mvvmproj.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mvvmproj.R;
import com.example.mvvmproj.viewmodel.ListViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.countryList)
    RecyclerView countryList;

    @BindView(R.id.errorText)
    TextView listError;

    @BindView(R.id.loading_view)
    ProgressBar loadingView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;

    private ListViewModel viewModel;
    private CountryListAdapter adapter = new CountryListAdapter(new ArrayList<>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        viewModel.refresh();

        countryList.setLayoutManager(new LinearLayoutManager(this));
        countryList.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(() -> {
            viewModel.refresh();
            refreshLayout.setRefreshing(false);
        });
        observerViewModel();
    }

    private void observerViewModel() {
        viewModel.countries.observe(this, countryModels -> {
            if(countryModels != null) {
                countryList.setVisibility(View.VISIBLE);
                adapter.updateCountries(countryModels);
            }
        });

        viewModel.countryLoadError.observe(this, isError -> {
            if(isError) {
                listError.setVisibility(View.VISIBLE);
            } else {
                listError.setVisibility(View.GONE);
            }
        });

        viewModel.loading.observe(this, isLoading -> {
            if(isLoading) {
                loadingView.setVisibility(View.VISIBLE);
            } else {
                loadingView.setVisibility(View.GONE);
            }
        });
    }


}