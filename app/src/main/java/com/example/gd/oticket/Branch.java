package com.example.gd.oticket;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by GD on 1/13/2018.
 */

public class Branch implements Serializable{
    private String id;
    private String name;
    private ArrayList<Service> services;

    public Branch(){

    }

    public Branch(String id, String name){
        this.setId(id);
        this.setName(name);
    }

    public Branch(String id, String name, ArrayList<Service> services){
        this.setId(id);
        this.setName(name);
        this.setServices(services);
    }

    public String getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public ArrayList<Service> getServices(){
        return this.services;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setServices(ArrayList<Service> services){
        this.services = services;
    }
}
