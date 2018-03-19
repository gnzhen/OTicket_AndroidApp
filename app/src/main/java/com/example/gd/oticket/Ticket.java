package com.example.gd.oticket;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by GD on 1/13/2018.
 */

public class Ticket implements Serializable, Comparable<Ticket>{
    private int id;
    private String ticketNo;
    private Date issueTime;
    private long queueId;
    private int waitTime;
    private int pplAhead;
    private String mobileUserId;
    private int postponed;
    private String status;
    private Date disposedTime;

    public Ticket(int id, String ticketNo, Date issueTime, long queueId, int waitTime, int pplAhead, String mobileUserId, int postponed, String status, Date disposedTime){
        this.setId(id);
        this.setTicketNo(ticketNo);
        this.setIssueTime(issueTime);
        this.setQueueId(queueId);
        this.setWaitTime(waitTime);
        this.setPplAhead(pplAhead);
        this.setMobileUserId(mobileUserId);
        this.setPostponed(postponed);
        this.setStatus(status);
        this.setDisposedTime(disposedTime);
    }

    public int getId(){
        return this.id;
    }

    public String getTicketNo(){
        return this.ticketNo;
    }

    public Date getIssueTime() {
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

    public String getMobileUserId(){
        return this.mobileUserId;
    }

    public String getStatus(){
        return this.status;
    }

    public Date getDisposedTime(){
        return this.disposedTime;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setTicketNo(String ticketNo){
        this.ticketNo = ticketNo;
    }

    public void setIssueTime(Date issueTime) {
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

    public void setMobileUserId(String mobileUserId){
        this.mobileUserId = mobileUserId;
    }

    public void setPostponed(int postponed){
        this.postponed = postponed;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void setDisposedTime(Date disposedTime){
        this.disposedTime = disposedTime;
    }

    @Override
    public int compareTo(@NonNull Ticket ticket) {
        return this.getWaitTime() - ticket.getWaitTime();
    }
}
