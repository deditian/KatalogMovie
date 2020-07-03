package com.example.root.sub_katalog_dedi.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.root.sub_katalog_dedi.BuildConfig;
import com.example.root.sub_katalog_dedi.R;
import com.example.root.sub_katalog_dedi.adapter.GridAdapter;
import com.example.root.sub_katalog_dedi.adapter.RecyclerAdapter;
import com.example.root.sub_katalog_dedi.apihelper.BaseApiService;
import com.example.root.sub_katalog_dedi.apihelper.UtilsApi;
import com.example.root.sub_katalog_dedi.model.ResponseUpComing;
import com.example.root.sub_katalog_dedi.model.ResultsItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpComingFragment extends Fragment {

    BaseApiService ApiService;
        String TAG = "MainActivity";
    Context mContext;
    @BindView(R.id.rvresult)
    RecyclerView rvresult;
    private RecyclerView.LayoutManager layoutManager;
    GridAdapter gridAdapter;
    List<ResultsItem> resultsItemList;
    public UpComingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_up_coming, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ApiService = UtilsApi.getAPIService();
        setHasOptionsMenu(true);
        rvresult.setHasFixedSize(true);
        resultsItemList = new ArrayList<>();

        rvresult.setItemAnimator(new DefaultItemAnimator());
        getActivity().setTitle(getResources().getString(R.string.nowplaying));
        rvresult.setLayoutManager(new GridLayoutManager(mContext, 2));
        gridAdapter = new GridAdapter(resultsItemList,mContext);
        rvresult.setAdapter(gridAdapter);
        rvresult.setItemAnimator(new DefaultItemAnimator());
        gridAdapter.notifyDataSetChanged();
        getActivity().setTitle(getResources().getString(R.string.upcoming));
        if(savedInstanceState!=null){
            resultsItemList = savedInstanceState.getParcelableArrayList("upcoming_movie");
            Log.i(TAG, "onCreateView: lisnya "+resultsItemList);
            gridAdapter = new GridAdapter(resultsItemList,mContext);
            gridAdapter.setResultItem(resultsItemList);
            rvresult.setAdapter(gridAdapter);
        }else {
            getSearch();
        }
    }

    private void getSearch() {
       ApiService.getUpcomingMovie(BuildConfig.API_KEY,"en-US").enqueue(new Callback<ResponseUpComing>() {
           @Override
           public void onResponse(Call<ResponseUpComing> call, Response<ResponseUpComing> response) {
               Log.i(TAG, "onResponse: "+response.code());
               if (response.code() == 200){
                   resultsItemList = response.body().getResults();
                   gridAdapter = new GridAdapter(resultsItemList,mContext);
                   rvresult.setAdapter(gridAdapter);
               }
           }

           @Override
           public void onFailure(Call<ResponseUpComing> call, Throwable t) {
               Log.i(TAG, "onFailure: "+ t.getMessage());
               Toast.makeText(mContext,"internet tidak aktif",Toast.LENGTH_SHORT).show();
           }
       });


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("upcoming_movie", (ArrayList<? extends Parcelable>) resultsItemList);
        //    outState.putParcelableArrayList("now_movie", new ArrayList<>(resultsItemList));
        Log.i(TAG, "onSaveInstanceState: "+resultsItemList);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null){
            List<ResultsItem> list = savedInstanceState.getParcelableArrayList("upcoming_movie");
            gridAdapter = new GridAdapter(list,mContext);
            gridAdapter.setResultItem(list);
            rvresult.setAdapter(gridAdapter);
            Log.i(TAG, "onActivityCreated: "+list);
        }else
        {
            Log.i(TAG, "onActivityCreated: "+null);
        }
    }




}
