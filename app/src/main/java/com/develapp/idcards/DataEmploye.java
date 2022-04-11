package com.develapp.idcards;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class DataEmploye implements Serializable {

    public String id,user_id,image,inst_name,employee_name,rel_name,phone,id_number,rel_type,parent_name,dob,
            aadhar,emp_code,admin_number,address,designation,blood_group;

    public DataEmploye(JSONObject jsonObject){
        try {
            this.id=jsonObject.getString("id");
            this.user_id=jsonObject.getString("user_id");
            this.image=jsonObject.getString("image");
            this.employee_name=jsonObject.getString("employee_name");
            this.rel_name=jsonObject.getString("rel_name");
            this.rel_type=jsonObject.getString("rel_type");
            this.phone=jsonObject.getString("phone");
            this.inst_name=jsonObject.getString("inst_name");
            this.blood_group=jsonObject.getString("blood_group");
            this.dob=jsonObject.getString("dob");
            this.emp_code=jsonObject.getString("emp_code");
            this.address=jsonObject.getString("address");
            this.designation=jsonObject.getString("designation");
            this.aadhar=jsonObject.getString("aadhar");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
