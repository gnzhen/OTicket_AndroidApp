package com.example.gd.oticket;

import java.io.Serializable;

/**
 * Created by GD on 1/13/2018.
 */

public class Staff implements Serializable{
    private String id;
    private String name;
    private String branchId;

    public Staff(String id, String name, String branchId){
        this.setId(id);
        this.setName(name);
        this.setBranchId(branchId);
    }

    public String getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getBranchId(){
        return this.branchId;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }
}
