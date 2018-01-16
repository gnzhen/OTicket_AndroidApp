package com.example.gd.oticket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by GD on 1/13/2018.
 */

public class TicketDetailsFrag extends Fragment {

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

        //Initialize variables
        mainActivity = (MainActivity)this.getActivity();
        toolbar = ((MainActivity)this.getActivity()).getToolbar();

        //set up fragment
        mainActivity.setNavActiveItem(R.id.nav_my_ticket);
        mainActivity.setSearchLabelText("Ticket Details");
        mainActivity.displayFab(false);
        mainActivity.showSearchBar(false);
    }
}
