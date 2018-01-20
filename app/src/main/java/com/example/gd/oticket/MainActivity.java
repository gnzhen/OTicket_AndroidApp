package com.example.gd.oticket;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
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
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    Branch branch;
    Service service;
    BranchService branchService;
    Queue queue;
    Ticket ticket;
    Dialog confirmDialog, issueTicketDialog, postponeDialog, cancelTicketDialog;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        branches = new ArrayList<>();
        services = new ArrayList<>();
        branchServices = new ArrayList<>();
        queues = new ArrayList<>();
        tickets = new ArrayList<>();

        fab = findViewById(R.id.fab);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        searchBar = toolbar.findViewById(R.id.search_bar);
        searchView = toolbar.findViewById(R.id.search_view);

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
                DisplayFragment(new BranchFrag(), "branches");
                setNavActiveItem(R.id.nav_take_a_ticket);
            }
        });

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        showBackButton(true);

        setData();

        //Set default fragment display
        Fragment defaultFragment = new TicketFrag();
        setBundle(defaultFragment, "tickets");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_layout, defaultFragment);
        ft.commit();
        drawer.closeDrawer(GravityCompat.START);
        setNavActiveItem(R.id.nav_my_ticket);
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
                data = "tickets";
                break;
            case R.id.nav_take_a_ticket:
                fragment = new BranchFrag();
                data = "branches";
                break;
            case R.id.nav_history:
                fragment = new HistoryFrag();
                data = "histories";
                break;
        }

        DisplayFragment(fragment, data);

        return true;
    }

    /*
     * Functions for layout control
     */
    public void DisplayFragment(Fragment fragment, String bundleData){

        if (fragment != null){
            if(bundleData != null)
                setBundle(fragment, bundleData);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_layout, fragment);
            ft.addToBackStack("fragment");
            ft.commit();
        }

        clearSearchView();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
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

    public void showIssueTicketDialog(Queue shortestQueue){
        issueTicketDialog = new Dialog(this);
        issueTicketDialog.setContentView(R.layout.dialog_issue_ticket);
        issueTicketDialog.setCancelable(false);

        TextView serviceTV = issueTicketDialog.findViewById(R.id.issue_ticket_dialog_service);
        TextView waitTimeTV = issueTicketDialog.findViewById(R.id.issue_ticket_dialog_wait_time);
        String branchServiceId = shortestQueue.getBranchServiceId();
        Service queueService = getServiceByBranchServiceId(branchServiceId);
        serviceTV.setText(queueService.getName());
        waitTimeTV.setText(intTimeToString(getWaitTimeByQueue(shortestQueue)));

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
                showConfirmationDialog("issueTicket");
            }
        });

        issueTicketDialog.show();
    }

    public void showConfirmationDialog(String action){
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
                showConfirmationDialog("postponeTicket");
            }
        });

        postponeDialog.show();
    }

    public void setContentText(String text){
        TextView content = findViewById(R.id.content_text_view);
        content.setText(text);
    }


    /*
     * Functions for action control
     */
    public boolean issueTicket(){
        return true;
    }

    /*
     * Other functions
     */
    public void setData(){
        //hardcode service data
        for(int j = 0; j < 5; j++) {
            String serviceId = "service " + Integer.toString(j);
            String[] serviceName = {"Customer Service", "Order", "Cashier", "Pick Up", "Other Services"};
            Service service = new Service(serviceId, serviceName[j]);
            services.add(service);
        }

        //hardcode branch data
        for(int i = 0; i < 5; i++){
            String id = "branch " + Integer.toString(i+1);
            String[] name = {"Kepong", "KL", "PJ", "Rawang", "Cheras"};
            String desc = "1, jalan kepong, Kepong.";

            Branch branch = new Branch(id, name[i], desc, services);
            branches.add(branch);
        }

        //hardcode branchservice data
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                String id = branches.get(i).getId() + "_" + services.get(j).getId();
                String branchId = branches.get(i).getId();
                String serviceId = services.get(j).getId();
                int avgWaitTime = 700;

                BranchService branchService = new BranchService(id, branchId, serviceId, avgWaitTime);
                branchServices.add(branchService);
            }
        }

        //hardcode queue data
        int a = 0;
        for(BranchService bs: branchServices){
            int id = a;
            ArrayList<String> counterIds = new ArrayList<>();
            int ticketServingNow = 1;
            String counterId = "counter" + a;
            counterIds.add(counterId);

            queue = new Queue(id, bs.getId(), counterIds, ticketServingNow);
            queues.add(queue);
            a++;
            queue.addTicketIdToQueue(0);
            queue.addTicketIdToQueue(1);
            queue.addTicketIdToQueue(2);
            queue.addTicketIdToQueue(3);
            queue.addTicketIdToQueue(4);
        }

        //hardcode ticket data
        for(int i = 0; i < 5; i++){
            int id = i;
            String ticketNo = String.format("%04d", id);
            int queueId = i;
            int waitTime = (i + 1) * 2000;
            int pplAhead = 5;
            int userId = i;

            ticket = new Ticket(id, ticketNo, queueId, waitTime, pplAhead, userId);
            tickets.add(ticket);
        }
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

    public Queue getQueueById(int id){
        queue = null;

        for(Queue q: queues){
            if(q.getId() == id)
                queue = q;
        }
        return queue;
    }

    public Ticket getTicketById(long id){
        ticket = null;

        for(Ticket t: tickets){
            if(t.getId() == id)
                ticket = t;
        }
        return ticket;
    }

    public ArrayList<BranchService> getBranchServicesByBranchId(String id){
        ArrayList<BranchService> serviceOfBranch = new ArrayList<BranchService>();

        for(BranchService branchService: branchServices){
            if(branchService.getBranchId().equals(id))
                serviceOfBranch.add(branchService);
        }
        return serviceOfBranch;
    }

    public Service getServiceByBranchServiceId(String id){
        Service service = null;

        service = getServiceById(branchService.getServiceId());

        return service;
    }

    public Queue getQueueByBranchServiceId(String id){

        for(Queue q: queues){
            if(q.getBranchServiceId().equals(id)){
                queue = q;
            }
        }

        return queue;
    }

    public Queue getQueueByTicketId(long id){
        return getQueueById(getTicketById(id).getQueueId());
    }

    public Branch getBranchByTicketId(long id){
        Branch branchOfQueue = null;
        Queue queue = getQueueByTicketId(id);
        BranchService branchService = getBranchServiceById(queue.getBranchServiceId());
        String branchId = branchService.getBranchId();

        for(Branch b: branches){
            if(b.getId().equals(branchId))
                branchOfQueue = b;
        }

        return branchOfQueue;
    }

    public Service getServiceByTicketId(long id){
        Service serviceOfQueue = null;
        Queue queue = getQueueByTicketId(id);
        BranchService branchService = getBranchServiceById(queue.getBranchServiceId());
        String serviceId = branchService.getServiceId();

        for(Service s: services){
            if(s.getId().equals(serviceId))
                serviceOfQueue = s;
        }

        return serviceOfQueue;
    }

    public int getWaitTimeByQueue(Queue queue){
        BranchService queueBranchService = getBranchServiceById(queue.getBranchServiceId());
        int avgWaitTime = queueBranchService.getAvgWaitTime();
        int numberOfTicketInQueue = queue.getNumberOfTicket();
        int waitTime = numberOfTicketInQueue * avgWaitTime;

        return waitTime;
    }

    public String getServeTimeStringByTicketId(long id){
        int waitTime = getTicketById(id).getWaitTime();

        Calendar now = Calendar.getInstance();
        now.add(Calendar.SECOND,waitTime);

        DateFormat dateFormat = new SimpleDateFormat("h:mm a");

        return dateFormat.format(now.getTime());
    }

    public String getTicketNoServingNowByTicket(Ticket ticket){
        queue = getQueueById(ticket.getQueueId());
        Ticket ticketServingNow = getTicketById(queue.getTicketIdServingNow());

        return ticketServingNow.getTicketNo();
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
}
