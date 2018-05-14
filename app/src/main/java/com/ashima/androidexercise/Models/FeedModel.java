package com.ashima.androidexercise.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FeedModel {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("rows")
    @Expose
    private ArrayList<RowDataFeed> rows;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<RowDataFeed> getRows() {
        return rows;
    }

    public void setRows(ArrayList<RowDataFeed> rows) {
        this.rows = rows;
    }
}