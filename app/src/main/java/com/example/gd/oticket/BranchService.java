package com.example.gd.oticket;

import java.io.Serializable;

/**
 * Created by GD on 1/13/2018.
 */

public class BranchService implements Serializable{
    private String id;
    private String serviceName;
    private int waitTime;
    private int totalTicket;

    public BranchService(String id, String serviceName, int waitTime, int totalTicket){
        this.setId(id);
        this.setServiceName(serviceName);
        this.setWaitTime(waitTime);
        this.setTotalTicket(totalTicket);
    }
    public String getId(){
        return this.id;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public int getWaitTime() {
        return this.waitTime;
    }

    public int getTotalTicket() {
        return this.totalTicket;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setServiceName(String serviceName){
        this.serviceName = serviceName;
    }

    public void setWaitTime(int waitTime){
        this.waitTime = waitTime;
    }

    public void setTotalTicket(int totalTicket){
        this.totalTicket = totalTicket;
    }

}
