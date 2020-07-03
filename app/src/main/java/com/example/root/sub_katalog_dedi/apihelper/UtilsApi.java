package com.example.root.sub_katalog_dedi.apihelper;

public class UtilsApi {
    public static final String BASE_URL_API = "https://api.themoviedb.org/3/";
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
