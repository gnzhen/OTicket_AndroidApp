package com.example.gd.oticket;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * Created by GD on 1/13/2018.
 */

public class Ticket implements Serializable{
    private int id;
    private String ticketNo;
    private SimpleDateFormat issueTime;
    private int queueId;
    private int waitTime;
    private int pplAhead;
    private int userId;
    private int postponed;

    public Ticket(int id, String ticketNo, int queueId, int waitTime, int pplAhead){
        this.setId(id);
        this.setTicketNo(ticketNo);
        this.setQueueId(queueId);
        this.setWaitTime(waitTime);
        this.setPplAhead(pplAhead);
        this.setPostponed(0);
    }

    public Ticket(int id, String ticketNo, int queueId, int waitTime, int pplAhead, int userId){
        this.setId(id);
        this.setTicketNo(ticketNo);
        this.setQueueId(queueId);
        this.setWaitTime(waitTime);
        this.setPplAhead(pplAhead);
        this.setUserId(userId);
        this.setPostponed(0);
    }

    public int getId(){
        return this.id;
    }

    public String getTicketNo(){
        return this.ticketNo;
    }

    public SimpleDateFormat getIssueTime() {
        return this.issueTime;
    }

    public int getQueueId(){
        return this.queueId;
    }

    public int getWaitTime(){
        return this.waitTime;
    }

    public int getPplAhead(){
        return this.pplAhead;
    }

    public int getUserId(){
        return this.userId;
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

    public void setIssueTime(SimpleDateFormat issueTime) {
        this.issueTime = issueTime;
    }

    public void setQueueId(int queueId){
        this.queueId = queueId;
    }

    public void setWaitTime(int waitTime){
        this.waitTime = waitTime;
    }

    public void setPplAhead(int pplAhead){
        this.pplAhead = pplAhead;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public void setPostponed(int postponed){
        this.postponed = postponed;
    }
}
