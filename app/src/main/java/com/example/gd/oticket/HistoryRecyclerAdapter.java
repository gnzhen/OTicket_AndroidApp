package com.example.gd.oticket;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.ViewHolder> {

    private List<History> historyAL = new ArrayList<>();
    private Context context;
    private MainActivity mainActivity;
    private View dot1, dot2;

    public HistoryRecyclerAdapter(List<History> historyAL, Context context){
        this.historyAL = historyAL;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_row_layout, parent, false);

        dot1 = view.findViewById(R.id.history_row_dot1);
        dot2 = view.findViewById(R.id.history_row_dot2);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mainActivity = (MainActivity)context;
        final History history = historyAL.get(position);

        mainActivity.setLayerType(dot1);
        mainActivity.setLayerType(dot2);

        Ticket ticket = mainActivity.getTicketById(history.getTicketId());
        Branch branch = mainActivity.getBranchByTicketId(history.getTicketId());
        Service service = mainActivity.getServiceByTicketId(history.getTicketId());
        int waitTime = mainActivity.calWaitTimeInt(history.getServeTime(), history.getDoneTime());

        holder.branchTV.setText(branch.getName());
        holder.serviceTV.setText(service.getName());
        holder.waitTimeTV.setText(mainActivity.intTimeToString(waitTime));
//        holder.dateTimeTV.setText(mainActivity.getDateTimeStringByUnix(ticket.getIssueTime()));
        holder.rowLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //implement later
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyAL.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView waitTimeTV, branchTV, serviceTV, dateTimeTV;
        LinearLayout rowLL;

        public ViewHolder(View itemView) {
            super(itemView);

            dateTimeTV = itemView.findViewById(R.id.history_row_issue_date_time);
            branchTV = itemView.findViewById(R.id.history_row_branch);
            serviceTV = itemView.findViewById(R.id.history_row_service);
            waitTimeTV = itemView.findViewById(R.id.history_row_wait_time);
            rowLL = itemView.findViewById(R.id.recycler_row_linear_layout);
        }
    }
}
