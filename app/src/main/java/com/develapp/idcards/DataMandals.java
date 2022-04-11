package com.develapp.idcards;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class DataMandals implements Serializable {

    public String id,title,image,status,sorting,created,modified;

    public DataMandals(JSONObject jsonObject){
        try {
            this.id=jsonObject.getString("id");
            this.title=jsonObject.getString("title");
            this.image=jsonObject.getString("image");
            this.sorting=jsonObject.getString("sorting");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
