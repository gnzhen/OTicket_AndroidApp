package com.example.gd.oticket;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gd.oticket.myrequest.MyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GD on 1/13/2018.
 */

public class BranchRecyclerAdapter extends RecyclerView.Adapter<BranchRecyclerAdapter.ViewHolder> {

    private ArrayList<Branch> branchAL = new ArrayList<>();
    private ArrayList<BranchService> branchServices = new ArrayList<>();
    private ArrayList<Queue> queues = new ArrayList<>();
    private Context context;
    private MainActivity mainActivity;
    private MyRequest request;

    public BranchRecyclerAdapter(ArrayList<Branch> branchAL, Context context){
        this.branchAL = branchAL;
        this.context = context;
        request = new MyRequest(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mainActivity = (MainActivity)context;
        final Branch branch = branchAL.get(position);

        holder.headTV.setText(branch.getName());
        holder.bodyTV.setText(branch.getDesc());
        holder.rowLL.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                final Fragment serviceFrag = new ServiceFrag();

                Bundle bundle = new Bundle();
                bundle.putSerializable("branch", branch);
                serviceFrag.setArguments(bundle);

                mainActivity.displayFragment(serviceFrag, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return branchAL.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView headTV;
        TextView bodyTV;
        LinearLayout rowLL;

        public ViewHolder(View itemView) {
            super(itemView);

            headTV = itemView.findViewById(R.id.recycler_row_head);
            bodyTV = itemView.findViewById(R.id.recycler_row_body);
            rowLL = itemView.findViewById(R.id.recycler_row_linear_layout);
        }
    }

    public void setFilter(ArrayList<Branch> newList){
        branchAL = new ArrayList<>();
        branchAL.addAll(newList);
        notifyDataSetChanged();
    }
}
