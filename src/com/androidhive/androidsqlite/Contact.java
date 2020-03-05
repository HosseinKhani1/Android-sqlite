package com.androidhive.androidsqlite;

import android.content.Context;

/**
 * Created by Arash on 05/11/2016.
 */

public class Contact {

    int id;
    String name;
    String phone;

    public Contact(int id, String name, String phone){
        this.id =id;
        this.name = name;
        this.phone = phone;
    }

    public Contact( String name, String phone){

        this.name = name;
        this.phone = phone;
    }

    public Contact()
    {

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
