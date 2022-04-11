package com.develapp.idcards;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class DataDesgnations implements Serializable {

    public String title,id;
    public DataDesgnations(String title, String id){
        this.title = title;
        this.id = id;
    }
    public DataDesgnations(JSONObject jsonObject){
        try {
            this.id = jsonObject.getString("id");
            this.title = jsonObject.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}

