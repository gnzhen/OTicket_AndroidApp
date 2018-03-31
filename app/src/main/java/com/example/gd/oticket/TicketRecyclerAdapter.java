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

public class TicketRecyclerAdapter extends RecyclerView.Adapter<TicketRecyclerAdapter.ViewHolder> {

    private List<Ticket> ticketAL = new ArrayList<>();
    private Context context;
    private MainActivity mainActivity;
    private View dot;

    public TicketRecyclerAdapter(List<Ticket> ticketAL, Context context){
        this.ticketAL = ticketAL;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ticket_card_view_layout, parent, false);

        dot = view.findViewById(R.id.ticket_card_view_dot);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mainActivity = (MainActivity)context;
        final Ticket ticket = ticketAL.get(position);

        mainActivity.setLayerType(dot);

        holder.ticketNoTV.setText(ticket.getTicketNo());
        holder.waitTimeTV.setText(mainActivity.intTimeToString(ticket.getWaitTime()));
        holder.branchTV.setText(ticket.getBranchName());
        holder.serviceTV.setText(ticket.getServiceName());
        holder.rowLL.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Fragment ticketDetailsFrag = new TicketDetailsFrag();

                Bundle bundle = new Bundle();
                bundle.putSerializable("ticketDetails", ticket.getId());

                mainActivity.displayFragment(ticketDetailsFrag, bundle, "TICKET_DETAILS");
            }
        });
    }

    @Override
    public int getItemCount() {
        return ticketAL.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView ticketNoTV, waitTimeTV, branchTV, serviceTV;
        LinearLayout rowLL;

        public ViewHolder(View itemView) {
            super(itemView);

            ticketNoTV = itemView.findViewById(R.id.ticket_card_view_ticket_number);
            waitTimeTV = itemView.findViewById(R.id.ticket_card_view_wait_time);
            branchTV = itemView.findViewById(R.id.ticket_card_view_branch);
            serviceTV = itemView.findViewById(R.id.ticket_card_view_service);
            rowLL = itemView.findViewById(R.id.take_card_view_linear_layout);
        }
    }
}
