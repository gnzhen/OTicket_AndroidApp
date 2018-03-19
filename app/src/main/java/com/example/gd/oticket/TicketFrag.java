package com.example.gd.oticket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private List<Ticket> tickets;
    private SearchView searchView;
    private Ticket ticket;
    private Toolbar toolbar;
    private MyRequest request;
    private SwipeRefreshLayout swipeLayout;

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

        request = new MyRequest(view.getContext());
        tickets = new ArrayList<>();

        loadView();
    }

    public void loadView() {
        /* Get branches */
        request.getUserCurrentTickets("1", new MyRequest.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(jsonArray != null){
                    tickets.clear();
                    for(int i = 0; i < jsonArray.length(); i++){
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            SimpleDateFormat format = new SimpleDateFormat("d/MM/yyyy h:mm a", Locale.ENGLISH);
                            Date issueTime = null;
                            Date disposedTime = null;
                            try {
                                issueTime = format.parse(jsonObject.get("issue_time").toString());
                                disposedTime = format.parse(jsonObject.get("disposed_time").toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Ticket ticket = new Ticket(
                                    (Integer)jsonObject.get("id"),
                                    jsonObject.get("ticket_no").toString(),
                                    issueTime,
                                    new Long((Integer)jsonObject.get("queue_id")),
                                    (Integer)jsonObject.get("wait_time"),
                                    (Integer)jsonObject.get("ppl_ahead"),
                                    jsonObject.get("mobile_user_id").toString(),
                                    (Integer)jsonObject.get("postponed"),
                                    jsonObject.get("status").toString(),
                                    disposedTime
                            );
                            tickets.add(ticket);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if(tickets.size() > 0) {
                    Collections.sort(tickets);
                    adapter = new TicketRecyclerAdapter(tickets, getContext());
                    recyclerView.setAdapter(adapter);
                    mainActivity.showSpinner(false);
                }
                else{
                    recyclerView.setAdapter(new EmptyAdapter());
                    mainActivity.showSpinner(false);
                    mainActivity.setContentText("-  No ticket to display  -");
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        loadView();
        swipeLayout.setRefreshing(false);
    }
}
