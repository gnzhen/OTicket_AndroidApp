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
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        branches = new ArrayList<>();
        services = new ArrayList<>();
        branchServices = new ArrayList<>();
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

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Set default fragment display
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_layout, new TicketFrag());
        ft.commit();
        drawer.closeDrawer(GravityCompat.START);
        setNavActiveItem(R.id.nav_my_ticket);

        setData();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
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

    public void showIssueTicketDialog(BranchService branchService){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_issue_ticket);
        dialog.setCancelable(false);

        Button cancelBtn = dialog.findViewById(R.id.issue_ticket_dialog_cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dialog.dismiss();
            }
        });

        Button issueBtn = dialog.findViewById(R.id.issue_ticket_dialog_issue_btn);
        issueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDialog();
            }
        });

        dialog.show();
    }

    public void showConfirmDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Confirm issue ticket?").setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();
            }
        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        // Get the alert dialog buttons reference
        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        // Change the alert dialog buttons layout
        positiveButton.setTextColor(getResources().getColor(R.color.colorAccent));
        positiveButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        negativeButton.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        negativeButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        setMargins(positiveButton, 30, 0, 30, 0);
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
            String serviceId = "service " + Integer.toString(j+1);
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
                String id = branches.get(i).getId() + services.get(j).getId();
                String branchId = branches.get(i).getId();
                String serviceId = services.get(j).getId();
                int avgWaitTime = 700;

                BranchService branchService = new BranchService(id, branchId, serviceId, avgWaitTime);
                branchServices.add(branchService);
            }
        }

        int a = 0;
        //hardcode queue data
        for(BranchService bs: branchServices){
            String id = "queue_" + a;
            int latestTicketNo = 8;
            int ticketServingNow = 3;
            queue = new Queue(id, bs.getId(), latestTicketNo, ticketServingNow);
            a++;
        }

        //hardcode ticket data
        for(int i = 0; i < 2; i++){
            String id = "TIX_" + i;
            int ticketNo = i + 1;
            String queueId = "Kepong_Order";
            int waitTime = 3665;
            int pplAhead = 5;

            ticket = new Ticket(id, ticketNo, queueId, waitTime, pplAhead);
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

    public String getWaitTimeString(int waitTime){
        String waitTimeString;

        if(waitTime < 60){
            waitTimeString = Integer.toString(waitTime) + "sec";
        }
        else if (waitTime < 3600) {
            int m = waitTime / 60;
            int s = waitTime % 60;
            waitTimeString = Integer.toString(m) + "min "
                    + Integer.toString(s) + "sec";
        }
        else {
            int h = waitTime / 3600;
            int mTemp = waitTime % 3600;
            int m = mTemp / 60;

            waitTimeString = Integer.toString(h) + "hr "
                    + Integer.toString(m) + "min";
        }
        return waitTimeString;
    }

    /*
     * Functions to get objects
     */
    public Branch getBranchById(String id){
        branch = null;

        for(Branch b: branches){
            if(b.getId() == id)
                branch = b;
        }
        return branch;
    }

    public Service getServiceById(String id){
        service = null;

        for(Service s: services){
            if(s.getId() == id)
                service = s;
        }
        return service;
    }

    public BranchService getBranchServiceById(String id){
        branchService = null;

        for(BranchService bs: branchServices){
            if(bs.getId() == id)
                branchService = bs;
        }
        return branchService;
    }

    public Queue getQueueById(String id){
        queue = null;

        for(Queue q: queues){
            if(q.getId() == id)
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

    public ArrayList<Service> getServicesByBranchId(String id){
        return getBranchById(id).getServices();
    }

    public ArrayList<BranchService> getBranchServicesByBranchId(String id){
        ArrayList<BranchService> serviceOfBranch = new ArrayList<BranchService>();

        for(BranchService branchService: branchServices){
            if(branchService.getBranchId() == id)
                serviceOfBranch.add(branchService);
        }
        return serviceOfBranch;
    }

    public Service getServiceByBranchServiceId(String id){
        Service service = null;

        for(BranchService bs: branchServices){
            if(bs.getServiceId() == id){
                service = getServiceById(bs.getServiceId());
            }
        }
        return service;
    }

    public ArrayList<Queue> getQueuesByBranchServiceId(String id){
        ArrayList<Queue> queuesOfService = new ArrayList<Queue>();

        for(Queue q: queues){
            if(q.getBranchServiceId() == id){
                queuesOfService.add(q);
            }
        }

        return queuesOfService;
    }

    public Queue getShortestQueuesByBranchServiceId(String id){
        ArrayList<Queue> queuesOfService = getQueuesByBranchServiceId(id);
        Queue shortestQueue = queuesOfService.get(0);

        for(Queue q: queues){
            if(q.getWaitTime() < shortestQueue.getWaitTime())
                shortestQueue = q;
        }

        return shortestQueue;
    }

    public Queue getQueueByTicketId(String id){
        return getQueueById(getTicketById(id).getQueueId());
    }

    public Branch getBranchByQueueId(String id){
        Branch branchOfQueue = null;
        Queue queue = getQueueById(id);
        BranchService branchService = getBranchServiceById(queue.getBranchServiceId());
        String branchId = branchService.getBranchId();

        for(Branch b: branches){
            if(b.getId() == branchId)
                branchOfQueue = b;
        }

        return branchOfQueue;
    }

    public Branch getBranchByTicketId(String id){
        Branch branchOfQueue = null;
        Queue queue = getQueueByTicketId(id);
        BranchService branchService = getBranchServiceById(queue.getBranchServiceId());
        String branchId = branchService.getBranchId();

        for(Branch b: branches){
            if(b.getId() == branchId)
                branchOfQueue = b;
        }

        return branchOfQueue;
    }

    public Service getServiceByTicketId(String id){
        Service serviceOfQueue = null;
        Queue queue = getQueueByTicketId(id);
        BranchService branchService = getBranchServiceById(queue.getBranchServiceId());
        String serviceId = branchService.getServiceId();

        for(Service s: services){
            if(s.getId() == serviceId)
                serviceOfQueue = s;
        }

        return serviceOfQueue;
    }
}
