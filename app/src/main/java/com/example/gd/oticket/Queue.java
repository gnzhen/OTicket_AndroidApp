package com.example.gd.oticket;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by GD on 1/13/2018.
 */

public class Queue implements Serializable{
    private long id;
    private String branchServiceId;
    private long ticketIdServingNow;
    private int waitTime;
    private int totalTicket;
    private int pendingTicket;
    private Date startTime;
    private Date endTime;
    private int active;
    private int avgWaitTime;
    private ArrayList<Integer> ticketIds = new ArrayList<>();
    private ArrayList<String> counterIds = new ArrayList<>();

    public Queue(long id, String branchServiceId, long ticketIdServingNow, int waitTime, int pendingTicket){
        this.setId(id);
        this.setBranchServiceId(branchServiceId);
        this.setTicketIdServingNow(ticketIdServingNow);
        this.setWaitTime(waitTime);
        this.setPendingTicket(pendingTicket);
    }

    public Queue(long id, String branchServiceId, int waitTime, int pendingTicket){
        this.setId(id);
        this.setBranchServiceId(branchServiceId);
        this.setWaitTime(waitTime);
        this.setPendingTicket(pendingTicket);
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

    public int getWaitTime(){
        return this.waitTime;
    }

    public int getPendingTicket(){
        return this.pendingTicket;
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

    public void setWaitTime(int waitTime){
        this.waitTime = waitTime;
    }

    public void setPendingTicket(int pendingTicket){
        this.pendingTicket = pendingTicket;
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
