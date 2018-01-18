package com.example.gd.oticket;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GD on 1/13/2018.
 */

public class ServiceRecyclerAdapter extends RecyclerView.Adapter<ServiceRecyclerAdapter.ViewHolder> {

    private ArrayList<BranchService> branchServiceAL = new ArrayList<>();
    private Context context;
    private Branch branch;
    private MainActivity mainActivity;

    public ServiceRecyclerAdapter(ArrayList<BranchService> branchServiceAL, Context context){
        this.branchServiceAL = branchServiceAL;
        this.context = context;
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
        BranchService branchService = branchServiceAL.get(position);

        String serviceName = mainActivity.getServiceByBranchServiceId(branchService.getId()).getName();
        final Queue queue = mainActivity.getShortestQueuesByBranchServiceId(branchService.getId());
        int waitTime = mainActivity.getWaitTimeByQueue(queue);

        holder.headTV.setText(serviceName);
        holder.bodyTV.setText("EWT: " + mainActivity.getWaitTimeString(waitTime));
        holder.rowLL.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ((MainActivity) context).showIssueTicketDialog(queue);
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
