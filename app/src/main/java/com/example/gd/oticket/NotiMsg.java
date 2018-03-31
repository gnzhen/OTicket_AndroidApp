package com.example.gd.oticket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by GD on 3/31/2018.
 */

public class NotiMsg implements Serializable{

    private String titleLg;
    private String titleSm;
    private String ticketId;
    private String ticketNo;
    private String waitTime;
    private String branchName;
    private String serviceName;

    public NotiMsg(String titleLg, String titleSm, String ticketId, String ticketNo,
                   String waitTime, String branchName, String serviceName){
        this.titleLg = titleLg;
        this.titleSm = titleSm;
        this.ticketId = ticketId;
        this.ticketNo = ticketNo;
        this.waitTime = waitTime;
        this.branchName = branchName;
        this.serviceName = serviceName;
    }

    public String getTitleLg(){
        return this.titleLg;
    }

    public String getTitleSm(){
        return this.titleSm;
    }

    public String getTicketId(){
        return this.ticketId;
    }

    public String getTicketNo(){
        return this.ticketNo;
    }

    public String getBranchName(){
        return this.branchName;
    }

    public String getServiceName(){
        return this.serviceName;
    }

    public String getWaitTime(){
        return this.waitTime;
    }

}
