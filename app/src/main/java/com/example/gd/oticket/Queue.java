package com.example.gd.oticket;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GD on 1/13/2018.
 */

public class Queue implements Serializable{
    private long id;
    private String branchServiceId;
    private ArrayList<Integer> ticketIds = new ArrayList<>();
    private long ticketIdServingNow;
    private ArrayList<String> counterIds = new ArrayList<>();

    public Queue(long id, String branchServiceId, ArrayList<String> counterIds){
        this.setId(id);
        this.setBranchServiceId(branchServiceId);
        this.setCounterIds(counterIds);
        this.setTicketIdServingNow(0);
        this.setCounterIds(counterIds);
    }

    public Queue(long id, String branchServiceId, ArrayList<String> counterIds, long ticketIdServingNow){
        this.setId(id);
        this.setBranchServiceId(branchServiceId);
        this.setTicketIdServingNow(ticketIdServingNow);
        counterIds = new ArrayList<>();
        this.setCounterIds(counterIds);
    }

    public long getId(){
        return this.id;
    }

    public String getBranchServiceId() {
        return this.branchServiceId;
    }

    public long getTicketIdServingNow() {
        return this.ticketIdServingNow;
    }

    public ArrayList<String> getCounterIds(){
        return this.counterIds;
    }

    public ArrayList<Integer> getTicketIds(){
        return this.ticketIds;
    }

    public int getNumberOfTicket(){
        return this.ticketIds.size();
    }

    public int getLatestTicketId (){
        return this.ticketIds.get(ticketIds.size() - 1);
    }

    public void setId(long id){
        this.id = id;
    }

    public void setBranchServiceId(String branchServiceId){
        this.branchServiceId = branchServiceId;
    }

    public void setCounterIds(ArrayList<String> counterIds){
        this.counterIds = counterIds;
    }

    public void setTicketIdServingNow(long ticketIdServingNow){
        this.ticketIdServingNow = ticketIdServingNow;
    }

    public void addTicketIdToQueue(int id){
        ticketIds.add(id);
    }
}
