package com.develapp.idcards;

import java.io.Serializable;

public class DataRelations implements Serializable {

    public String title,id;
    public DataRelations(String title, String id){

        this.title = title;
        this.id = id;

    }
}
