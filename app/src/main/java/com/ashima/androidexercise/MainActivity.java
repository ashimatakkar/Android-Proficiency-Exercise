package com.ashima.androidexercise;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Surface;
import android.view.WindowManager;

import com.ashima.androidexercise.Adapters.FeedAdapter;
import com.ashima.androidexercise.Models.FeedModel;
import com.ashima.androidexercise.Models.RowDataFeed;
import com.ashima.androidexercise.Utils.ApiInterface;
import com.ashima.androidexercise.Utils.RetrofitApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private RecyclerView recyclerViewFeeds;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FeedAdapter feedAdapter;
    private ArrayList<RowDataFeed> rowDataFeedList;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        getFeedFromServer();
    }

    private void initView() {
        // inilitize all views by id
        toolbar = findViewById(R.id.toolbar);
        recyclerViewFeeds = findViewById(R.id.recyclerViewFeeds);
        swipeRefreshLayout = findViewById(R.id.swipeLayout);
        //add a ToolBar to ActionBar
        setSupportActionBar(toolbar);

        // add listener to Swipe Refresh Layout
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    // this will never block UI as it is ASYCH Call means all operation in background and get callback when recived response : By default retrofit provide it
    private void getFeedFromServer() {
        lockScreenOrientation();
        swipeRefreshLayout.setRefreshing(true);
        // not using progress dialog as specification says not block UI while loading json
        //showProgressDialogWithMsg("Please Wait While Receiving Feeds....");
        ApiInterface apiService =
                RetrofitApiClient.getClient().create(ApiInterface.class);

        Call<FeedModel> call = apiService.getFeeds();
        call.enqueue(new Callback<FeedModel>() {
            @Override
            public void onResponse(Call<FeedModel> call, Response<FeedModel> response) {
                Log.d(TAG, "Success Feed GET");
                if (response != null && response.isSuccessful()) {

                    if (rowDataFeedList != null)
                        rowDataFeedList.clear();
                    else
                        rowDataFeedList = new ArrayList<>();

                    setToolbarTitle(response.body().getTitle());
                    rowDataFeedList.addAll(response.body().getRows());
                    setRecyclerViewAdapter();
                    swipeRefreshLayout.setRefreshing(false);
                    //dismissProgressDialogWithMsg();
                    unlockScreenOrientation();
                }
            }

            @Override
            public void onFailure(Call<FeedModel> call, Throwable t) {
                //dismissProgressDialogWithMsg();
                unlockScreenOrientation();
                swipeRefreshLayout.setRefreshing(false);
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

    private void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }


    /**
     * This method used to show progress dialog with given message.
     *
     * @param msg message to show on progress dialog
     */
    public void showProgressDialogWithMsg(String msg) {
        if (mProgressDialog == null)
            mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(msg);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    /**
     * Method Used to dismiss Progress bar
     */
    public void dismissProgressDialogWithMsg() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    private void lockScreenOrientation() {
        int orientation = getRequestedOrientation();
        int rotation = ((WindowManager) getSystemService(
                Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
        switch (rotation) {
            case Surface.ROTATION_0:
                orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                break;
            case Surface.ROTATION_90:
                orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                break;
            case Surface.ROTATION_180:
                orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                break;
            default:
                orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                break;
        }

        setRequestedOrientation(orientation);
    }

    private void unlockScreenOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }
    @Override
    public void onRefresh() {
        getFeedFromServer();
    }
}
