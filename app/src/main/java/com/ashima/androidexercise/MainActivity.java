package com.ashima.androidexercise;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.ashima.androidexercise.Adapters.FeedAdapter;
import com.ashima.androidexercise.Models.FeedModel;
import com.ashima.androidexercise.Models.RowDataFeed;
import com.ashima.androidexercise.Utils.ApiInterface;
import com.ashima.androidexercise.Utils.RetrofitApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private RecyclerView recyclerViewFeeds;
    private FeedAdapter feedAdapter;
    private ArrayList<RowDataFeed> rowDataFeedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        apiHit();
    }

    private void initView() {
        // inilitize all views by id
        toolbar = findViewById(R.id.toolbar);
        recyclerViewFeeds = findViewById(R.id.recyclerViewFeeds);
        //adding a ToolBar to ActionBar
        setSupportActionBar(toolbar);
    }

    private void apiHit() {
        ApiInterface apiService =
                RetrofitApiClient.getClient().create(ApiInterface.class);

        Call<FeedModel> call = apiService.getFeeds();
        call.enqueue(new Callback<FeedModel>() {
            @Override
            public void onResponse(Call<FeedModel> call, Response<FeedModel> response) {
                Log.d(TAG, "Success");
                if (response != null && response.isSuccessful()) {

                    if(rowDataFeedList!=null)
                        rowDataFeedList.clear();
                    else
                        rowDataFeedList=new ArrayList<>();

                    setToolbarTitle(response.body().getTitle());
                    rowDataFeedList.addAll(response.body().getRows());
                    setRecyclerViewAdapter();

                }
            }

            @Override
            public void onFailure(Call<FeedModel> call, Throwable t) {
                Log.d(TAG, "Fail");
            }
        });
    }

    private void setRecyclerViewAdapter() {
        if (feedAdapter == null) {
            recyclerViewFeeds.setLayoutManager(new LinearLayoutManager(this));
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewFeeds.getContext(),
                    LinearLayoutManager.VERTICAL);
            recyclerViewFeeds.addItemDecoration(dividerItemDecoration);
            feedAdapter = new FeedAdapter(this, rowDataFeedList);
            recyclerViewFeeds.setAdapter(feedAdapter);
        } else {
            feedAdapter.notifyDataSetChanged();
        }
    }

    private void setToolbarTitle(String title)
    {
        toolbar.setTitle(title);
    }
}
