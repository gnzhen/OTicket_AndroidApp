package com.example.gd.oticket;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by GD on 3/19/2018.
 */

public class EmptyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
