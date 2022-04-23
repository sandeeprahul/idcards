package com.develapp.idcards;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.RestrictionsManager.RESULT_ERROR;

//change SHG to VOA
//Bookkeeper
public class RegisterActivity extends AppCompatActivity {
    public String mandalId="";
    public TextView mandal_tv;
    EditText name_edt,phone_edt,wspnumber_edt,email_edt,address_edt,password_edt,confirmpass_edt,usrname_edt,vo_edt,noof_cards_edt;
    EditText serach_edt_popup;
    RelativeLayout desg_rl,mandal_rl;
    AdapterMandals adapterMandals;
    ArrayList<DataMandals> dataMandals = new ArrayList<DataMandals>();
    TextView submit_tv;
    RadioButton sgh_rb,student_rb,employe_rb,nregs_rb;
    RadioGroup type_rg;
    ImageView back_img;
    LinearLayout mandals_popup_ll;
    RecyclerView mandals_rv;
    Bitmap bitmap;
    TextView close_tv_mandals_popup;
    String desgID="",mandal="";
    boolean shg=true,employee,student,nregs;
    String TAG = "RegisterActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_register);

        if (ActivityCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        //storage wirte perimission
        if (ActivityCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        noof_cards_edt = (EditText)findViewById(R.id.noof_cards_edt);
        serach_edt_popup = (EditText)findViewById(R.id.serach_edt_popup);
        close_tv_mandals_popup = (TextView)findViewById(R.id.close_tv_mandals_popup);
        mandal_tv = (TextView)findViewById(R.id.mandal_tv);
        mandals_rv = (RecyclerView) findViewById(R.id.mandals_rv);
        sgh_rb = (RadioButton)findViewById(R.id.sgh_rb);
        student_rb = (RadioButton)findViewById(R.id.student_rb);
        employe_rb = (RadioButton)findViewById(R.id.employe_rb);
        nregs_rb = (RadioButton)findViewById(R.id.nregs_rb);
        mandal_rl = (RelativeLayout)findViewById(R.id.mandal_rl);
        mandals_popup_ll = (LinearLayout)findViewById(R.id.mandals_popup_ll);
        type_rg = (RadioGroup) findViewById(R.id.type_rg);
        name_edt = (EditText)findViewById(R.id.name_edt);
        phone_edt = (EditText)findViewById(R.id.phone_edt);
        usrname_edt = (EditText)findViewById(R.id.usrname_edt);
        wspnumber_edt = (EditText)findViewById(R.id.wspnumber_edt);
        email_edt = (EditText)findViewById(R.id.email_edt);
        vo_edt= (EditText)findViewById(R.id.vo_edt);
        address_edt = (EditText)findViewById(R.id.address_edt);
        password_edt = (EditText)findViewById(R.id.password_edt);
        confirmpass_edt = (EditText)findViewById(R.id.confirmpass_edt);
        submit_tv = (TextView) findViewById(R.id.submit_tv);
        back_img = (ImageView) findViewById(R.id.back_img);
        confirmpass_edt.setVisibility(View.GONE);
        adapterMandals = new AdapterMandals(RegisterActivity.this,dataMandals,0);
        //initial check of shg
        if (sgh_rb.isChecked()){
            mandal_rl.setVisibility(View.VISIBLE);
            vo_edt.setVisibility(View.VISIBLE);
            noof_cards_edt.setVisibility(View.VISIBLE);
            noof_cards_edt.setHint("Total SHG Members");

            shg=true;
            student = false;
            employee= false;
            nregs= false;
        }
        else if(student_rb.isChecked()){
            mandal_rl.setVisibility(View.GONE);
            vo_edt.setVisibility(View.GONE);
            noof_cards_edt.setVisibility(View.VISIBLE);

            noof_cards_edt.setHint("Total Students");
            shg = false;
            student = true;
            employee= false;
            nregs= false;
        }
        else if(employe_rb.isChecked()){
            mandal_rl.setVisibility(View.GONE);
            vo_edt.setVisibility(View.GONE);
            noof_cards_edt.setVisibility(View.VISIBLE);

            noof_cards_edt.setHint("Total Staff");
            shg = false;
            employee = true;
            student = false;
            nregs = false;
        }
        else if(nregs_rb.isChecked()){
            //d
            mandal_rl.setVisibility(View.VISIBLE);
            vo_edt.setVisibility(View.GONE);
            noof_cards_edt.setVisibility(View.VISIBLE);
            noof_cards_edt.setHint("Total NREGS Members");

            shg=false;
            student = false;
            employee= false;
            nregs= true;

        }


        final AdapterMandals adapterMandals = new AdapterMandals(RegisterActivity.this,dataMandals,0);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RegisterActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mandals_rv.setLayoutManager(linearLayoutManager);
        mandals_rv.setAdapter(adapterMandals);
        close_tv_mandals_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mandals_popup_ll.setVisibility(View.GONE);
                serach_edt_popup.getText().clear();
            }
        });
        mandals_popup_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        type_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.sgh_rb) {
                    mandal_rl.setVisibility(View.VISIBLE);
                    vo_edt.setVisibility(View.VISIBLE);
                    noof_cards_edt.setVisibility(View.VISIBLE);
                    noof_cards_edt.setHint("Total SHG Members");

                    shg = true;
                    employee = false;
                    student = false;
                } else if (checkedId == R.id.student_rb){
                    mandal_rl.setVisibility(View.GONE);
                    vo_edt.setVisibility(View.GONE);
                    noof_cards_edt.setVisibility(View.VISIBLE);
                    noof_cards_edt.setHint("Total Students");

                    shg = false;
                    employee = false;
                    student = true;
                }
                else if (checkedId == R.id.employe_rb){
                    mandal_rl.setVisibility(View.GONE);
                    vo_edt.setVisibility(View.GONE);
                    noof_cards_edt.setVisibility(View.VISIBLE);
                    noof_cards_edt.setHint("Total Staff");

                    shg = false;
                    employee = true;
                    student = false;
                }
                else if(nregs_rb.isChecked()){
                    //d
                    mandal_rl.setVisibility(View.VISIBLE);
                    vo_edt.setVisibility(View.GONE);
                    noof_cards_edt.setVisibility(View.VISIBLE);
                    noof_cards_edt.setHint("Total NREGS Members");

                    shg=false;
                    student = false;
                    employee= false;
                    nregs= true;

                }
            }
        });
        submit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name_edt.getText().toString().equals("")){
                    showAlert("Please enter Name");
                    //Toast.makeText(RegisterActivity.this,"Please enter Name",Toast.LENGTH_SHORT).show();
                }
                else if (phone_edt.getText().toString().length()!=10){
                    showAlert("Phone number must be 10 numbers");
                   // Toast.makeText(RegisterActivity.this,"Please enter a Valid Email",Toast.LENGTH_SHORT).show();
                }

          /*      else if (usrname_edt.getText().toString().equals("")){
                    showAlert("Please enter User Name");

//                    Toast.makeText(RegisterActivity.this,"Please enter UserName",Toast.LENGTH_SHORT).show();
                }
                else if (password_edt.getText().toString().equals("")||password_edt.getText().toString().length()<6){
                    showAlert("Password length must be atlest 6 characters");

                   // Toast.makeText(RegisterActivity.this,"Password length must be atlest 6 characters",Toast.LENGTH_SHORT).show();
                }*/
                else {
                    if (shg){
                        if (mandalId.equals("")){
                            showAlert("Please Select Mandal");
                           // Toast.makeText(RegisterActivity.this,"Please Select Mandal",Toast.LENGTH_SHORT).show();
                        }else if(vo_edt.getText().toString().equals("")){
                            showAlert("Please enter VO Name");
                        }
                        else  if(noof_cards_edt.getText().toString().equals("")){
                            showAlert("Please enter Total SHG Members");
                            //Total SHG Members
                        }
                        else {
                            addSubscription();
                        }
                    }
                    else if (nregs){
                        if (mandalId.equals("")){
                            showAlert("Please Select Mandal");
                        }
                        else  if(noof_cards_edt.getText().toString().equals("")){
                            showAlert("Please enter Total NREGS Members");
                        }
                        else {
                            addSubscription();
                        }
                    }
                    else {
                        addSubscription();
                    }
                }
               /* else if (mandalId.equals("")){
                    Toast.makeText(RegisterActivity.this,"Password length must be atlest 6 characters",Toast.LENGTH_SHORT).show();

                }*/

            }
        });

        serach_edt_popup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                adapterMandals.getFilter().filter(s);
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mandal_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mandals_popup_ll.setVisibility(View.VISIBLE);
            }
        });

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getMandals();
    }

    public void filter(String text){
        ArrayList<DataMandals> temp = new ArrayList();
        for(DataMandals d: dataMandals){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
           /* if(d.title.contains(text)){

                temp.add(d);
            }*/

            if (d.title.toLowerCase().contains(text)) {
                temp.add(d);
            }
            else if (d.title.contains(text)){
                temp.add(d);
            }
            else if (d.title.toUpperCase().contains(text)){
                temp.add(d);
            }
        }
        //update recyclerview
        adapterMandals.updateList(temp);
    }



    public void  getMandals() {
        dataMandals.clear();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String Url = Session.BASE_URL + "mandals";
        Log.e("url1",Url);


        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.e("onResponse: ", response.toString());

                try {
                    JSONArray jsonArray  = new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++){
                        DataMandals temp = new DataMandals(jsonArray.getJSONObject(i));
                        dataMandals.add(temp);
                    }
                    adapterMandals = new AdapterMandals(RegisterActivity.this,dataMandals,0);
                    adapterMandals.notifyDataSetChanged();
                    mandals_rv.setAdapter(adapterMandals);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
    public void  addSubscription() {


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String Url = Session.BASE_URL + "register";//?name="+name_edt.getText().toString()+"&username="+usrname_edt.getText().toString()+
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

                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                       // finish();
                        Toast.makeText(RegisterActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                    }
                    else {
                       // message
                        Toast.makeText(RegisterActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();

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

                params.put("name", name_edt.getText().toString());//6154043824,school_id, pickup_address pickup_latitude pickup_longitude subscription_duration amount_paid
//                params.put("username", usrname_edt.getText().toString());//271012306
//                params.put("password", password_edt.getText().toString());
                params.put("phone", phone_edt.getText().toString());
                params.put("email", email_edt.getText().toString());
                params.put("whatsapp", wspnumber_edt.getText().toString());
                if (shg){
                    params.put("type", "0");
                    params.put("mandal",mandalId);
                    params.put("vo_name", vo_edt.getText().toString());
                    params.put("ids_cnt",noof_cards_edt.getText().toString());
                }
                else if (student){
                    params.put("type", "1");
//                    params.put("mandal","0");
//                    params.put("vo_name","");
                    params.put("ids_cnt",noof_cards_edt.getText().toString());
                }else if (employee){
                    params.put("type", "2");
//                    params.put("mandal","0");
//                    params.put("vo_name","");
                    params.put("ids_cnt",noof_cards_edt.getText().toString());
                }
                else if(nregs){
                    params.put("type", "3");
                    params.put("mandal",mandalId);
                    params.put("ids_cnt",noof_cards_edt.getText().toString());
                }
                params.put("address", address_edt.getText().toString());
                Log.e("params",params.toString());
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

    public void showAlert(String msg){
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(RegisterActivity.this);

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
                dialog.dismiss();
            }
        });
        final AlertDialog alertDialog = builder.create();


        alertDialog.show();
    }
}