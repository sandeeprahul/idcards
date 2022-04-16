package com.develapp.idcards;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class DataNregs implements Serializable {

    public String id, user_id, image, inst_name, ration_card_number, section, phone, village_name, member_name, parent_name, dob,
            aadhar, group_name,category, bank_name, bank_acc_number, job_card_number, rel_type,district,mandal_id,mandal_name;

    public DataNregs(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getString("id");
            this.user_id = jsonObject.getString("user_id");
            this.image = jsonObject.getString("image");
            this.member_name = jsonObject.getString("member_name");
            this.parent_name = jsonObject.getString("parent_name");
            this.job_card_number = jsonObject.getString("job_card_number");
            this.ration_card_number = jsonObject.getString("ration_card_number");
            this.phone = jsonObject.getString("phone");
            this.category = jsonObject.getString("category");
            this.group_name=jsonObject.getString("group_name");
            this.aadhar = jsonObject.getString("aadhar");
            this.bank_name = jsonObject.getString("bank_name");
            this.bank_acc_number = jsonObject.getString("bank_acc_number");
            this.village_name = jsonObject.getString("village_name");


            this.district=jsonObject.getString("district");
            this.mandal_id=jsonObject.getString("mandal_id");
            this.mandal_name=jsonObject.getString("mandal_name");

            this.aadhar = jsonObject.getString("aadhar");
            this.rel_type = jsonObject.getString("rel_type");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

        /*  "id": "3",
          "image": "http://idcards.yellowsoft.in/uploads\/shg_ids\/DSC_16500878869168.jpg",
          "user_id": "9168",
          "member_name": "test16Apr",
          "parent_name": "test",
          "rel_type": "S\/o",
          "phone": "5145545454",
          "category": "CatTest",
          "ration_card_number": "shshsg",
          "aadhar": "9494",
          "bank_name": "vzvz",
          "bank_acc_number": "vsvs",
          "job_card_number": "",
          "group_name": "test",
          "village_name": "tet",
          "district": "1",
          "mandal_id": "4",
          "mandal_name": "CHANDARLAPADU",
          "last_updated": "0000-00-00 00:00:00",
          "now": "2022-04-16 11:14:46",
          "status": "1",
          "id_image": "http:\/\/idcards.yellowsoft.in\/corecode\/gen_id.php?shg_id=3"
          */