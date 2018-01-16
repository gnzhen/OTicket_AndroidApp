package com.example.gd.oticket;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by GD on 1/13/2018.
 */

public class Ticket implements Serializable{
    private String queueNo;
    private Branch branch;
    private Service service;
    private int waitTime;

    public Ticket(){

    }

    public Ticket(String queueNo, Branch branch, Service service, int waitTime){
        this.setQueueNo(queueNo);
        this.setBranch(branch);
        this.setService(service);
        this.setWaitTime(waitTime);
    }

    public String getQueueNo(){
        return this.queueNo;
    }

    public Branch getBranch(){
        return this.branch;
    }

    public Service getService(){
        return this.service;
    }

    public int getWaitTime(){
        return this.waitTime;
    }

    public String getWaitTimeString(){
        String waitTimeString;
        int waitTime = getWaitTime();

        if(waitTime < 60){
            waitTimeString = Integer.toString(waitTime) + "s";
        }
        else if (waitTime < 3600) {
            int m = waitTime / 60;
            int s = waitTime % 60;
            waitTimeString = Integer.toString(m) + "m"
                    + Integer.toString(s) + "s";
        }
        else {
            int h = waitTime / 3600;
            int mTemp = waitTime % 3600;
            int m = mTemp / 60;

            waitTimeString = Integer.toString(h) + "h "
                    + Integer.toString(m) + "m ";
        }
        return waitTimeString;
    }

    public void setQueueNo(String queueNo){
        this.queueNo = queueNo;
    }

    public void setBranch(Branch branch){
        this.branch = branch;
    }

    public void setService(Service service){
        this.service = service;
    }

    public void setWaitTime(int waitTime){
        this.waitTime = waitTime;
    }
}
