package com.example.gd.oticket;

import java.io.Serializable;

/**
 * Created by GD on 1/13/2018.
 */

public class Ticket implements Serializable{
    private String id;
    private int ticketNo;
    private String queueId;
    private int waitTime;
    private int pplAhead;

    public Ticket(String id, int ticketNo, String queueId, int waitTime, int pplAhead){
        this.setId(id);
        this.setTicketNo(ticketNo);
        this.setQueueId(queueId);
        this.setWaitTime(waitTime);
        this.setPplAhead(pplAhead);
    }

    public String getId(){
        return this.id;
    }

    public int getTicketNo(){
        return this.ticketNo;
    }

    public String getQueueId(){
        return this.queueId;
    }

    public int getWaitTime(){
        return this.waitTime;
    }

    public int getPplAhead(){
        return this.pplAhead;
    }

    public String getTicketNoString(){
        return String.format("%05d", this.ticketNo);
    }

    public void setId(String id){
        this.id = id;
    }

    public void setTicketNo(int ticketNo){
        this.ticketNo = ticketNo;
    }

    public void setQueueId(String queueId){
        this.queueId = queueId;
    }

    public void setWaitTime(int waitTime){
        this.waitTime = waitTime;
    }

    public void setPplAhead(int pplAhead){
        this.pplAhead = pplAhead;
    }
}
