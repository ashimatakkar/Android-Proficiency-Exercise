package com.ashima.androidexercise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerViewFeeds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView()
    {
        // inilitize all views by id
        toolbar=findViewById(R.id.toolbar);
        recyclerViewFeeds=findViewById(R.id.recyclerViewFeeds);
        //adding a ToolBar to ActionBar
        setSupportActionBar(toolbar);
    }
}
