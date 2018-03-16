package com.example.gd.oticket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.gd.oticket.myrequest.MyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private MyRequest request;

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
        mainActivity.showBackButton(false);

        //set up branch list
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        request = new MyRequest(view.getContext());
        branches = new ArrayList<>();

        /* Get branches */
        request.getBranches(new MyRequest.VolleyCallback(){

            @Override
            public void onSuccess(String result) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(jsonArray != null){
                    branches.clear();
                    for(int i = 0; i < jsonArray.length(); i++){
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Branch branch = new Branch(
                                    jsonObject.get("id").toString(),
                                    jsonObject.get("code").toString(),
                                    jsonObject.get("name").toString(),
                                    jsonObject.get("desc").toString()
                            );
                            branches.add(branch);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if(branches != null) {
                    adapter = new BranchRecyclerAdapter(branches, getContext());
                    recyclerView.setAdapter(adapter);
                }
                else{
                    mainActivity.setContentText("-  No branch to display  -");
                }
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchText) {

                ArrayList<Branch> filteredList = new ArrayList<>();

                if(branches != null) {
                    for (Branch branch : branches) {
                        String name = branch.getName().toUpperCase();
                        if (name.contains(searchText.toUpperCase())) {
                            filteredList.add(branch);
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
