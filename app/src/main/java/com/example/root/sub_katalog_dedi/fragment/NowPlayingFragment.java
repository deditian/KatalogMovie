package com.example.root.sub_katalog_dedi.fragment;


import android.content.Context;
import android.os.Bundle;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.root.sub_katalog_dedi.BuildConfig;
import com.example.root.sub_katalog_dedi.R;
import com.example.root.sub_katalog_dedi.adapter.RecyclerAdapter;
import com.example.root.sub_katalog_dedi.apihelper.BaseApiService;
import com.example.root.sub_katalog_dedi.apihelper.UtilsApi;
import com.example.root.sub_katalog_dedi.model.ResponseNowPlaying;
import com.example.root.sub_katalog_dedi.model.ResponseUpComing;
import com.example.root.sub_katalog_dedi.model.ResultsItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NowPlayingFragment extends Fragment {
    BaseApiService ApiService;
    String TAG = "MainActivity";
    Context mContext;
    @BindView(R.id.rvresult)
    RecyclerView rvresult;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter recyclerAdapter;
    List<ResultsItem> resultsItemList;
    public NowPlayingFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ApiService = UtilsApi.getAPIService();
        setHasOptionsMenu(true);
        rvresult.setHasFixedSize(true);
        resultsItemList = new ArrayList<>();


        layoutManager = new LinearLayoutManager(mContext);
        rvresult.setLayoutManager(layoutManager);
        rvresult.setAdapter(recyclerAdapter);
        rvresult.setItemAnimator(new DefaultItemAnimator());
        getActivity().setTitle(getResources().getString(R.string.nowplaying));



        if(savedInstanceState!=null){
            resultsItemList = savedInstanceState.getParcelableArrayList("now_movie");
            Log.i(TAG, "onCreateView: lisnya "+resultsItemList);
            recyclerAdapter = new RecyclerAdapter(resultsItemList,mContext);
            recyclerAdapter.setResultItem(resultsItemList);
            rvresult.setAdapter(recyclerAdapter);
        }
        else{
            getSearch();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void getSearch() {
        ApiService.getNowPlayingMovie(BuildConfig.API_KEY,"en-US").enqueue(new Callback<ResponseNowPlaying>() {
            @Override
            public void onResponse(Call<ResponseNowPlaying> call, Response<ResponseNowPlaying> response) {
                Log.i(TAG, "onResponse: "+response.code());
                if (response.code() == 200){
                    resultsItemList = response.body().getResults();
                    recyclerAdapter = new RecyclerAdapter(resultsItemList,mContext);
                    rvresult.setAdapter(recyclerAdapter);
                    for (int i = 0; i < resultsItemList.size(); i++) {
                        Log.i(TAG, "onResponse: "+resultsItemList.get(i).getTitle());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseNowPlaying> call, Throwable t) {
                Log.i(TAG, "onFailure: "+ t.getMessage());
                Toast.makeText(mContext,"internet tidak aktif",Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null){
            List<ResultsItem> list = savedInstanceState.getParcelableArrayList("now_movie");
            recyclerAdapter = new RecyclerAdapter(list,mContext);
            recyclerAdapter.setResultItem(list);
            rvresult.setAdapter(recyclerAdapter);
            Log.i(TAG, "onActivityCreated: "+list);
        }else
        {
            Log.i(TAG, "onActivityCreated: "+null);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("now_movie", (ArrayList<? extends Parcelable>) resultsItemList);
        Log.i(TAG, "onSaveInstanceState: "+resultsItemList);
    }

}
