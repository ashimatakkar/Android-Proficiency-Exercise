package com.ashima.androidexercise.Utils;
/**
 * Interfaces which defines the possible HTTP operations
 *
 * @author Ashima
 */

import com.ashima.androidexercise.Models.FeedModel;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiInterface {
    //  Each Call can make a synchronous or asynchronous HTTP request to the remote webserver to get Feed.
    @GET("s/2iodh4vg0eortkl/facts.json")
    Call<FeedModel> getFeeds();
}