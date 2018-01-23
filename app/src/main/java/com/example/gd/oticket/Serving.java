package com.example.gd.oticket;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by GD on 1/13/2018.
 */

public class Serving implements Serializable{
    private int id;
    private String staffId;
    private int ticketId;
    private long serveTime;
    private String counterId;

    public Serving(int id, String staffId, int ticketId, long serveTime, String counterId){
        this.setId(id);
        this.setStaffId(staffId);
        this.setTicketId(ticketId);
        this.setServeTime(serveTime);
        this.setCounterId(counterId);
    }

    public int getId(){
        return this.id;
    }

    public String getStaffId() {
        return this.staffId;
    }

    public int getTicketId() {
        return this.ticketId;
    }

    public String getCounterId(){
        return this.counterId;
    }

    public long getServeTime(){
        return this.serveTime;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setStaffId(String staffId){
        this.staffId = staffId;
    }

    public void setTicketId(int ticketId){
        this.ticketId = ticketId;
    }

    public void setServeTime(long serveTime){
        this.serveTime = serveTime;
    }

    public void setCounterId(String counterId){
        this.counterId = counterId;
    }

}
