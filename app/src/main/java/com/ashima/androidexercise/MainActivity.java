package com.ashima.androidexercise;

import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.ashima.androidexercise.Models.FeedModel;
import com.ashima.androidexercise.Utils.ApiInterface;
import com.ashima.androidexercise.Utils.RetrofitApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerViewFeeds;
    private static final String TAG="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        apiHit();
    }

    private void initView()
    {
        // inilitize all views by id
        toolbar=findViewById(R.id.toolbar);
        recyclerViewFeeds=findViewById(R.id.recyclerViewFeeds);
        //adding a ToolBar to ActionBar
        setSupportActionBar(toolbar);
    }

    private void apiHit()
    {
        ApiInterface apiService =
                RetrofitApiClient.getClient().create(ApiInterface.class);

        Call<FeedModel> call = apiService.getFeeds();
        call.enqueue(new Callback<FeedModel>() {
            @Override
            public void onResponse(Call<FeedModel>call, Response<FeedModel> response) {
                Log.d(TAG,"Success");
            }

            @Override
            public void onFailure(Call<FeedModel>call, Throwable t) {
                Log.d(TAG,"Fail");
            }
        });
    }
}
