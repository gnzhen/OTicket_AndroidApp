package com.example.gd.oticket;

import java.io.Serializable;

/**
 * Created by GD on 1/13/2018.
 */

public class Service implements Serializable{
    private String id;
    private String code;
    private String name;

    public Service(){

    }

    public Service(String id, String code, String name){
        this.setId(id);
        this.setCode(code);
        this.setName(name);
    }

    public String getId(){
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public void setCode(String code){
        this.code = code;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

}
