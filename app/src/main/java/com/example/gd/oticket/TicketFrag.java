package com.example.gd.oticket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GD on 1/13/2018.
 */

public class TicketFrag extends Fragment {

    private MainActivity mainActivity;
    private TicketRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private List<Ticket> tickets;
    private SearchView searchView;
    private Ticket ticket;
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_ticket, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        //Initialize variables
        getView().setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mainActivity = (MainActivity)this.getActivity();
        recyclerView = getView().findViewById(R.id.ticket_recycler_view);
        toolbar = ((MainActivity)this.getActivity()).getToolbar();
        searchView = toolbar.findViewById(R.id.search_view);

        //set up fragment
        mainActivity.setNavActiveItem(R.id.nav_my_ticket);
        mainActivity.setSearchLabelText("Your Ticket");
        mainActivity.displayFab(true);
        mainActivity.showSearchBar(false);

        //set up ticket list
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        //hardcode branch data
        tickets = new ArrayList<>();
        ticket = null;

        for(int i = 0; i < 2; i++){
            String queueNo = "000" + Integer.toString(i);
            Branch branch = new Branch("branch1", "Kepong");
            Service service = new Service("service1", "Pick Up", 60);
            int waitTime = 3665;

            ticket = new Ticket(queueNo, branch, service, waitTime);
            tickets.add(ticket);
        }

        adapter = new TicketRecyclerAdapter(tickets, getContext());
        recyclerView.setAdapter(adapter);
    }
}
