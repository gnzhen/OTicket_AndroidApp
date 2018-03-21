package com.example.gd.oticket;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by GD on 1/13/2018.
 */

public class Change implements Serializable{
    private String id;
    private String change;
    private int time;
    private ArrayList<Integer> ticketIds = new ArrayList<>();

    public Change(){

    }

    public Change(String id, String change, int time){
        this.setId(id);
        this.setChange(change);
        this.setTime(time);
    }

    public Change(String id, String change, int time, ArrayList<Integer> ticketIds){
        this.setId(id);
        this.setChange(change);
        this.setTime(time);
        this.setTicketIds(ticketIds);
    }

    public String getId(){
        return this.id;
    }

    public String getChange(){
        return this.change;
    }

    public int getTime(){
        return this.time;
    }

    public ArrayList<Integer> getTicketIds(){
        return this.ticketIds;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setChange(String change){
        this.change = change;
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

    public String getChangeName(){
        return this.change;
    }

}
