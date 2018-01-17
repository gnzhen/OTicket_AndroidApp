package com.example.gd.oticket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by GD on 1/13/2018.
 */

public class TicketDetailsFrag extends Fragment {

    private MainActivity mainActivity;
    private TextView ticketNumber, waitTime, serveTime, servingNow, pplAhead, branch, service;
    private Ticket ticket;
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_ticket_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Initialize variables
        mainActivity = (MainActivity)this.getActivity();
        toolbar = mainActivity.getToolbar();
        ticketNumber = view.findViewById(R.id.ticket_details_ticket_number);
        pplAhead = view.findViewById(R.id.ticket_details_ppl_ahead);
        waitTime = view.findViewById(R.id.ticket_details_wait_time);

        //set up fragment
        mainActivity.setNavActiveItem(R.id.nav_my_ticket);
        mainActivity.displayFab(false);
        mainActivity.showSearchBar(false);


        //get bundle
        Bundle bundle = getArguments();
        ticket = (Ticket) bundle.getSerializable("ticket");
        ticketNumber.setText(ticket.getTicketNo());
        pplAhead.setText(Integer.toString(ticket.getPplAhead()));
        waitTime.setText(mainActivity.getWaitTimeString(ticket.getWaitTime()));
        branch.setText(mainActivity.getBranchByTicketId(ticket.getId()).getName());
        service.setText(mainActivity.getServiceByTicketId(ticket.getId()).getName());
    }
}
