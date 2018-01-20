package com.example.gd.oticket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by GD on 1/13/2018.
 */

public class TicketDetailsFrag extends Fragment {

    private MainActivity mainActivity;
    private TextView ticketNumber, waitTime, serveTime, servingNow, pplAhead, branch, service;
    private Button postpone, cancelTicket;
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
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mainActivity = (MainActivity)this.getActivity();
        toolbar = mainActivity.getToolbar();
        ticketNumber = view.findViewById(R.id.ticket_details_ticket_number);
        pplAhead = view.findViewById(R.id.ticket_details_ppl_ahead);
        branch = view.findViewById(R.id.ticket_details_branch);
        service = view.findViewById(R.id.ticket_details_service);
        waitTime = view.findViewById(R.id.ticket_details_wait_time);
        serveTime = view.findViewById(R.id.ticket_details_serve_time);
        servingNow = view.findViewById(R.id.ticket_details_serving_now);
        postpone = view.findViewById(R.id.ticket_details_postpone_btn);
        cancelTicket = view.findViewById(R.id.ticket_details_cancel_btn);

        //set up fragment
        mainActivity.setNavActiveItem(R.id.nav_my_ticket);
        mainActivity.displayFab(false);
        mainActivity.showSearchBar(false);

        //get bundle
        Bundle bundle = getArguments();
        ticket = (Ticket) bundle.getSerializable("ticket");
        ticketNumber.setText(ticket.getTicketNo());
        pplAhead.setText(Integer.toString(ticket.getPplAhead()));
        waitTime.setText(mainActivity.intTimeToString(ticket.getWaitTime()));
        serveTime.setText(mainActivity.getServeTimeStringByTicketId(ticket.getId()));
        servingNow.setText(mainActivity.getTicketNoServingNowByTicket(ticket));
        branch.setText(mainActivity.getBranchByTicketId(ticket.getId()).getName());
        service.setText(mainActivity.getServiceByTicketId(ticket.getId()).getName());

        postpone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.showPostponeDialog(ticket);
            }
        });

        cancelTicket.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mainActivity.showConfirmationDialog("cancelTicket");
            }
        });

    }
}
