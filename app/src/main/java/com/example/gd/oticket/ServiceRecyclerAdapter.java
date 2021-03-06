package com.example.gd.oticket;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gd.oticket.myrequest.MyRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GD on 1/13/2018.
 */

public class ServiceRecyclerAdapter extends RecyclerView.Adapter<ServiceRecyclerAdapter.ViewHolder> {

    private ArrayList<BranchService> branchServiceAL = new ArrayList<>();
    private ArrayList<Queue> queues = new ArrayList<>();
    private ArrayList<Service> services = new ArrayList<>();
    private Context context;
    private MainActivity mainActivity;
    private Queue queue;
    private Service service;
    private String serviceName;
    private int waitTime;
    private int pplInQueue;

    public ServiceRecyclerAdapter(ArrayList<BranchService> branchServiceAL, Context context){
        this.branchServiceAL = branchServiceAL;
        this.context = context;
        this.mainActivity = (MainActivity)context;
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
        final BranchService branchService = branchServiceAL.get(position);

        holder.headTV.setText(branchService.getServiceName());
        holder.bodyTV.setText("Estimated: " + mainActivity.intTimeToString(branchService.getWaitTime()));
        holder.rowLL.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                mainActivity.showIssueTicketDialog(branchService);
            }
        });


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

    @Override
    public int getItemCount() {
        return branchServiceAL.size();
    }

    public void setFilter(ArrayList<BranchService> newList){
        branchServiceAL = new ArrayList<>();
        branchServiceAL.addAll(newList);
        notifyDataSetChanged();
    }
}
