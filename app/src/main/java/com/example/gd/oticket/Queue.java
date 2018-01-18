package com.example.gd.oticket;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by GD on 1/13/2018.
 */

public class Queue implements Serializable{
    private int id;
    private String branchServiceId;
    private ArrayList<Integer> ticketIds;
    private int ticketServingNow;
    private String counterId;

    public Queue(int id, String branchServiceId, String counterId){
        this.setId(id);
        this.setBranchServiceId(branchServiceId);
        this.setCounterId(counterId);
        this.setTicketServingNow(0);
        this.ticketIds = new ArrayList<>();
    }

    public Queue(int id, String branchServiceId, String counterId, int ticketServingNow){
        this.setId(id);
        this.setBranchServiceId(branchServiceId);
        this.setCounterId(counterId);
        this.setTicketServingNow(ticketServingNow);
        this.ticketIds = new ArrayList<>();
        this.counterId = counterId;
    }

    public int getId(){
        return this.id;
    }

    public String getBranchServiceId() {
        return this.branchServiceId;
    }

    public int getTicketServingNow() {
        return this.ticketServingNow;
    }

    public String getCounterId(){
        return this.counterId;
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

    public void setId(int id){
        this.id = id;
    }

    public void setBranchServiceId(String branchServiceId){
        this.branchServiceId = branchServiceId;
    }

    public void setCounterId(String counterId){
        this.counterId = counterId;
    }

    public void setTicketServingNow(int ticketServingNow){
        this.ticketServingNow = ticketServingNow;
    }

    public void addTicketIdToQueue(int id){
        ticketIds.add(id);
    }
}
