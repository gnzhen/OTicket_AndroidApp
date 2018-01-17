package com.example.gd.oticket;

import java.io.Serializable;

/**
 * Created by GD on 1/13/2018.
 */

public class BranchService implements Serializable{
    private String id;
    private String branchId;
    private String serviceId;
    private int avgWaitTime;

    public BranchService(String id, String branchId, String serviceId){
        this.setId(id);
        this.setBranch(branchId);
        this.setService(serviceId);
    }

    public BranchService(String id, String branchId, String serviceId, int avgWaitTime){
        this.setId(id);
        this.setBranch(branchId);
        this.setService(serviceId);
        this.setAvgWaitTime(avgWaitTime);
    }

    public String getId(){
        return this.id;
    }

    public String getBranchId() {
        return this.branchId;
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public int getAvgWaitTime() {
        return this.avgWaitTime;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setBranch(String branchId){
        this.branchId = branchId;
    }

    public void setService(String servicId){
        this.serviceId = servicId;
    }

    public void setAvgWaitTime(int avgWaitTime){
        this.avgWaitTime = avgWaitTime;
    }
}
