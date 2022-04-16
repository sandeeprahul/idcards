package com.develapp.idcards;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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

public class IDListActivity extends AppCompatActivity {

    ImageView back_img;
    TextView no_data_tv;
    RecyclerView todays_rv;
    AdapterImages adapterImages;
    AdapterTodayList adapterTodayList;
    AdapterTodayListStudents adapterTodayListStudents;
    AdapterTodayListEmploye adapterTodayListEmploye;
    AdapterTodayListNregs adapterTodayListNregs;
//    AdapterTodayList adapterTodayList;
    ArrayList<DataImages> dataImages = new ArrayList<DataImages>();
    ArrayList<DataStudents> dataStudents = new ArrayList<DataStudents>();
    ArrayList<DataEmploye> dataEmployes = new ArrayList<DataEmploye>();
    ArrayList<DataNregs> dataNregs = new ArrayList<DataNregs>();
//    ArrayList<DataImages> dataImages = new ArrayList<DataImages>();
    LinearLayoutManager gl;
    int lastItem = 0;
    int tem = 0;
    private int preLast;
    int firstVisibleItem, visibleItemCount = 0, totalItemCount;
    int page = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idslist);

        back_img = (ImageView)findViewById(R.id.back_img);
        no_data_tv = (TextView)findViewById(R.id.no_data_tv);
        todays_rv = (RecyclerView)findViewById(R.id.todays_rv);

        LinearLayoutManager linearLayoutManager1  = new LinearLayoutManager(IDListActivity.this);
        linearLayoutManager1.setOrientation(RecyclerView.VERTICAL);
        gl  = new LinearLayoutManager(IDListActivity.this);
        gl.setOrientation(RecyclerView.VERTICAL);
        todays_rv.setLayoutManager(gl);

        if (Session.getType(IDListActivity.this).equals("0")){
            getAllDataList("shg_ids");
        }
        else if (Session.getType(IDListActivity.this).equals("1")){
            getAllDataList("student_ids");
        }
        else if (Session.getType(IDListActivity.this).equals("2")){
            getAllDataList("employee_ids");
        }
        else if (Session.getType(IDListActivity.this).equals("3")){
            getAllDataList("nregs_ids");
        }
        else {
            no_data_tv.setVisibility(View.VISIBLE);
            todays_rv.setVisibility(View.GONE);
        }
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        todays_rv.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = todays_rv.getChildCount();
                totalItemCount = gl.getItemCount();
                firstVisibleItem = gl.findFirstVisibleItemPosition();
                lastItem = firstVisibleItem + visibleItemCount;
                tem = totalItemCount;
                Log.e("last  total", String.valueOf(lastItem) + "" + String.valueOf(totalItemCount));
                if (lastItem == totalItemCount) {
                    if (preLast != lastItem) {
                        //to avoid multiple calls for last item
                        Log.d("Last", "Last");
                        preLast = lastItem;
                        page = page + 1;
                        if (Session.getType(IDListActivity.this).equals("0")){
                            getAllDataList("shg_ids");
                        }
                        else if (Session.getType(IDListActivity.this).equals("1")){
                            getAllDataList("student_ids");
                        }
                        else if (Session.getType(IDListActivity.this).equals("2")){
//employee_ids
                            getAllDataList("employee_ids");
                        }
                        else if (Session.getType(IDListActivity.this).equals("3")){
//employee_ids
                            getAllDataList("nregs_ids");
                        }
                    }
                }
            }
        });    }


    public void  getAllDataList(final String s) {
        // dataMandals.clear();
        if (page==0){
            dataImages.clear();
            dataStudents.clear();
        }
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String Url = Session.BASE_URL + ""+s;//shg_ids?user_id=1&type=today
        Log.e("url1",Url);


        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                Log.e("onResTdy: ", response.toString());

                try {
                    JSONArray jsonArray  = new JSONArray(response);
                    // mandal_tv.setText(jsonArray.getJSONObject(0).getString("title"));
                   /* if (jsonArray.length()==0){
                        showAlert();
                    }*/

                    if (Session.getType(IDListActivity.this).equals("0")){
                        for (int i=0;i<jsonArray.length();i++){
                            DataImages temp = new DataImages(jsonArray.getJSONObject(i));
                            dataImages.add(temp);
                        }
                        adapterTodayList = new AdapterTodayList(IDListActivity.this,dataImages,1);
                        todays_rv.setAdapter(adapterTodayList);
                        if (page == 0) {

                            adapterTodayList.notifyDataSetChanged();

                        } else {
                            todays_rv.scrollToPosition(tem - visibleItemCount + 1);
                        }
                    }

                    else if (Session.getType(IDListActivity.this).equals("1")){
                        for (int i=0;i<jsonArray.length();i++){
                            DataStudents temp = new DataStudents(jsonArray.getJSONObject(i));
                            dataStudents.add(temp);
                        }
                        adapterTodayListStudents = new AdapterTodayListStudents(IDListActivity.this,dataStudents,1);
                        todays_rv.setAdapter(adapterTodayListStudents);
                        if (page == 0) {

                            adapterTodayListStudents.notifyDataSetChanged();

                        } else {
                            todays_rv.scrollToPosition(tem - visibleItemCount + 1);
                        }
                    }

                    else if (Session.getType(IDListActivity.this).equals("2")){
                        for (int i=0;i<jsonArray.length();i++){
                            DataEmploye temp = new DataEmploye(jsonArray.getJSONObject(i));
                            dataEmployes.add(temp);
                        }
                        adapterTodayListEmploye = new AdapterTodayListEmploye(IDListActivity.this,dataEmployes,1);
                        todays_rv.setAdapter(adapterTodayListEmploye);
                        if (page == 0) {

                            adapterTodayListEmploye.notifyDataSetChanged();

                        } else {
                            todays_rv.scrollToPosition(tem - visibleItemCount + 1);
                        }
                    }
                    else if (Session.getType(IDListActivity.this).equals("3")){
                        for (int i=0;i<jsonArray.length();i++){
                            DataNregs temp = new DataNregs(jsonArray.getJSONObject(i));
                            dataNregs.add(temp);
                        }
                        adapterTodayListNregs = new AdapterTodayListNregs(IDListActivity.this,dataNregs,1);
                        todays_rv.setAdapter(adapterTodayListNregs);
                        if (page == 0) {

                            adapterTodayListNregs.notifyDataSetChanged();

                        } else {
                            todays_rv.scrollToPosition(tem - visibleItemCount + 1);
                        }
                    }


                   /* adapterTodayList = new AdapterTodayList(IDListActivity.this,dataImages,1);
                    adapterTodayList.notifyDataSetChanged();
                    todays_rv.setAdapter(adapterTodayList);*/
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
                params.put("user_id",Session.getUserid(IDListActivity.this));
                params.put("page",""+page);
                /*if (page!=0){
                    params.put("page",""+page);
                }*/

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

    public void editDetails(final int pos,final DataImages data){
        Intent intent = new Intent(IDListActivity.this,SHGFormActivity.class);
        intent.putExtra("data",data);
        intent.putExtra("pos",""+pos);
        startActivity(intent);
        finish();
    }
    public void editDetailsStudents(final int pos,final DataStudents data){
        Intent intent = new Intent(IDListActivity.this,StudentFormActivity.class);
        intent.putExtra("data",data);
        intent.putExtra("pos",""+pos);
        startActivity(intent);
        finish();
    }
    public void editDetailsEmploye(final int pos,final DataEmploye data){
        Intent intent = new Intent(IDListActivity.this,Emp_temp.class);
        intent.putExtra("data",data);
        intent.putExtra("pos",""+pos);
        startActivity(intent);
        finish();
    }
    public void editDetailsNregs(final int pos,final DataNregs data){
        Intent intent = new Intent(IDListActivity.this,NREGSFormActivity.class);
        intent.putExtra("data",data);
        intent.putExtra("pos",""+pos);
        startActivity(intent);
        finish();
    }
//    public void editDetails(final int pos,final DataImages data){
//        Intent intent = new Intent(IDListActivity.this,SHGFormActivity.class);
//        intent.putExtra("data",data);
//        intent.putExtra("pos",""+pos);
//        startActivity(intent);
//        finish();
//    }

    public void showAlert(){
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(IDListActivity.this);

        builder.setTitle("Alert !");
        builder.setMessage("No data\nHome -> SHG");
        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                finish();
            }
        });
        final AlertDialog alertDialog = builder.create();


        alertDialog.show();
    }


    public void  calldelete(final String id,final int activity) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String Url = "";
        if (activity==0){
             Url = Session.BASE_URL + "shg_delete";
        }
        else if (activity==1){
            Url = Session.BASE_URL + "student_delete";
        }
        else if (activity==2){
            Url = Session.BASE_URL + "employee_delete";
        }
        Log.e("url1",Url);


        final StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.e("onResponse: ", response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("Success")) {
                        Toast.makeText(IDListActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("jsonExc",e.getMessage());
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
                params.put("id",id);
                Log.e("params",params.toString());
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
