/*
 * Created by omrobbie.
 * Copyright (c) 2018. All rights reserved.
 * Last modified 11/11/17 3:14 PM.
 */

package com.example.root.sub_katalog_dedi.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;


import com.example.root.sub_katalog_dedi.BuildConfig;
import com.example.root.sub_katalog_dedi.R;
import com.example.root.sub_katalog_dedi.adapter.RecyclerAdapter;
import com.example.root.sub_katalog_dedi.apihelper.BaseApiService;
import com.example.root.sub_katalog_dedi.apihelper.UtilsApi;
import com.example.root.sub_katalog_dedi.model.ResponseSearch;
import com.example.root.sub_katalog_dedi.model.ResultsItem;
import com.example.root.sub_katalog_dedi.util.Language;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    public static final String MOVIE_TITLE = "movie_title";
    private static final String TAG ="SearchActivity" ;

    @BindView(R.id.rv_search)
    RecyclerView rv_search;
    private Context mContext;
    private RecyclerAdapter adapter;
    BaseApiService ApiService;
    private Call<ResponseSearch> apiCall;
    RecyclerAdapter recyclerAdapter;
    List<ResultsItem>  resultsItemList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ApiService = UtilsApi.getAPIService();
        ButterKnife.bind(this);

        setupList();

        String movie_title = getIntent().getStringExtra(MOVIE_TITLE);
        loadData(movie_title);
    }

    private void setupList() {
        adapter = new RecyclerAdapter(resultsItemList,this);
        rv_search.setLayoutManager(new LinearLayoutManager(this));
        rv_search.setAdapter(adapter);
    }

    private void loadData(String movie_title) {

       ApiService.getSearchMovie(BuildConfig.API_KEY,movie_title, Language.getCountry()).enqueue(new Callback<ResponseSearch>() {
           @Override
           public void onResponse(Call<ResponseSearch> call, Response<ResponseSearch> response) {
               Log.i(TAG, "onResponse: "+response.code());
               if (response.code() == 200){
                   List<ResultsItem> resultsItems = response.body().getResults();
                   for (int i = 0; i < resultsItems.size(); i++) {
                       Log.i(TAG, "onResponse: "+resultsItems.get(i).getTitle());
                   }
                   adapter.replaceAll(response.body().getResults());
               }
           }

           @Override
           public void onFailure(Call<ResponseSearch> call, Throwable t) {
               Log.i(TAG, "onFailure: "+ t.getMessage());
               Toast.makeText(mContext,"error",Toast.LENGTH_SHORT).show();
           }
       });
    }

}
