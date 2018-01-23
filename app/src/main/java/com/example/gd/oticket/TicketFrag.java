package com.example.gd.oticket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
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
        mainActivity = (MainActivity)this.getActivity();
        recyclerView = view.findViewById(R.id.ticket_recycler_view);
        toolbar = mainActivity.getToolbar();
        searchView = toolbar.findViewById(R.id.search_view);

        //set up fragment
        mainActivity.setNavActiveItem(R.id.nav_my_ticket);
        mainActivity.displayFab(true);
        mainActivity.showSearchBar(false);
        mainActivity.showBackButton(false);
        mainActivity.setTitle("OTicket");

        //set up ticket list
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        tickets = new ArrayList<>();

        Bundle bundle = getArguments();
        tickets = (ArrayList<Ticket>) bundle.getSerializable("tickets");

        if(tickets != null){
            Collections.sort((List<Ticket>) tickets);
            adapter = new TicketRecyclerAdapter(tickets, getContext());
            recyclerView.setAdapter(adapter);
        }
        else{
            mainActivity.setContentText("-  No ticket to display  -");
        }

    }
}
