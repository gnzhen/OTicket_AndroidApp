package com.example.gd.oticket;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.Toast;

import com.example.gd.oticket.myrequest.MyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GD on 1/15/2018.
 */

public class ServiceFrag extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private ServiceRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private MainActivity mainActivity;
    private FloatingActionButton fab;
    private Dialog issueTicketDialog;
    private SearchView searchView;
    private TextView branchName, ewtLabel;
    private Toolbar toolbar;
    private ArrayList<Queue> queues;
    private ArrayList<BranchService> branchServices;
    private ArrayList<Service> services;
    private MyRequest request;
    private Branch branch;
    private SwipeRefreshLayout swipeLayout;

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
        swipeLayout = view.findViewById(R.id.service_swipe_layout);
        request = new MyRequest(view.getContext());

        //set up fragment
        mainActivity.setNavActiveItem(R.id.nav_take_a_ticket);
        mainActivity.displayFab(false);
        mainActivity.showSearchBar(true);
        mainActivity.showBackButton(true);
        mainActivity.setSearchLabelText("Choose a service");
        mainActivity.showSpinner(true);

        //set up branch list
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeLayout.setOnRefreshListener(this);

        branchServices = new ArrayList<>();
        queues = new ArrayList<>();
        services = new ArrayList<>();
        recyclerView.setAdapter(new EmptyAdapter());

        //get bundle
        Bundle bundle = getArguments();
        branch = (Branch)bundle.getSerializable("branch");

        if(branch != null) {
            loadView();
        }

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
                        String name = bs.getServiceName().toUpperCase();

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

    public void loadView() {
        /* Get branch services */
        request.getBranchServicesDetailsByBranchId(branch.getId(), new MyRequest.VolleyCallback() {

            @Override
            public void onSuccess(String result) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (jsonArray != null) {
                    branchServices.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            if (jsonObject != null) {

                                BranchService branchService = new BranchService(
                                        jsonObject.get("id").toString(),
                                        jsonObject.get("service_name").toString(),
                                        (Integer)jsonObject.get("wait_time"),
                                        (Integer)jsonObject.get("pending_ticket")
                                );
                                branchServices.add(branchService);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                mainActivity.showSpinner(false);
                swipeLayout.setRefreshing(false);

                if(branchServices.size() > 0) {
                    branchName.setText(branch.getName());
                    ewtLabel.setText("Estimated - Estimated Wait Time");
                    adapter = new ServiceRecyclerAdapter(branchServices, getContext());
                    recyclerView.setAdapter(adapter);
                }
                else{
                    mainActivity.setContentText("-  No service to display  -");
                }

            }
            @Override
            public void onFailure(String error) {
                Log.d("onFailure", error);
                recyclerView.setAdapter(new EmptyAdapter());
                mainActivity.showSpinner(false);
                mainActivity.showToast(error);
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

    @Override
    public void onRefresh() {
        loadView();
        new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
                //
            }

            public void onFinish() {
                swipeLayout.setRefreshing(false);
                mainActivity.showSpinner(false);
            }
        }.start();
    }
}
