package com.develapp.idcards;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class DataImages implements Serializable {

    public String id,user_id,image,member_name,rel_name,rel_type,phone,group_name,group_id,vo_name,village_name,
            designation,mandal_id,mandal_name,id_image;

    public DataImages(JSONObject jsonObject){
        try {
            this.id=jsonObject.getString("id");
            this.user_id=jsonObject.getString("user_id");
            this.image=jsonObject.getString("image");
            this.member_name=jsonObject.getString("member_name");
            this.group_name=jsonObject.getString("group_name");
            this.rel_name=jsonObject.getString("rel_name");
            this.rel_type=jsonObject.getString("rel_type");
            this.phone=jsonObject.getString("phone");
            this.group_id=jsonObject.getString("group_id");
            this.vo_name=jsonObject.getString("vo_name");
            this.village_name=jsonObject.getString("village_name");
            this.designation=jsonObject.getString("designation");
            this.mandal_id=jsonObject.getString("mandal_id");
            this.mandal_name=jsonObject.getString("mandal_name");
            this.id_image=jsonObject.getString("id_image");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
