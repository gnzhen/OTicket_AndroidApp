package com.example.gd.oticket;

import java.io.Serializable;

/**
 * Created by GD on 1/13/2018.
 */

public class Service implements Serializable{
    private String id;
    private String name;
    private int avgWaitTime;
    private int pplInQueue;

    public Service(){

    }

    public Service(String id, String name){
        this.setId(id);
        this.setName(name);
    }

    public Service(String id, String name, int avgWaitTime){
        this.setId(id);
        this.setName(name);
        this.setAvgWaitTime(avgWaitTime);
    }

    public Service(String id, String name, int avgWaitTime, int pplInQueue){
        this.setId(id);
        this.setName(name);
        this.setAvgWaitTime(avgWaitTime);
        this.setPplInQueue(pplInQueue);
    }

    public String getId(){
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getAvgWaitTime() {
        return this.avgWaitTime;
    }

    public int getPplInQueue() {
        return this.pplInQueue;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setAvgWaitTime(int avgWaitTime){
        this.avgWaitTime = avgWaitTime;
    }

    public void setPplInQueue(int pplInQueue){
        this.pplInQueue = pplInQueue;
    }

    public int getWaitTime() {
        return this.avgWaitTime * this.pplInQueue;
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
}
