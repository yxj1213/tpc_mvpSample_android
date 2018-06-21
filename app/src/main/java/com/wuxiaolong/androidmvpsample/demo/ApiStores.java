package com.wuxiaolong.androidmvpsample.demo;


import com.wuxiaolong.androidmvpsample.demo.module.MainModel;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiStores {
    //baseUrl
    String API_SERVER_URL = "http://www.weather.com.cn/";

    //加载天气
    @GET("adat/sk/{cityId}.html")
//    Call<MainModel> loadDataByRetrofit(@Path("cityId") String cityId);
    Call<MainModel> loadDataByRetrofit(@Body MainModel cityId);

    //加载天气
    @GET("adat/sk/{cityId}.html")
    Observable<MainModel> loadDataByRetrofitRxJava(@Path("cityId") String cityId);
}
