package com.example.gd.oticket;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by GD on 1/13/2018.
 */

public class Counter implements Serializable{
    private String id;
    private String name;
    private String branchServiceId;
    private String staffId;

    public Counter(){

    }

    public Counter(String id, String name){
        this.setId(id);
        this.setName(name);
    }

    public Counter(String id, String name, String branchServiceId, String staffId){
        this.setId(id);
        this.setName(name);
        this.setStaffId(staffId);
    }

    public String getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getBranchServiceId(){
        return this.branchServiceId;
    }

    public String getStaffId(){
        return this.staffId;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setBranchServiceId(String branchServiceId){
        this.branchServiceId = branchServiceId;
    }

    public void setStaffId(String staffId){
        this.staffId = staffId;
    }

}
