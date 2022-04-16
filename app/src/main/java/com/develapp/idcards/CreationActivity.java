package com.develapp.idcards;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreationActivity extends AppCompatActivity {
    EditText name_edt,phone_edt,wspnumber_edt,email_edt,address_edt,password_edt,confirmpass_edt,usrname_edt,vo_edt,noof_cards_edt;
    EditText edt_popup;
    RelativeLayout desg_rl,mandal_rl,inst_rl;
    TextView submit_tv,submit_tv_popup,inst_tv;
    RelativeLayout relation_rl,classes_rl,sections_rl,designations_rl;
    Bitmap bitmap;
    int popupType;

    ArrayList<String> relationItems = new ArrayList<String>();
    ArrayList<String> classItems = new ArrayList<String>();
    ArrayList<String> sectionItems = new ArrayList<String>();
    ArrayList<String> desgItems = new ArrayList<String>();
    ArrayList<String> instItems = new ArrayList<String>();
    String desgID="",mandal="";
    ImageView close_img_popup,back_img;
    RecyclerView relation_rv,class_rv,section_rv,desg_rv,inst_rv;
    boolean relat,clas,sec,desgg,inst;



    LinearLayout popup_ll;
    ImageView capture_img,gallery_img,usr_img;
    AdapterAddRelations adapterAddRelations;
    AdapterAddClasses adapterAddClasses;
    AdapterAddSections adapterAddSections;
    AdapterAddDesgnations adapterAddDesgnations;
    AdapterAddInstutions adapterAddInstutions;
    String TAG = "RegisterActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_creation);

        if (ActivityCompat.checkSelfPermission(CreationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CreationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CreationActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        //storage wirte perimission
        if (ActivityCompat.checkSelfPermission(CreationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CreationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CreationActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        inst_rv = (RecyclerView)findViewById(R.id.inst_rv);
        inst_tv = (TextView)findViewById(R.id.inst_tv);
        inst_rl = (RelativeLayout)findViewById(R.id.inst_rl);
        back_img = (ImageView)findViewById(R.id.back_img);
        close_img_popup = (ImageView)findViewById(R.id.close_img_popup);
        relation_rv = (RecyclerView) findViewById(R.id.relation_rv);
        class_rv = (RecyclerView) findViewById(R.id.class_rv);
        section_rv = (RecyclerView) findViewById(R.id.section_rv);
        desg_rv = (RecyclerView) findViewById(R.id.desg_rv);
        edt_popup = (EditText)findViewById(R.id.edt_popup);
        submit_tv_popup = (TextView)findViewById(R.id.submit_tv_popup);
        popup_ll = (LinearLayout)findViewById(R.id.popup_ll);
        designations_rl = (RelativeLayout) findViewById(R.id.designations_rl);
        sections_rl = (RelativeLayout) findViewById(R.id.sections_rl);
        classes_rl = (RelativeLayout) findViewById(R.id.classes_rl);
        relation_rl = (RelativeLayout) findViewById(R.id.relation_rl);
        name_edt = (EditText)findViewById(R.id.name_edt);
        noof_cards_edt = (EditText)findViewById(R.id.noof_cards_edt);
        phone_edt = (EditText)findViewById(R.id.phone_edt);
        usrname_edt = (EditText)findViewById(R.id.usrname_edt);
        wspnumber_edt = (EditText)findViewById(R.id.wspnumber_edt);
        email_edt = (EditText)findViewById(R.id.email_edt);
        vo_edt = (EditText)findViewById(R.id.vo_edt);
        address_edt = (EditText)findViewById(R.id.address_edt);
        password_edt = (EditText)findViewById(R.id.password_edt);
        password_edt.setVisibility(View.GONE);
        confirmpass_edt = (EditText)findViewById(R.id.confirmpass_edt);

        edt_popup.setImeOptions(EditorInfo.IME_ACTION_DONE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CreationActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(CreationActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(CreationActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(CreationActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(CreationActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        relation_rv.setLayoutManager(linearLayoutManager);
        desg_rv.setLayoutManager(linearLayoutManager1);
        section_rv.setLayoutManager(linearLayoutManager2);
        class_rv.setLayoutManager(linearLayoutManager3);
        inst_rv.setLayoutManager(linearLayoutManager4);

        adapterAddRelations = new AdapterAddRelations(CreationActivity.this, relationItems);
        adapterAddClasses = new AdapterAddClasses(CreationActivity.this, classItems);
        adapterAddSections = new AdapterAddSections(CreationActivity.this, sectionItems);
        adapterAddDesgnations = new AdapterAddDesgnations(CreationActivity.this, desgItems);
        adapterAddInstutions = new AdapterAddInstutions(CreationActivity.this, instItems);
        submit_tv = (TextView) findViewById(R.id.submit_tv);


        if(Session.getType(CreationActivity.this).equals("0")){
            vo_edt.setVisibility(View.VISIBLE);
            noof_cards_edt.setVisibility(View.VISIBLE);
        }

        else if(Session.getType(CreationActivity.this).equals("2")){
            vo_edt.setVisibility(View.GONE);
            noof_cards_edt.setVisibility(View.GONE);
            classes_rl.setVisibility(View.GONE);
            sections_rl.setVisibility(View.GONE);
            inst_tv.setText("Company Name");
        }
        else if(Session.getType(CreationActivity.this).equals("3")){
            vo_edt.setVisibility(View.GONE);
            classes_rl.setVisibility(View.GONE);
            sections_rl.setVisibility(View.GONE);
            inst_rl.setVisibility(View.GONE);
            designations_rl.setVisibility(View.GONE);

        }



        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        popup_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        close_img_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_ll.setVisibility(View.GONE);
            }
        });
        submit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phone_edt.getText().toString().length()!=10){
                    AlertDialog.Builder builder
                            = new AlertDialog
                            .Builder(CreationActivity.this);

                    builder.setTitle("Alert !");
                    builder.setMessage("Phone number must be 10 numbers");
                    // Set Cancelable false
                    // for when the user clicks on the outside
                    // the Dialog Box then it will remain show
                    builder.setCancelable(true);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                         //   updateDetails();
                            dialog.dismiss();
                        }
                    });
                    final AlertDialog alertDialog = builder.create();


                    alertDialog.show();
                }
                else {
                    showAlert("Update Details");
                }

            }
        });
        submit_tv_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e( "onClick: ",edt_popup.getText().toString() );
                Log.e( "onClick: ",""+popupType);

                if (relat){
                    popup_ll.setVisibility(View.GONE);
                    relationItems.add(edt_popup.getText().toString());

//                    adapterAddRelations.notifyItemInserted(relationItems.size() - 1);
                    adapterAddRelations.notifyDataSetChanged();
                    edt_popup.setHint("");
                    edt_popup.setText("");
                    for (int i=0;i<relationItems.size();i++) {
                        Log.e("onClick: ", relationItems.get(i));
                    }

                }else if (clas){
                    popup_ll.setVisibility(View.GONE);
                    classItems.add(edt_popup.getText().toString());

                  //  adapterAddClasses.notifyItemInserted(classItems.size() - 1);
                    adapterAddClasses.notifyDataSetChanged();
                    edt_popup.setHint("");
                    edt_popup.setText("");
                    for (int i=0;i<classItems.size();i++) {
                        Log.e("onClick: ", classItems.get(i));
                    }
                }
                else if (sec){
                    popup_ll.setVisibility(View.GONE);
                    sectionItems.add(edt_popup.getText().toString());

                  //  adapterAddSections.notifyItemInserted(sectionItems.size() - 1);
                    adapterAddSections.notifyDataSetChanged();
                    edt_popup.setHint("");
                    edt_popup.setText("");
                    for (int i=0;i<sectionItems.size();i++) {
                        Log.e("onClick: ", sectionItems.get(i));
                    }
                }
                else if (desgg){
                    popup_ll.setVisibility(View.GONE);
                    desgItems.add(edt_popup.getText().toString());

                  //  adapterAddDesgnations.notifyItemInserted(desgItems.size() - 1);
                    adapterAddDesgnations.notifyDataSetChanged();
                    edt_popup.setHint("");
                    edt_popup.setText("");
                    for (int i=0;i<desgItems.size();i++) {
                        Log.e("onClick: ", desgItems.get(i));
                    }
                }
                else if (inst){
                    popup_ll.setVisibility(View.GONE);
                    instItems.add(edt_popup.getText().toString());

                    //adapterAddInstutions.notifyItemInserted(instItems.size() - 1);
                    adapterAddInstutions.notifyDataSetChanged();
                    edt_popup.setHint("");
                    edt_popup.setText("");
                    for (int i=0;i<instItems.size();i++) {
                        Log.e("onClick: ", instItems.get(i));
                    }
                }


            }
        });
        relation_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_ll.setVisibility(View.VISIBLE);
                edt_popup.setHint("Enter Relation Name");
                popupType = 0;
                relat = true;
                clas = false;
                sec = false;
                inst =false;
                desgg = false;
              //  getDetails();
            }
        });
        classes_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_ll.setVisibility(View.VISIBLE);
                edt_popup.setHint("Enter Classes");
                relat = false;
                clas = true;
                sec = false;
                desgg = false;
                inst =false;
                popupType = 1;

            }
        });
        sections_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_ll.setVisibility(View.VISIBLE);
                edt_popup.setHint("Enter Selection");
                sec = true;
                relat = false;
                clas = false;
                desgg = false;
                inst =false;
                popupType = 2;
            }
        });
        designations_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_ll.setVisibility(View.VISIBLE);
                edt_popup.setHint("Enter Designation");
                desgg = true;
                relat = false;
                clas = false;
                sec = false;
                inst =false;
                popupType = 3;
            }
        });
        inst_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_ll.setVisibility(View.VISIBLE);
                if(Session.getType(CreationActivity.this).equals("0")){
                    edt_popup.setHint("Enter Institution Name");
                }else if(Session.getType(CreationActivity.this).equals("2")){
                    edt_popup.setHint("Enter Company Name");
                }
                else {
                    edt_popup.setHint("Enter Institution Name");

                }
                desgg = false;
                relat = false;
                clas = false;
                sec = false;
                inst =true;
                popupType = 4;
            }
        });


        getDetails();
    }
    public void getDetails()  {
        Log.e( "getDetails: ", "true");
        Log.e( "getDetails: ", Session.getMemberJSON(CreationActivity.this));
        try {
            //dataMandals.clear();
            JSONArray jsonArrar = new JSONArray(Session.getMemberJSON(CreationActivity.this));
            JSONObject jsonObject = jsonArrar.getJSONObject(0);
            Log.e("getDetails: ", jsonObject.toString());
            name_edt.setText(jsonObject.getString("name"));
            usrname_edt.setText(jsonObject.getString("username"));
            email_edt.setText(jsonObject.getString("email"));
            phone_edt.setText(jsonObject.getString("phone"));
            wspnumber_edt.setText(jsonObject.getString("whatsapp"));
            vo_edt.setText(jsonObject.getString("vo_name"));
            address_edt.setText(jsonObject.getString("address"));
            noof_cards_edt.setText(jsonObject.getString("ids_cnt"));
            //name_edt.setText(jsonObject.getString("name"));

//            ArrayList<String> temp = new ArrayList<String>();

            for (int i = 0; i < jsonObject.getJSONArray("relations").length(); i++) {
                relationItems.add(jsonObject.getJSONArray("relations").get(i).toString());
            }
             adapterAddRelations = new AdapterAddRelations(CreationActivity.this, relationItems);
            adapterAddRelations.notifyDataSetChanged();
            relation_rv.setAdapter(adapterAddRelations);


            for (int i = 0; i < jsonObject.getJSONArray("classes").length(); i++) {
                classItems.add(jsonObject.getJSONArray("classes").get(i).toString());
            }
            adapterAddClasses = new AdapterAddClasses(CreationActivity.this, classItems);
            adapterAddClasses.notifyDataSetChanged();
            class_rv.setAdapter(adapterAddClasses);


            for (int i = 0; i < jsonObject.getJSONArray("sections").length(); i++) {
                sectionItems.add(jsonObject.getJSONArray("sections").get(i).toString());
            }
            adapterAddSections = new AdapterAddSections(CreationActivity.this, sectionItems);
            adapterAddSections.notifyDataSetChanged();
            section_rv.setAdapter(adapterAddSections);


            for (int i = 0; i < jsonObject.getJSONArray("designations").length(); i++) {
                desgItems.add(jsonObject.getJSONArray("designations").get(i).toString());
            }
            adapterAddDesgnations = new AdapterAddDesgnations(CreationActivity.this, desgItems);
            adapterAddDesgnations.notifyDataSetChanged();
            desg_rv.setAdapter(adapterAddDesgnations);


            for (int i = 0; i < jsonObject.getJSONArray("inst_names").length(); i++) {
                instItems.add(jsonObject.getJSONArray("inst_names").get(i).toString());
            }
            adapterAddInstutions = new AdapterAddInstutions(CreationActivity.this, instItems);
            adapterAddInstutions.notifyDataSetChanged();
            inst_rv.setAdapter(adapterAddInstutions);
        }
        catch (JSONException j){
            j.printStackTrace();
            Log.e("jsonExp",j.getMessage());
        }
    }

    public void showAlert(String msg){
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(CreationActivity.this);

        builder.setTitle("Alert !");
        builder.setMessage(msg);
        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                updateDetails();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        final AlertDialog alertDialog = builder.create();


        alertDialog.show();
    }

    public void  updateDetails() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        //String Url = Session.BASE_URL + "memberupdate";
        String Url = Session.BASE_URL + "update_profile";
        //?name="+name_edt.getText().toString()+"&username="+usrname_edt.getText().toString()+
                //"&password="+ password_edt.getText().toString()+
              //  "&phone="+phone_edt.getText().toString()+
              //  "&email="+email_edt.getText().toString()+
              //  "&whatsapp="+wspnumber_edt.getText().toString()+
               // "&address="+address_edt.getText().toString();
        Log.e("url1",Url);
        // double lat = mlatLng.latitude;
        // double lng = mlatLng.longitude;

            //final JSONObject jsonBody = new JSONObject();
            //jsonBody.put("parent_id", Session.getUserid(AddSubscriptionActivity.this));
        /*jsonBody.put("name", name_edt.getText().toString());//6154043824,school_id,price_id,kid_name kid_age kid_class pickup_address pickup_latitude pickup_longitude subscription_duration amount_paid

    jsonBody.put("username", usrname_edt.getText().toString());//271012306
        jsonBody.put("password", password_edt.getText().toString());
        jsonBody.put("phone", phone_edt.getText().toString());
        jsonBody.put("email", email_edt.getText().toString());
        jsonBody.put("whatsapp", wspnumber_edt.getText().toString());
        jsonBody.put("address", address_edt.getText().toString());

        Log.e("params",jsonBody.toString());
        final String mRequestBody = jsonBody.toString();*/

        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.e("onResponse: ", response.toString());
                //{ "status": "Success", "member_id": "2", "message": "Registered Successfully" }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("Success")){
                        showSuccessAlert(jsonObject.getString("message"));

                       // Intent intent = new Intent(CreationActivity.this,LoginActivity.class);
                      //  startActivity(intent);
                       // finish();
                        Toast.makeText(CreationActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //showAlert(String.valueOf(response));
                /*try {
                    Log.e("onResponse: ", response.toString());
                 *//*   if (response.getString("status").equals("1")){

                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else {

                    }*//*
                    //showAlert(""+response.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.e("resLoginError", error.toString());
                VolleyLog.e("Error: ", error.getMessage());
               // showAlert(error.getMessage().toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();


               // JSONObject jsonObject = new JSONObject();

                params.put("member_id",Session.getUserid(CreationActivity.this));
                params.put("name", name_edt.getText().toString());//6154043824,school_id, pickup_address pickup_latitude pickup_longitude subscription_duration amount_paid
                params.put("username", usrname_edt.getText().toString());//271012306
                // jsonObject.put("password", password_edt.getText().toString());
                params.put("email", email_edt.getText().toString());
                params.put("phone", phone_edt.getText().toString());
                params.put("whatsapp", wspnumber_edt.getText().toString());

                if(Session.getType(CreationActivity.this).equals("0")){
                    params.put("vo_name", vo_edt.getText().toString());
                    params.put("ids_cnt", noof_cards_edt.getText().toString());
                }else{
                    params.put("vo_name", "");
                    params.put("ids_cnt", noof_cards_edt.getText().toString());
                }

                params.put("address", address_edt.getText().toString());
                String relationsS="";
                for (int i  = 0;i<relationItems.size();i++){
                    if(relationsS.equals("")){
                        relationsS = relationItems.get(i);
                    }else {
                        relationsS = relationsS+","+relationItems.get(i);
                    }
                }
                String classess="";
                for (int i  = 0;i<classItems.size();i++){
                    if(classess.equals("")){
                        classess = classItems.get(i)+"";

                    }else {
                        classess = classess+","+classItems.get(i);
                    }
                }
                String sectionS="";
                for (int i  = 0;i<sectionItems.size();i++){
                    if(sectionS.equals("")){
                        sectionS = sectionItems.get(i)+"";

                    }else {
                        sectionS = sectionS+","+sectionItems.get(i);
                    }
                }
                String desgS="";
                for (int i  = 0;i<desgItems.size();i++){
                    //desgS = desgItems.get(i)+"";
                    if(desgS.equals("")){
                        desgS = desgItems.get(i)+"";

                    }else {
                        desgS = desgS+","+desgItems.get(i);
                    }
                }
                String instNames="";
                for (int i  = 0;i<instItems.size();i++){
                    //instNames = instItems.get(i)+"";
                    if(instNames.equals("")){
                        instNames = instItems.get(i)+"";

                    }else {
                        instNames = instNames+","+instItems.get(i);
                    }
                }
                params.put("relations",relationsS);
                params.put("classes",classess);
                params.put("sections",sectionS);
                params.put("designations",desgS);
                params.put("inst_names",instNames);

                JSONArray jsonArray = new JSONArray();

               // jsonArray.put(jsonObject);
               // params.put("memberupdate",jsonObject.toString());//6154043824,school_id, pickup_address pickup_latitude pickup_longitude subscription_duration amount_paid
                Log.e( "getParams: ", params.toString());
                return params;
            }

          /*  @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
            *//*    headers.put("name", name_edt.getText().toString());//6154043824,school_id,price_id,kid_name kid_age kid_class pickup_address pickup_latitude pickup_longitude subscription_duration amount_paid
                headers.put("username", usrname_edt.getText().toString());//271012306
                headers.put("password", password_edt.getText().toString());
                headers.put("phone", phone_edt.getText().toString());
                headers.put("email", email_edt.getText().toString());
                headers.put("whatsapp", wspnumber_edt.getText().toString());
                headers.put("address", address_edt.getText().toString());*//*
                return headers;
            }*/


          /*  @Override
            public byte[] getBody() {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            mRequestBody, "utf-8");
                    return null;
                }
            }*/


        };

        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);

    }
    public void showSuccessAlert(final String title){
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.success_popup, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        deleteDialog.setView(deleteDialogView);
//        TextView text = (TextView) deleteDialog.findViewById(R.id.success_title_tv);
//        assert text != null;
//        text.setText(title);
//        Button btn = (Button)deleteDialog.findViewById(R.id.ok_btn);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                deleteDialog.dismiss();
//                finish();
//            }
//        });
        deleteDialogView.findViewById(R.id.ok_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMemberDetails();
                deleteDialog.dismiss();
            }
        });


        deleteDialog.show();

    }
    public void  getMemberDetails() {


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String Url = Session.BASE_URL + "members/"+Session.getUserid(CreationActivity.this);
        Log.e("url1",Url);


        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.e("onResponse: ", response.toString());
                Session.setMemberJSON(CreationActivity.this,response);
                finish();
             /*   Session.setMemberJSON(SHGFormActivity.this,response);
                Intent intent = new Intent(SHGFormActivity.this,HomeActivity.class);
                startActivity(intent);*/
              /*  if (!Session.getUserid(SplashScreen.this).equals("-1")){


                }
                else {
                    Intent intent = new Intent(SplashScreen.this,WelcomePageActivity.class);
                    startActivity(intent);
                }*/
                // finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.e("resLoginError", error.toString());
                VolleyLog.e("Error: ", error.getMessage());
                // showAlert(error.getMessage().toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //  params.put("", "1");
         /*       params.put("name", name_edt.getText().toString());//6154043824,school_id, pickup_address pickup_latitude pickup_longitude subscription_duration amount_paid
                params.put("username", usrname_edt.getText().toString());//271012306
                params.put("password", password_edt.getText().toString());
                params.put("phone", phone_edt.getText().toString());
                params.put("email", email_edt.getText().toString());
                params.put("whatsapp", wspnumber_edt.getText().toString());
                params.put("address", address_edt.getText().toString());*/
                return params;
            }

        };

        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);

    }
}