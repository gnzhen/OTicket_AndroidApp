package com.example.gd.oticket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GD on 1/13/2018.
 */

public class BranchFrag extends Fragment {

    private BranchRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private MainActivity mainActivity;
    private ArrayList<Branch> branches;
    private Branch branch;
    private SearchView searchView;
    private Toolbar toolbar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_branch, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        //Initialize variables
        mainActivity = (MainActivity)this.getActivity();
        recyclerView = view.findViewById(R.id.branch_recycler_view);
        toolbar = mainActivity.getToolbar();
        searchView = toolbar.findViewById(R.id.search_view);

        //set up fragment
        mainActivity.setNavActiveItem(R.id.nav_take_a_ticket);
        mainActivity.setSearchLabelText("Choose a branch");
        mainActivity.displayFab(false);
        mainActivity.showSearchBar(true);

        //set up branch list
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        branches = new ArrayList<>();

        Bundle bundle = getArguments();
        branches = (ArrayList<Branch>) bundle.getSerializable("branches");

        adapter = new BranchRecyclerAdapter(branches, getContext());
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchText) {

                ArrayList<Branch> filteredList = new ArrayList<>();

                for(Branch branch: branches){
                    String name = branch.getName().toUpperCase();
                    if(name.contains(searchText.toUpperCase())){
                        filteredList.add(branch);
                    }
                }

                adapter.setFilter(filteredList);

                return false;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);

    }
}
