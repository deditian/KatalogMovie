package com.example.root.sub_katalog_dedi.apihelper;


import com.example.root.sub_katalog_dedi.model.ResponseNowPlaying;
import com.example.root.sub_katalog_dedi.model.ResponseSearch;
import com.example.root.sub_katalog_dedi.model.ResponseUpComing;


import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.Query;


public interface BaseApiService {

    @GET("movie/now_playing")
    Call<ResponseNowPlaying> getNowPlayingMovie(
            @Query("api_key") String apikey,
            @Query("language") String language
    );

    @GET("search/movie")
    Call<ResponseSearch> getSearchMovie(
            @Query("api_key") String apikey,
            @Query("query") String query,
            @Query("language") String language
    );

    @GET("movie/upcoming")
    Call<ResponseUpComing> getUpcomingMovie(
            @Query("api_key") String apikey,
            @Query("language") String language
    );


}
