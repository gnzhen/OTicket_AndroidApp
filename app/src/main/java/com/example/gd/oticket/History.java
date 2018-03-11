package com.example.gd.oticket;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * Created by GD on 1/13/2018.
 */

public class History implements Serializable{
    private int id;
    private long ticketId;
    private String staffId;
    private String counterId;
    private long serveTime;
    private long doneTime;

    public History(int id, long ticketId, String staffId, String counterId, long serveTime){
        this.setId(id);
        this.setTicketId(ticketId);
        this.setCounterId(counterId);
        this.setStaffId(staffId);
        this.setServeTime(serveTime);
    }

    public History(int id, long ticketId, String staffId, String counterId, long serveTime, long doneTime){
        this.setId(id);
        this.setTicketId(ticketId);
        this.setCounterId(counterId);
        this.setStaffId(staffId);
        this.setServeTime(serveTime);
        this.setDoneTime(doneTime);
    }

    public int getId(){
        return this.id;
    }

    public long getTicketId(){
        return this.ticketId;
    }

    public String getStaffId(){
        return this.staffId;
    }

    public String getCounterId(){
        return this.counterId;
    }

    public long getServeTime(){
        return this.serveTime;
    }

    public long getDoneTime(){
        return this.doneTime;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setTicketId(long ticketId){
        this.ticketId = ticketId;
    }

    public void setStaffId(String staffId){
        this.staffId = staffId;
    }

    public void setCounterId(String counterId){
        this.counterId = counterId;
    }

    public void setServeTime(long serveTime){
        this.serveTime = serveTime;
    }

    public void setDoneTime(long doneTime){
        this.doneTime = doneTime;
    }
}
