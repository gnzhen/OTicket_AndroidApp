package com.example.gd.oticket;

import java.io.Serializable;

/**
 * Created by GD on 1/13/2018.
 */

public class User implements Serializable{
    private String id;
    private String username;
    private String name;

    public User(String id, String username, String name){
        this.setId(id);
        this.setUsername(username);
        this.setName(name);
    }

    public String getId(){
        return this.id;
    }

    public String getUsername(){
        return this.username;
    }

    public String getName(){
        return this.name;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setName(String name){
        this.name = name;
    }
}
