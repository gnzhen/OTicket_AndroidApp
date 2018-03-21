package com.example.gd.oticket;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * Created by GD on 1/13/2018.
 */

public class History implements Serializable{
    private String id;
    private String ticketNo;
    private String issueTime;
    private String waitTime;
    private String branchName;
    private String serviceName;
    private String status;

    public History(String id, String ticketNo, String issueTime, String waitTime, String branchName, String serviceName, String status){
        this.setId(id);
        this.setTicketNo(ticketNo);
        this.setIssueTime(issueTime);
        this.setWaitTime(waitTime);
        this.setBranchName(branchName);
        this.setServiceName(serviceName);
        this.setStatus(status);
    }

    public String getId(){
        return this.id;
    }

    public String getTicketNo(){
        return this.ticketNo;
    }

    public String getIssueTime(){
        return this.issueTime;
    }

    public String getWaitTime(){
        return this.waitTime;
    }

    public String getBranchName(){
        return this.branchName;
    }

    public String getServiceName(){
        return this.serviceName;
    }

    public String getStatus(){
        return this.status;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setTicketNo(String ticketNo){
        this.ticketNo = ticketNo;
    }

    public void setIssueTime(String issueTime){
        this.issueTime = issueTime;
    }

    public void setWaitTime(String waitTime){
        this.waitTime = waitTime;
    }

    public void setBranchName(String branchName){
        this.branchName = branchName;
    }

    public void setServiceName(String serviceName){
        this.serviceName = serviceName;
    }

    public void setStatus(String status){
        this.status = status;
    }
}
