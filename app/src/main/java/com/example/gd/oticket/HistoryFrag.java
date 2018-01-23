package com.example.gd.oticket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by GD on 1/13/2018.
 */

public class HistoryFrag extends Fragment {

    private MainActivity mainActivity;
    private RecyclerView recyclerView;
    private ArrayList<History> histories;
    private HistoryRecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_history, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        //initialize variable
        mainActivity = (MainActivity)this.getActivity();
        recyclerView = view.findViewById(R.id.history_recycler_view);

        //set up fragment
        mainActivity.setNavActiveItem(R.id.nav_history);
        mainActivity.showSearchBar(false);
        mainActivity.showBackButton(false);
        mainActivity.displayFab(false);
        mainActivity.setTitle("History");

        //set up history list
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        histories = new ArrayList<>();

        Bundle bundle = getArguments();
        histories = (ArrayList<History>) bundle.getSerializable("histories");

        if(histories != null) {
            adapter = new HistoryRecyclerAdapter(histories, getContext());
            recyclerView.setAdapter(adapter);
        }
        else{
            mainActivity.setContentText("-  No history to display  -");
        }
    }
}
