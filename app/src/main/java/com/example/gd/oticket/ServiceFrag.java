package com.example.gd.oticket;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GD on 1/15/2018.
 */

public class ServiceFrag extends Fragment {

    private ServiceRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private MainActivity mainActivity;
    private FloatingActionButton fab;
    private Dialog issueTicketDialog;
    private SearchView searchView;
    private TextView branchName, ewtLabel;
    private Toolbar toolbar;
    private ArrayList<BranchService> branchServices;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_service, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        //Initialize variables
        mainActivity = (MainActivity)this.getActivity();
        issueTicketDialog = new Dialog(getContext());
        branchName = view.findViewById(R.id.frag_service_branch);
        recyclerView = view.findViewById(R.id.service_recycler_view);
        toolbar = mainActivity.getToolbar();
        searchView = toolbar.findViewById(R.id.search_view);
        ewtLabel = view.findViewById(R.id.service_estimated_wait_time_label);

        //set up fragment
        mainActivity.setNavActiveItem(R.id.nav_take_a_ticket);
        mainActivity.displayFab(false);
        mainActivity.showSearchBar(true);
        mainActivity.showBackButton(true);

        //set up branch list
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        branchServices = new ArrayList<>();

        //get bundle
        Bundle bundle = getArguments();
        branchServices = (ArrayList<BranchService>) bundle.getSerializable("branchServices");

        if(branchServices != null) {
            Branch selectedBranch = mainActivity.getBranchById(branchServices.get(0).getBranchId());
            branchName.setText(selectedBranch.getName());
            ewtLabel.setText("EWT - Estimated Wait Time");

            adapter = new ServiceRecyclerAdapter(branchServices, getContext());
            recyclerView.setAdapter(adapter);
        }
        else{
            mainActivity.setContentText("-  No service to display  -");
        }

        mainActivity.setSearchLabelText("Choose a service");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchText) {

                ArrayList<BranchService> filteredList = new ArrayList<>();

                if(branchServices != null) {
                    for (BranchService bs : branchServices) {
                        Service service = mainActivity.getServiceById(bs.getServiceId());
                        String name = service.getName().toUpperCase();

                        if (name.contains(searchText.toUpperCase())) {
                            filteredList.add(bs);
                        }
                    }

                    adapter.setFilter(filteredList);
                }
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
