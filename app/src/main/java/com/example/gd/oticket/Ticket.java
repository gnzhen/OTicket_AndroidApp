package com.example.gd.oticket;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * Created by GD on 1/13/2018.
 */

public class Ticket implements Serializable, Comparable<Ticket>{
    private int id;
    private String ticketNo;
    private long issueTime;
    private long queueId;
    private int waitTime;
    private int pplAhead;
    private String username;
    private int postponed;

    public Ticket(int id, String ticketNo, long queueId, int waitTime, int pplAhead){
        this.setId(id);
        this.setTicketNo(ticketNo);
        this.setQueueId(queueId);
        this.setWaitTime(waitTime);
        this.setPplAhead(pplAhead);
        this.setPostponed(0);
        this.setIssueTime(1516451460);
    }

    public Ticket(int id, String ticketNo, long queueId, int waitTime, int pplAhead, String username, long issueTime){
        this.setId(id);
        this.setTicketNo(ticketNo);
        this.setQueueId(queueId);
        this.setWaitTime(waitTime);
        this.setPplAhead(pplAhead);
        this.setUsername(username);
        this.setPostponed(0);
        this.setIssueTime(issueTime);
    }

    public int getId(){
        return this.id;
    }

    public String getTicketNo(){
        return this.ticketNo;
    }

    public long getIssueTime() {
        return this.issueTime;
    }

    public long getQueueId(){
        return this.queueId;
    }

    public int getWaitTime(){
        return this.waitTime;
    }

    public int getPplAhead(){
        return this.pplAhead;
    }

    public String getUsername(){
        return this.username;
    }

    public int getPostponed(){
        return this.postponed;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setTicketNo(String ticketNo){
        this.ticketNo = ticketNo;
    }

    public void setIssueTime(long issueTime) {
        this.issueTime = issueTime;
    }

    public void setQueueId(long queueId){
        this.queueId = queueId;
    }

    public void setWaitTime(int waitTime){
        this.waitTime = waitTime;
    }

    public void setPplAhead(int pplAhead){
        this.pplAhead = pplAhead;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPostponed(int postponed){
        this.postponed = postponed;
    }

    @Override
    public int compareTo(@NonNull Ticket ticket) {
        return this.getWaitTime() - ticket.getWaitTime();
    }
}
