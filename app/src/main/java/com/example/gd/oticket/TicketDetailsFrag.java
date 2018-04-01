package com.example.gd.oticket;

import android.os.Bundle;
import android.os.Handler;
import android.os.CountDownTimer;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.gd.oticket.myrequest.MyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

/**
 * Created by GD on 1/13/2018.
 */

public class TicketDetailsFrag extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private MainActivity mainActivity;
    private TextView ticketNumber, waitTime, serveTime, servingNow, pplAhead, branch, service;
    private Button postpone, cancelTicket;
    private Ticket ticket;
    private Toolbar toolbar;
    private View dot1, dot2;
    private SwipeRefreshLayout swipeLayout;
    private MyRequest request;
    private String id;
    private ArrayList<Integer> postponeTimes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_ticket_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Initialize variables
        dot1 = view.findViewById(R.id.ticket_details_dot1);
        dot2 = view.findViewById(R.id.ticket_details_dot2);
        mainActivity = (MainActivity)this.getActivity();
        toolbar = mainActivity.getToolbar();
        ticketNumber = view.findViewById(R.id.ticket_details_ticket_number);
        pplAhead = view.findViewById(R.id.ticket_details_ppl_ahead);
        branch = view.findViewById(R.id.ticket_details_branch);
        service = view.findViewById(R.id.ticket_details_service);
        waitTime = view.findViewById(R.id.ticket_details_wait_time);
        serveTime = view.findViewById(R.id.ticket_details_serve_time);
        servingNow = view.findViewById(R.id.ticket_details_serving_now);
        postpone = view.findViewById(R.id.ticket_details_postpone_btn);
        cancelTicket = view.findViewById(R.id.ticket_details_cancel_btn);
        swipeLayout = view.findViewById(R.id.ticket_details_swipe_layout);

        //set up fragment
        mainActivity.setNavActiveItem(R.id.nav_my_ticket);
        mainActivity.displayFab(false);
        mainActivity.showSearchBar(false);
        mainActivity.showBackButton(true);
        mainActivity.setTitle("Ticket Details");
        mainActivity.setLayerType(dot1);
        mainActivity.setLayerType(dot2);
        swipeLayout.setOnRefreshListener(this);
        request = new MyRequest(view.getContext());
        postponeTimes = new ArrayList<>();

        mainActivity.showSpinnerWithOverlay(true);

        //get bundle
        Bundle bundle = getArguments();
        id = bundle.getSerializable("ticketDetails").toString();

        loadView();

        postpone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mainActivity.showSpinnerWithOverlay(true);
                postpone(id);
            }
        });

        cancelTicket.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mainActivity.showConfirmationDialog("cancelTicket", id);
            }
        });
    }

    // Create the Handler object (on the main thread by default)
    Handler handler = new Handler(Looper.getMainLooper());
    // Define the code block to be executed
    final Runnable runnableCode = new Runnable() {
        @Override
        public void run() {

            loadView(); // Volley Request

            // Repeat this the same runnable code block again another 2 seconds
            handler.postDelayed(runnableCode, 1000);
        }
    };

    public void postpone(String ticketId){

        request.getPostponeDetails(ticketId, new MyRequest.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {

                    JSONObject jsonObject = new JSONObject(result);

                    if (jsonObject.has("fail")) {

                        mainActivity.showToast(jsonObject.get("fail").toString());
                        mainActivity.showSpinnerWithOverlay(false);
                    } else {

                        JSONArray jsonArray = jsonObject.getJSONArray("postpone_times");

                        for(int i = 0; i < jsonArray.length(); i++) {
                            postponeTimes.add((Integer)jsonArray.get(i));
                        }

                        mainActivity.showPostponeDialog(ticket, postponeTimes);
                    }

                    swipeLayout.setRefreshing(false);
                    mainActivity.showSpinnerWithOverlay(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                    swipeLayout.setRefreshing(false);
                    mainActivity.showSpinnerWithOverlay(false);
                }
            }

            @Override
            public void onFailure(String error) {
                Log.d("onFailure", error);
                swipeLayout.setRefreshing(false);
                mainActivity.showToast(error);
            }
        });
    }

    public void loadView() {

        /* Get Tickets */
        request.getTicketDetails(id, new MyRequest.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);

                    if (jsonObject.has("fail")) {
                        mainActivity.showSpinnerWithOverlay(false);
                        mainActivity.onBackPressed();
                        mainActivity.showToast(jsonObject.get("fail").toString());
                    } else {
                        String ticketServingNow = "-";
                        String disposedTime = "-";

                        if (!jsonObject.get("ticket_serving_now").toString().equals("null")) {
                            ticketServingNow = jsonObject.get("ticket_serving_now").toString();
                        }
                        if (!jsonObject.get("disposed_time").toString().equals("null")) {
                            disposedTime = jsonObject.get("disposed_time").toString();
                        }
                        ticket = new Ticket(
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
                        swipeLayout.setRefreshing(false);
                        mainActivity.showSpinnerWithOverlay(false);

                        //Set Layout Text
                        ticketNumber.setText(ticket.getTicketNo());
                        pplAhead.setText(Integer.toString(ticket.getPplAhead()));
                        waitTime.setText(mainActivity.intTimeToString(ticket.getWaitTime()));
                        serveTime.setText(ticket.getServeTime());
                        servingNow.setText(ticket.getTicketServingNow());
                        branch.setText(ticket.getBranchName());
                        service.setText(ticket.getServiceName());

////                        Refresh fragment component
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("ticketDetails", ticket);

//                        mainActivity.refreshFragment("TICKET_DETAILS", bundle);
                    }

                } catch(JSONException e){
                    e.printStackTrace();
                    mainActivity.showSpinnerWithOverlay(false);
                    swipeLayout.setRefreshing(false);
                }
            }

                @Override
            public void onFailure(String error) {
                Log.d("onFailure", error);
                mainActivity.showSpinnerWithOverlay(false);
                swipeLayout.setRefreshing(false);
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
                mainActivity.showSpinnerWithOverlay(false);
            }
        }.start();
    }

    @Override
    public void onResume(){
        super.onResume();
        // Start the initial runnable task by posting through the handler
        handler.post(runnableCode);
    }

    @Override
    public void onPause(){
        super.onPause();

        handler.removeCallbacks(runnableCode);
    }
}
