package com.ashima.androidexercise.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashima.androidexercise.Models.RowDataFeed;
import com.ashima.androidexercise.R;

import java.util.ArrayList;


public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private ArrayList<RowDataFeed> rowDataFeedList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public FeedAdapter(Context context, ArrayList<RowDataFeed> data) {
        this.mInflater = LayoutInflater.from(context);
        this.rowDataFeedList = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_feed, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final RowDataFeed rowDataFeed = rowDataFeedList.get(position);
        holder.tvTitle.setText(rowDataFeed.getTitle());
        holder.tvDesc.setText(rowDataFeed.getDescription());

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return rowDataFeedList.size();
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(RowDataFeed tblSfFormModel, int clickType);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc;
        ImageView ivThumbnail;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDescription);
            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
        }

    }
}