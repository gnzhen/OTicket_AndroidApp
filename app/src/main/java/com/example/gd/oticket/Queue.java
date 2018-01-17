package com.example.gd.oticket;

import java.io.Serializable;

/**
 * Created by GD on 1/13/2018.
 */

public class Queue implements Serializable{
    private String id;
    private String branchServiceId;
    private int latestTicketNo;
    private int ticketServingNow;
    private int waitTime;

    public Queue(String id, String branchServiceId){
        this.setId(id);
        this.setBranchServiceId(branchServiceId);
        this.setLatestTicketNo(0);
        this.setTicketServingNow(0);
        this.setWaitTime(0);
    }

    public Queue(String id, String branchServiceId, int latestTicketNo, int ticketServingNow){
        this.setId(id);
        this.setBranchServiceId(branchServiceId);
        this.setLatestTicketNo(latestTicketNo);
        this.setTicketServingNow(ticketServingNow);
        this.setWaitTime(0);
    }

    public Queue(String id, String branchServiceId, int latestTicketNo, int ticketServingNow, int waitTime){
        this.setId(id);
        this.setBranchServiceId(branchServiceId);
        this.setLatestTicketNo(latestTicketNo);
        this.setTicketServingNow(ticketServingNow);
        this.setWaitTime(waitTime);
    }

    public String getId(){
        return this.id;
    }

    public String getBranchServiceId() {
        return this.branchServiceId;
    }

    public int getLatestTicketNo() {
        return this.latestTicketNo;
    }

    public int getTicketServingNow() {
        return this.ticketServingNow;
    }

    public int getNoOfTicketToBeServed(){
        return (this.latestTicketNo - this.ticketServingNow) + 1;
    }

    public int getWaitTime() {
        return this.waitTime;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setBranchServiceId(String branchServiceId){
        this.branchServiceId = branchServiceId;
    }

    public void setLatestTicketNo(int latestTicketNo){
        this.latestTicketNo = latestTicketNo;
    }

    public void setTicketServingNow(int ticketServingNow){
        this.ticketServingNow = ticketServingNow;
    }

    public void setWaitTime(int waitTime){
        this.waitTime = waitTime;
    }

}
