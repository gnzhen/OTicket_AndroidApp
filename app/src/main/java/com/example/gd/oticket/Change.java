package com.example.gd.oticket;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by GD on 1/13/2018.
 */

public class Change implements Serializable{
    private int id;
    private int action;
    private int time;
    private ArrayList<Integer> ticketIds = new ArrayList<>();

    public Change(){

    }

    public Change(int id, int action, int time){
        this.setId(id);
        this.setAction(action);
        this.setTime(time);
    }

    public Change(int id, int action, int time, ArrayList<Integer> ticketIds){
        this.setId(id);
        this.setAction(action);
        this.setTime(time);
        this.setTicketIds(ticketIds);
    }

    public int getId(){
        return this.id;
    }

    public int getAction(){
        return this.action;
    }

    public int getTime(){
        return this.time;
    }

    public ArrayList<Integer> getTicketIds(){
        return this.ticketIds;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setAction(int action){
        this.action = action;
    }

    public void setTime(int time){
        this.time = time;
    }

    public void setTicketIds(ArrayList<Integer> ticketIds){
        this.ticketIds = ticketIds;
    }

    public void addTicketIds(int id){
        this.ticketIds.add(id);
    }

    public String getActionName(){
        if(this.action == 0)
            return "reduced";
        else if(this.action == 1)
            return "increased";
        else
            return "";
    }

}
