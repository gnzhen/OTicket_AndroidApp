package com.example.gd.oticket;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gd.oticket.myrequest.MyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by GD on 1/13/2018.
 */

public class HistoryFrag extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private MainActivity mainActivity;
    private RecyclerView recyclerView;
    private ArrayList<History> histories;
    private HistoryRecyclerAdapter adapter;
    private SwipeRefreshLayout swipeLayout;
    private MyRequest request;

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
        swipeLayout = view.findViewById(R.id.history_swipe_layout);

        //set up fragment
        mainActivity.setNavActiveItem(R.id.nav_history);
        mainActivity.showSearchBar(false);
        mainActivity.showBackButton(false);
        mainActivity.displayFab(false);
        mainActivity.setTitle("History");
        mainActivity.setContentText("");
        mainActivity.showSpinner(true);
        swipeLayout.setOnRefreshListener(this);

        //set up history list
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        request = new MyRequest(view.getContext());
        histories = new ArrayList<>();
        recyclerView.setAdapter(new EmptyAdapter());

        loadView(mainActivity.getUserId());
    }

    public void loadView(String id) {

        /* Get branches */
        request.getHistories(id, new MyRequest.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(jsonArray != null){
                    histories.clear();
                    for(int i = 0; i < jsonArray.length(); i++){
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            History history = new History(
                                    jsonObject.get("id").toString(),
                                    jsonObject.get("ticket_no").toString(),
                                    jsonObject.get("issue_time").toString(),
                                    jsonObject.get("wait_time").toString(),
                                    jsonObject.get("branch_name").toString(),
                                    jsonObject.get("service_name").toString(),
                                    jsonObject.get("status").toString()
                            );
                            histories.add(history);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                mainActivity.showSpinner(false);
                swipeLayout.setRefreshing(false);

                if(histories.size() > 0) {
                    adapter = new HistoryRecyclerAdapter(histories, getContext());
                    recyclerView.setAdapter(adapter);
                    mainActivity.showSpinner(false);
                }
                else{
                    mainActivity.setContentText("-  No history to display  -");
                    mainActivity.showSpinner(false);
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
        loadView(mainActivity.getUserId());
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
