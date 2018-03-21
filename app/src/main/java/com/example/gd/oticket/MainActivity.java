package com.example.gd.oticket;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.gd.oticket.myrequest.MyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.function.LongToIntFunction;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawer;
    FloatingActionButton fab;
    NavigationView navigationView;
    RelativeLayout searchBar;
    SearchView searchView;
    ActionBar actionBar;
    ArrayList<Branch> branches;
    ArrayList<Service> services;
    ArrayList<BranchService> branchServices;
    ArrayList<Queue> queues;
    ArrayList<Ticket> tickets;
    ArrayList<History> histories;
    Branch branch;
    Service service;
    BranchService branchService;
    Queue queue;
    Ticket ticket;
    Change change;
    History history;
    Dialog confirmDialog, issueTicketDialog, postponeDialog, cancelTicketDialog;
    Dialog reminderDialog, timeChangeDialog;
    ActionBarDrawerToggle toggle;
    TextView name, email;
    MyRequest request;
    FrameLayout progressBarHolder;
    ProgressBar spinner, mainSpinner;
    Toast toast;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getApplicationContext().getSharedPreferences("auth", MODE_PRIVATE);
        editor = pref.edit();
        checkAuth();

        branches = new ArrayList<>();
        services = new ArrayList<>();
        branchServices = new ArrayList<>();
        queues = new ArrayList<>();
        tickets = new ArrayList<>();
        histories = new ArrayList<>();

        fab = findViewById(R.id.fab);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        searchBar = toolbar.findViewById(R.id.search_bar);
        searchView = toolbar.findViewById(R.id.search_view);
        name = navigationView.getHeaderView(0).findViewById(R.id.header_name);
        email = navigationView.getHeaderView(0).findViewById(R.id.header_email);
        progressBarHolder = findViewById(R.id.main_progressBarHolder);
        mainSpinner = findViewById(R.id.main_progress);
        spinner = findViewById(R.id.progress_bar);
        progressBarHolder.bringToFront();

        request = new MyRequest(this);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_add_white_24dp));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayFragment(new BranchFrag(), null, "BRANCH");
                setNavActiveItem(R.id.nav_take_a_ticket);
            }
        });

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        showBackButton(true);
        showSpinner(false);
        setData();

        //Set default fragment display
        Fragment defaultFragment = new TicketFrag();
        setBundle(defaultFragment, "tickets");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_layout, defaultFragment);
        ft.commit();
        drawer.closeDrawer(GravityCompat.START);
        setNavActiveItem(R.id.nav_my_ticket);

        setUserDetails();

//        testDialog();
    }

    public void onResume() {
        super.onResume();

        checkAuth();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            clearSearchView();
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        String data = "";

        switch (id){
            case R.id.nav_my_ticket:
                fragment = new TicketFrag();
                data = "TICKET";
                break;
            case R.id.nav_take_a_ticket:
                fragment = new BranchFrag();
                data = "BRANCH";
                break;
            case R.id.nav_history:
                fragment = new HistoryFrag();
                data = "HISTORY";
                break;
            case R.id.nav_acc_setting:
                fragment = new HistoryFrag();
                data = "ACCOUNT";
                break;
            case R.id.nav_logout:
                editor.clear().commit();
                checkAuth();
                break;
        }

        displayFragment(fragment, null, "data");

        return true;
    }

    /*
     * Functions for layout control
     */
    public void showSpinner(boolean show){
        if(show) {
            setContentText("");
            spinner.setVisibility(View.VISIBLE);
        }
        else{
            spinner.setVisibility(View.GONE);
        }
    }

    public void showSpinnerWithOverlay(boolean show){
        if(show) {
            setContentText("");
            mainSpinner.setVisibility(View.VISIBLE);
            progressBarHolder.setVisibility(View.VISIBLE);
        }
        else{
            mainSpinner.setVisibility(View.GONE);
            progressBarHolder.setVisibility(View.GONE);
        }
    }

    public void showToast(String text){
        toast = Toast.makeText(this ,text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP| Gravity.CENTER_HORIZONTAL, 0, 150);
        toast.show();
    }

    public void testDialog(){
        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                if(timeChangeDialog != null){
                    if(!timeChangeDialog.isShowing()){
                        showReminderDialog();
                    }
                }
            }

            public void onFinish() {
                showReminderDialog();
            }

        }.start();
    }

    public void displayFragment(Fragment fragment, Bundle bundle, String tag){

        if (fragment != null){
            if(bundle != null)
                fragment.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_layout, fragment, tag);
            ft.addToBackStack("fragment");
            ft.commit();
        }

        clearSearchView();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void refreshFragment(String tag, Bundle bundle) {
        Fragment currentFrag = getSupportFragmentManager()
                .findFragmentByTag(tag);
        if (currentFrag != null && currentFrag.isVisible()) {
            if (bundle != null) {
                currentFrag.setArguments(bundle);
            }
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.detach(currentFrag);
            ft.attach(currentFrag);
            ft.commit();
        }
    }

    public void displayActivity(Context fromActivity, Class<?> toActivity){
        Intent intent = new Intent(fromActivity, toActivity);
        startActivity(intent);
    }

    public void showBackButton(boolean show){
        if(show){
            // Remove hamburger
            toggle.setDrawerIndicatorEnabled(false);
            // Show back button
            actionBar.setDisplayHomeAsUpEnabled(true);

            toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
        else{
            // Remove back button
            actionBar.setDisplayHomeAsUpEnabled(false);
            // Show hamburger
            toggle.setDrawerIndicatorEnabled(true);
            // Remove the/any drawer toggle listener
            toggle.setToolbarNavigationClickListener(null);
        }
    }

    public void clearSearchView(){
        if(searchView != null){
            searchView.setQuery("", false);
            searchView.setIconified(true);
        }
    }

    public void setTitle(String title){
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
        }
    }

    public void setNavActiveItem(int item){
        if (navigationView != null){
            navigationView.setCheckedItem(item);
            navigationView.setNavigationItemSelectedListener(this);
        }
    }

    public void displayFab(boolean display){
        if(display)
            fab.show();
        else
            fab.hide();
    }

    public void setSearchLabelText(String text){
        TextView searchLabel = toolbar.findViewById(R.id.search_view_label);
        searchLabel.setText(text);
    }

    public void showSearchBar(boolean show){
        if(show){
            if(actionBar != null)
                actionBar.setDisplayShowTitleEnabled(false);

            searchBar.setVisibility(View.VISIBLE);
            searchBar.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    searchView.setIconified(false);
                }
            });
        }
        else{
            if(actionBar != null)
                actionBar.setDisplayShowTitleEnabled(true);
            searchBar.setVisibility(View.GONE);
        }

    }

    private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    public Toolbar getToolbar(){
        return toolbar;
    }

    public void showIssueTicketDialog(final String branchServiceId, String serviceName, int waitTime, int pplInQueue){
        issueTicketDialog = new Dialog(this);
        issueTicketDialog.setContentView(R.layout.dialog_issue_ticket);
        issueTicketDialog.setCancelable(false);

        TextView serviceTV = issueTicketDialog.findViewById(R.id.issue_ticket_dialog_service);
        TextView waitTimeTV = issueTicketDialog.findViewById(R.id.issue_ticket_dialog_wait_time);
        TextView pplInQueueTV = issueTicketDialog.findViewById(R.id.issue_ticket_dialog_ppl_in_queue);

        serviceTV.setText(serviceName);
        waitTimeTV.setText(intTimeToString(waitTime));
        pplInQueueTV.setText(Integer.toString(pplInQueue));

        Button cancelBtn = issueTicketDialog.findViewById(R.id.issue_ticket_dialog_cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                issueTicketDialog.dismiss();
            }
        });

        Button issueBtn = issueTicketDialog.findViewById(R.id.issue_ticket_dialog_issue_btn);
        issueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog("issueTicket", branchServiceId);
            }
        });

        issueTicketDialog.show();
    }

    public void showReminderDialog(){
        reminderDialog = new Dialog(this);
        reminderDialog.setContentView(R.layout.dialog_reminder_noti);
        reminderDialog.setCancelable(false);

        LinearLayout ticketLayout = reminderDialog.findViewById(R.id.reminder_ticket);
        Button dismissBtn = reminderDialog.findViewById(R.id.reminder_dismiss_btn);
        Button postponeBtn = reminderDialog.findViewById(R.id.reminder_postpone_btn);
        Button cancelTicketBtn = reminderDialog.findViewById(R.id.reminder_cancel_ticket_btn);
        TextView ticketTV = reminderDialog.findViewById(R.id.reminder_ticket_number);
        TextView serviceTV = reminderDialog.findViewById(R.id.reminder_service);
        View dot1 = reminderDialog.findViewById(R.id.reminder_dot1);
        View dot2 = reminderDialog.findViewById(R.id.reminder_dot2);
        View dot3 = reminderDialog.findViewById(R.id.reminder_dot3);
        setLayerType(dot1);
        setLayerType(dot2);
        setLayerType(dot3);

        ticketTV.setText(ticket.getTicketNo());
//        serviceTV.setText(getServiceByTicketId(ticket.getId()).getName());

        ticketLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayFragment(new TicketDetailsFrag(), null, "TICKET_DETAILS");
                reminderDialog.dismiss();
            }
        });

        dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reminderDialog.dismiss();
            }
        });

        postponeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPostponeDialog(tickets.get(0));
            }
        });

        cancelTicketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmationDialog("cancelTicket", null);
            }
        });

        reminderDialog.show();
    }

    public void showTimeChangeDialog(Change change){
        timeChangeDialog = new Dialog(this);
        timeChangeDialog.setContentView(R.layout.dialog_time_change_noti);
        final LinearLayout timeChangeLayout = timeChangeDialog.findViewById(R.id.time_change_layout);
        timeChangeDialog.setCancelable(false);

        View dot1 = timeChangeDialog.findViewById(R.id.time_change_dot1);
        View dot2 = timeChangeDialog.findViewById(R.id.time_change_dot2);
        setLayerType(dot1);
        setLayerType(dot2);

        LinearLayout ticketLayout = timeChangeDialog.findViewById(R.id.time_change_ticket);
        Button dismissBtn = timeChangeDialog.findViewById(R.id.time_change_dismiss_btn);
        TextView actionTV = timeChangeDialog.findViewById(R.id.time_change_action);
        TextView timeTV = timeChangeDialog.findViewById(R.id.time_change_time);
        TextView ticketNoTV = timeChangeDialog.findViewById(R.id.time_change_ticket_number);
        TextView serviceTV = timeChangeDialog.findViewById(R.id.time_change_service);

        actionTV.setText(change.getChangeName());
        timeTV.setText(intTimeToString(change.getTime()));
//        ticket = getTicketById(change.getTicketIds().get(1));
        ticketNoTV.setText(ticket.getTicketNo());
//        serviceTV.setText(getServiceByTicketId(ticket.getId()).getName());

        ticketLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeChangeDialog.dismiss();
                displayFragment(new TicketDetailsFrag(), null, "TICKET_DETAILS");
            }
        });

        dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeChangeDialog.dismiss();
            }
        });

        timeChangeDialog.show();
    }

    public void showConfirmationDialog(final String action, final String data){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        String message = "Are you sure?";

        switch (action){
            case "issueTicket":
                message = "Confirm issue ticket?";
                break;
            case "postponeTicket":
                message = "Confirm postpone ticket?";
                break;
            case "cancelTicket":
                message = "Confirm cancel ticket?";
                break;
        }

        alertDialogBuilder.setMessage(message).setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();
                if(action.equals("issueTicket")) {
                    issueTicket(data);
                }

                if(issueTicketDialog != null)
                    issueTicketDialog.dismiss();
                if(postponeDialog != null)
                    postponeDialog.dismiss();
            }
        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if(issueTicketDialog != null)
                    issueTicketDialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        // Get the alert dialog buttons reference
        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        // Change the alert dialog buttons layout
        positiveButton.setTextColor(getResources().getColor(R.color.colorAccentLight));
        positiveButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        negativeButton.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        negativeButton.setBackgroundColor(getResources().getColor(R.color.colorAccentLight));

        setMargins(positiveButton, 30, 0, 30, 0);
    }

    public void showPostponeDialog(Ticket ticket){
        postponeDialog = new Dialog(this);
        postponeDialog.setContentView(R.layout.dialog_postpone);
        postponeDialog.setCancelable(false);
        LinearLayout postponeLayout = postponeDialog.findViewById(R.id.postpone_layout);
        View dot1 = postponeDialog.findViewById(R.id.postpone_dialog_dot1);
        View dot2= postponeDialog.findViewById(R.id.postpone_dialog_dot2);
        setLayerType(dot1);
        setLayerType(dot2);

        List<String> postponeTime = new ArrayList<>();

        int noOfTicketBehind = getNumberOfTicketBehindByTicket(ticket);
        int avgWaitTime = getAvgWaitTimeByTicket(ticket);

        for(int i = 1; i <= noOfTicketBehind; i++){
            int postponeTimeInt = i * avgWaitTime;
            postponeTime.add(intTimeToString(postponeTimeInt));
        }

        Spinner postponeSpinner = postponeDialog.findViewById(R.id.postpone_dialog_postpone_time_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, postponeTime);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        postponeSpinner.setAdapter(adapter);

        Button cancelBtn = postponeDialog.findViewById(R.id.postpone_dialog_cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                postponeDialog.dismiss();
            }
        });

        Button postponeBtn = postponeDialog.findViewById(R.id.postpone_dialog_postpone_btn);
        postponeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog("postponeTicket", null);
            }
        });

        postponeDialog.show();
    }

    public void setContentText(String text){
        Log.d("setContentText", text);
        TextView content = findViewById(R.id.content_text_view);
        content.setText(text);
    }

    public void setUserDetails(){

        String prefName = pref.getString("name", null);
        String prefEmail = pref.getString("email", null);

        if(prefName != null && prefEmail != null) {
            name.setText(prefName);
            email.setText(prefEmail);
        }
        else{
            editor.clear().commit();
            checkAuth();
        }
    }

    /*
     * Functions for action control
     */
    public void issueTicket(String branchServiceId){
        showSpinnerWithOverlay(true);

        String userId = pref.getString("id", null);
        if(userId == null)
            checkAuth();
        else {
            /* Issue Ticket */
            request.issueTicket(userId, branchServiceId, new MyRequest.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);

                        if(jsonObject.has("success")) {
                            showToast(jsonObject.get("success").toString());

                        } else if(jsonObject.has("fail")){
                            showToast(jsonObject.get("fail").toString());
                        } else {
                            //Show first validation error from server
                            Iterator<String> keys = jsonObject.keys();
                            String str_Name = keys.next();
                            JSONArray value;
                            try {
                                value = jsonObject.getJSONArray(str_Name);
                                showToast(value.get(0).toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    showSpinnerWithOverlay(false);
                    displayFragment(new TicketFrag(), null, "TICKET");
                }

                @Override
                public void onFailure(String error) {
                    Log.d("onFailure", error);
                }
            });
        }
    }

    public boolean postponeTicket(Ticket ticket){
        return true;
    }

    public boolean cancelTicket(Ticket ticket){
        return true;
    }

    /*
     * Other functions
     */
    public String getUserId(){
        return pref.getString("id", null);
    }

    public void checkAuth(){
        if(pref.getString("id", null) == null){
            Log.d("check Auth", "user haven't login");
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }else{
            Log.d("check Auth Main", "user already login");
        }
    }

    public void setLayerType(View view){
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public void setData(){
        //
    }

    public ArrayList<Branch> getBranches(){
        return branches;
    }

    public ArrayList<Service> getServices(){
        return services;
    }

    public ArrayList<BranchService> getBranchServices(){
        return branchServices;
    }

    public ArrayList<Queue> getQueues(){
        return queues;
    }

    public void setBundle(Fragment fragment, String bundleData){
        if(bundleData != null){
            Bundle bundle = new Bundle();

            if(bundleData == "branches")
                bundle.putSerializable(bundleData, branches);
            else if(bundleData == "services")
                bundle.putSerializable(bundleData, services);
            else if(bundleData == "branchServices")
                bundle.putSerializable(bundleData, branchServices);
            else if(bundleData == "queues")
                bundle.putSerializable(bundleData, queues);
            else if(bundleData == "tickets")
                bundle.putSerializable(bundleData, tickets);
            else if(bundleData == "ticketDetails")
                bundle.putSerializable(bundleData, ticket);
            else if(bundleData == "histories")
                bundle.putSerializable(bundleData, histories);

            fragment.setArguments(bundle);
        }
    }

    public String intTimeToString(int waitTime){
        String waitTimeString;

        if(waitTime < 60){
            waitTimeString = Integer.toString(waitTime) + "sec";
        }
        else if (waitTime < 3600) {
            int m = waitTime / 60;
            int s = waitTime % 60;

            waitTimeString = Integer.toString(m) + "min";
            if (s > 0)
                waitTimeString += " " + Integer.toString(s) + "sec";
        }
        else {
            int h = waitTime / 3600;
            int mTemp = waitTime % 3600;
            int m = mTemp / 60;

            waitTimeString = Integer.toString(h) + "hr";
            if (m > 0)
                waitTimeString += " " + Integer.toString(m) + "min";
        }
        return waitTimeString;
    }

    /*
     * Functions to get objects
     */
    public Service searchServiceById(ArrayList<Service> services, String id){
        service = null;
        for(int i = 0; i <services.size(); i++){
            if(services.get(i).getId().equals(id))
                service = services.get(i);
        }
        return service;
    }

    public Branch getBranchById(String id){
        branch = null;

        for(Branch b: branches){
            if(b.getId().equals(id))
                branch = b;
        }
        return branch;
    }

    public Service getServiceById(String id){
        service = null;

        for(Service s: services){
            if(s.getId().equals(id))
                service = s;
        }
        return service;
    }

    public BranchService getBranchServiceById(String id){
        branchService = null;

        for(BranchService bs: branchServices){
            if(bs.getId().equals(id))
                branchService = bs;
        }
        return branchService;
    }

    public Queue getQueueById(String id){
        queue = null;

        for(Queue q: queues){
            if(q.getId().equals(id))
                queue = q;
        }
        return queue;
    }

    public Ticket getTicketById(String id){
        ticket = null;

        for(Ticket t: tickets){
            if(t.getId() == id)
                ticket = t;
        }
        return ticket;
    }

    public Queue getQueueByTicketId(String id){
        return getQueueById(getTicketById(id).getQueueId());
    }

    public Branch getBranchByTicketId(String id){
        branch = null;
        queue = getQueueByTicketId(id);
        BranchService branchService = getBranchServiceById(queue.getBranchServiceId());
        String branchId = branchService.getBranchId();

        for(Branch b: branches){
            if(b.getId().equals(branchId))
                branch = b;
        }

        return branch;
    }

    public Service getServiceByTicketId(String id){
        service = null;
        Queue queue = getQueueByTicketId(id);
        BranchService branchService = getBranchServiceById(queue.getBranchServiceId());
        String serviceId = branchService.getServiceId();

        for(Service s: services){
            if(s.getId().equals(serviceId))
                service = s;
        }

        return service;
    }

    public ArrayList<Integer> getTicketsBehindByTicket(Ticket ticket){
        queue = getQueueByTicketId(ticket.getId());
        ArrayList<Integer> ticketsInQueue = queue.getTicketIds();
        ArrayList<Integer> ticketsBehind = new ArrayList<>();

        int ticketPos = ticketsInQueue.indexOf(ticket.getId());
        int lastPos = ticketsInQueue.size() - 1;

        for(int i = ticketPos + 1; i <= lastPos; i++){
            ticketsBehind.add(ticketsInQueue.get(i));
        }

        return ticketsBehind;
    }

    public int getNumberOfTicketBehindByTicket(Ticket ticket){
        return getTicketsBehindByTicket(ticket).size();
    }

    public int getAvgWaitTimeByTicket(Ticket ticket){

        queue = getQueueByTicketId(ticket.getId());
        branchService = getBranchServiceById(queue.getBranchServiceId());

        return branchService.getAvgWaitTime();
    }

    public int calWaitTimeInt(long serveTime, long doneTime){
        long diff = doneTime - serveTime;
        int waitTime;

        if ( diff > (long)Integer.MAX_VALUE ) {
            // diff is too big to convert, throw an exception
            waitTime = -((int)diff);

        }
        else {
            waitTime = (int)diff;
        }

        return waitTime;
    }
}
