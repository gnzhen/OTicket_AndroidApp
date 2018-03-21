package com.example.gd.oticket;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gd.oticket.myrequest.MyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by GD on 1/13/2018.
 */

public class TicketFrag extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private MainActivity mainActivity;
    private TicketRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<Ticket> tickets;

    private ArrayList<Queue> queues;
    private ArrayList<BranchService> branchServices;
    private ArrayList<Service> services;
    private ArrayList<Service> branches;
    private SearchView searchView;
    private Ticket ticket;
    private Toolbar toolbar;
    private MyRequest request;
    private SwipeRefreshLayout swipeLayout;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String id;

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
        swipeLayout = view.findViewById(R.id.ticket_swipe_layout);

        //set up fragment
        mainActivity.setNavActiveItem(R.id.nav_my_ticket);
        mainActivity.displayFab(true);
        mainActivity.showSearchBar(false);
        mainActivity.showBackButton(false);
        mainActivity.setTitle("OTicket");
        mainActivity.showSpinner(true);

        //set up ticket list
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeLayout.setOnRefreshListener(this);

        pref = getActivity().getSharedPreferences("auth", getActivity().MODE_PRIVATE);
        editor = pref.edit();
        id = pref.getString("id", null);
        request = new MyRequest(view.getContext());
        tickets = new ArrayList<>();
        recyclerView.setAdapter(new EmptyAdapter());

        loadView();
    }

    public void loadView() {

        /* Get Tickets */
        request.getUserCurrentTickets(id, new MyRequest.VolleyCallback() {
            private String error;

            @Override
            public void onSuccess(String result) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (jsonArray != null) {
                    tickets.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            SimpleDateFormat format = new SimpleDateFormat("d/MM/yyyy h:mm a", Locale.ENGLISH);
                            Date issueTime = null;
                            String ticketServingNow = "-";
                            String disposedTime = "-";

                            if(!jsonObject.get("ticket_serving_now").toString().equals("null")) {
                                ticketServingNow = jsonObject.get("ticket_serving_now").toString();
                            }
                            if(!jsonObject.get("disposed_time").toString().equals("null")) {
                                disposedTime = jsonObject.get("disposed_time").toString();
                            }
//                                issueTime = format.parse(jsonObject.get("issue_time").toString());
//                                disposedTime = format.parse(jsonObject.get("disposed_time").toString());

                            Ticket ticket = new Ticket(
                                    jsonObject.get("id").toString(),
                                    jsonObject.get("ticket_no").toString(),
                                    jsonObject.get("issue_time").toString(),
                                    jsonObject.get("queue_id").toString(),
                                    (Integer) jsonObject.get("wait_time"),
                                    (Integer) jsonObject.get("ppl_ahead"),
                                    jsonObject.get("mobile_user_id").toString(),
                                    (Integer) jsonObject.get("postponed"),
                                    jsonObject.get("status").toString(),
                                    disposedTime,
                                    jsonObject.get("branch_name").toString(),
                                    jsonObject.get("service_name").toString(),
                                    jsonObject.get("serve_time").toString(),
                                    ticketServingNow
                            );
                            tickets.add(ticket);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                mainActivity.showSpinner(false);
                swipeLayout.setRefreshing(false);

                if(tickets.size() > 0) {
                    adapter = new TicketRecyclerAdapter(tickets, getContext());
                    recyclerView.setAdapter(adapter);
                }
                else{
                    recyclerView.setAdapter(new EmptyAdapter());
                    mainActivity.setContentText("-  No ticket to display  -");
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
