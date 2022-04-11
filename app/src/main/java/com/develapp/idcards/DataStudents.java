package com.develapp.idcards;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class DataStudents implements Serializable {

    public String id,user_id,image,inst_name,class_,section,phone,id_number,group_id,parent_name,dob,
            aadhar,student_name,admin_number,address,transport,blood_group,rel_type;

    public DataStudents(JSONObject jsonObject){
        try {
            this.id=jsonObject.getString("id");
            this.user_id=jsonObject.getString("user_id");
            this.image=jsonObject.getString("image");
            this.class_=jsonObject.getString("class");
            this.section=jsonObject.getString("section");

            this.student_name=jsonObject.getString("student_name");
            this.parent_name=jsonObject.getString("parent_name");
            this.phone=jsonObject.getString("phone");
//            this.group_name=jsonObject.getString("group_name");
            this.inst_name=jsonObject.getString("inst_name");
            this.admin_number=jsonObject.getString("admin_number");
            this.address=jsonObject.getString("address");
            this.transport=jsonObject.getString("transport");
            this.blood_group=jsonObject.getString("blood_group");
            this.id_number=jsonObject.getString("id_number");


//            this.group_id=jsonObject.getString("group_id");
            this.dob=jsonObject.getString("dob");

            this.aadhar=jsonObject.getString("aadhar");
            this.rel_type=jsonObject.getString("rel_type");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
