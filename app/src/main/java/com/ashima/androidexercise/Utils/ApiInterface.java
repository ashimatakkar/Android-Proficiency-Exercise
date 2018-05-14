package com.ashima.androidexercise.Utils;


import com.ashima.androidexercise.Models.FeedModel;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiInterface {
    @GET("s/2iodh4vg0eortkl/facts.json")
    Call<FeedModel> getFeeds();
}