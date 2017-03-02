package com.vnh.filmmarket.model;

/**
 * Created by HUYVINH on 16-Jan-17.
 */

public class Users {
    String name;
    String pass;

    public Users() {
    }

    public Users(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }


}
