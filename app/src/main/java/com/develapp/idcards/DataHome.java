package com.develapp.idcards;

import java.io.Serializable;

public class DataHome implements Serializable {

    public String title,id;
    public DataHome(String title,String id){

        this.title = title;
        this.id = id;

    }
}
