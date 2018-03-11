package com.example.gd.oticket;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by GD on 1/13/2018.
 */

public class Change implements Serializable{
    private long id;
    private int change;
    private int time;
    private ArrayList<Integer> ticketIds = new ArrayList<>();

    public Change(){

    }

    public Change(long id, int change, int time){
        this.setId(id);
        this.setChange(change);
        this.setTime(time);
    }

    public Change(long id, int change, int time, ArrayList<Integer> ticketIds){
        this.setId(id);
        this.setChange(change);
        this.setTime(time);
        this.setTicketIds(ticketIds);
    }

    public long getId(){
        return this.id;
    }

    public int getChange(){
        return this.change;
    }

    public int getTime(){
        return this.time;
    }

    public ArrayList<Integer> getTicketIds(){
        return this.ticketIds;
    }

    public void setId(long id){
        this.id = id;
    }

    public void setChange(int change){
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
        if(this.change == 0)
            return "reduced";
        else if(this.change == 1)
            return "increased";
        else
            return "";
    }

}
