package com.example.gd.oticket;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by GD on 1/13/2018.
 */

public class Ticket implements Serializable, Comparable<Ticket>{
    private String id;
    private String ticketNo;
    private String issueTime;
    private String queueId;
    private int waitTime;
    private int pplAhead;
    private String mobileUserId;
    private int postponed;
    private String status;
    private String disposedTime;
    private String branchName;
    private String serviceName;
    private String serveTime;
    private String ticketServingNow;

    public Ticket(String id, String ticketNo, String issueTime, String queueId, int waitTime, int pplAhead, String mobileUserId, int postponed,
                  String status, String disposedTime, String branchName, String serviceName, String serveTime, String ticketServingNow){
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
        this.setBranchName(branchName);
        this.setServiceName(serviceName);
        this.setServeTime(serveTime);
        this.setTicketServingNow(ticketServingNow);
    }

    public String getId(){
        return this.id;
    }

    public String getTicketNo(){
        return this.ticketNo;
    }

    public String getIssueTime() {
        return this.issueTime;
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

    public String getMobileUserId(){
        return this.mobileUserId;
    }

    public String getStatus(){
        return this.status;
    }

    public String getDisposedTime(){
        return this.disposedTime;
    }

    public String getBranchName(){
        return this.branchName;
    }

    public String getServiceName(){
        return this.serviceName;
    }

    public String getServeTime(){
        return this.serveTime;
    }

    public String getTicketServingNow(){
        return this.ticketServingNow;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setTicketNo(String ticketNo){
        this.ticketNo = ticketNo;
    }

    public void setIssueTime(String issueTime) {
        this.issueTime = issueTime;
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

    public void setMobileUserId(String mobileUserId){
        this.mobileUserId = mobileUserId;
    }

    public void setPostponed(int postponed){
        this.postponed = postponed;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void setDisposedTime(String disposedTime){
        this.disposedTime = disposedTime;
    }

    public void setBranchName(String branchName){
        this.branchName = branchName;
    }

    public void setServiceName(String serviceName){
        this.serviceName = serviceName;
    }

    public void setServeTime(String serveTime){
        this.serveTime = serveTime;
    }

    public void setTicketServingNow(String ticketServingNow){
        this.ticketServingNow = ticketServingNow;
    }

    @Override
    public int compareTo(@NonNull Ticket ticket) {
        return this.getWaitTime() - ticket.getWaitTime();
    }
}
