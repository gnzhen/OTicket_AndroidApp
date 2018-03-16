package com.example.gd.oticket;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by GD on 1/13/2018.
 */

public class Branch implements Serializable{
    private String id;
    private String code;
    private String name;
    private String desc;
    private ArrayList<Service> services = new ArrayList<>();

    public Branch(){

    }

    public Branch(String id, String code, String name, String desc){
        this.setId(id);
        this.setCode(code);
        this.setName(name);
        this.setDesc(desc);
    }
//
//    public Branch(String id, String name, String desc, ArrayList<Service> services){
//        this.setId(id);
//        this.setName(name);
//        this.setDesc(desc);
//        this.setServices(services);
//    }

    public String getId(){
        return this.id;
    }

    public String getCode(){
        return this.code;
    }

    public String getName(){
        return this.name;
    }

    public String getDesc(){
        return this.desc;
    }

    public ArrayList<Service> getServices(){
        return this.services;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setCode(String code){
        this.code = code;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setServices(ArrayList<Service> services){
        this.services = services;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }
}
