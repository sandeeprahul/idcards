package com.develapp.idcards;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class DataBanners implements Serializable {

    public String id,title,banner_image,status,created,modified;
    public String image;

/*
    public DataBanners(int image){
        this.image=image;
    }
*/
    public DataBanners(JSONObject jsonObject){
        try {
            this.id=jsonObject.getString("id");
            this.title=jsonObject.getString("title");
            this.image=jsonObject.getString("image");
           /* this.status=jsonObject.getString("status");
            this.created=jsonObject.getString("created");
            this.modified=jsonObject.getString("modified");*/

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
