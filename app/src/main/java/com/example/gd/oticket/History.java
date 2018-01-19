package com.example.gd.oticket;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * Created by GD on 1/13/2018.
 */

public class History implements Serializable{
    private int id;
    private int ticketId;
    private int servingId;
    private SimpleDateFormat doneTime;

    public History(int id, int ticketId, int servingId, SimpleDateFormat doneTime){
        this.setId(id);
        this.setTicketId(ticketId);
        this.setServingId(servingId);
        this.setDoneTime(doneTime);
    }

    public int getId(){
        return this.id;
    }

    public int getTicketId(){
        return this.ticketId;
    }

    public int getServingId(){
        return this.servingId;
    }

    public SimpleDateFormat getDoneTime(){
        return this.doneTime;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setTicketId(int ticketId){
        this.ticketId = ticketId;
    }

    public void setServingId(int servingId){
        this.servingId = servingId;
    }

    public void setDoneTime(SimpleDateFormat doneTime){
        this.doneTime = doneTime;
    }
}
