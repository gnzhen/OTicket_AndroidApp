package com.example.gd.oticket;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GD on 1/13/2018.
 */

public class ServiceRecyclerAdapter extends RecyclerView.Adapter<ServiceRecyclerAdapter.ViewHolder> {

    private List<Service> serviceAL = new ArrayList<>();
    private Context context;
    private Branch branch;

    public ServiceRecyclerAdapter(List<Service> serviceAL, Context context){
        this.serviceAL = serviceAL;
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
        final Service service = serviceAL.get(position);

        holder.headTV.setText(service.getName());
        holder.bodyTV.setText("EWT: " + service.getWaitTimeString());
        holder.rowLL.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ((MainActivity) context).showIssueTicketDialog(service);
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
        return serviceAL.size();
    }

    public void setFilter(ArrayList<Service> newList){
        serviceAL = new ArrayList<>();
        serviceAL.addAll(newList);
        notifyDataSetChanged();
    }
}
