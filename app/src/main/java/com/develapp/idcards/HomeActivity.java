package com.develapp.idcards;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;

public class HomeActivity extends AppCompatActivity {

    ViewPager bannerslider_vp;
    AdapterBanners adapterBanners;
    ArrayList<DataBanners> dataBanners = new ArrayList<DataBanners>();
    int currentPage;
    LinearLayout main_popup_ll,ll_disble_student,ll_disble_employe,ll_disble_business,sidemenu_ll;
    TextView usrname_tv,creation_tv,logout_tv,id_list_tv,fisrt_title_tv,pass_tv,pdf_tv,close_tv_popup;
    TextView help_tv,contactus_tv,rem_cards_tv;
    DrawerLayout  drawerlayout;
    CardView first_cardview,ids_list_cardview,pdf_cardview,logout_cardview;
    ImageView menu_img,first_img,img_popup;
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivity(0);
        finishAffinity();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getMemberDetails();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMemberDetails();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_home);



        if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        requestPermissionCamera();
    /*    if (!checkPermissionCamera()){
            requestPermissionCamera();
        }

        if (!checkPermissionRead()){
            requestPermissionRead();
        }

        if (!checkPermissionWrite()){
            requestPermissionWrite();
        }*/




        main_popup_ll = (LinearLayout)findViewById(R.id.main_popup_ll);
        sidemenu_ll = (LinearLayout)findViewById(R.id.sidemenu_ll);
        img_popup = (ImageView)findViewById(R.id.img_popup);
        first_img = (ImageView)findViewById(R.id.first_img);
        contactus_tv = (TextView)findViewById(R.id.contactus_tv);
        rem_cards_tv = (TextView)findViewById(R.id.rem_cards_tv);
        help_tv = (TextView)findViewById(R.id.help_tv);
        pdf_tv = (TextView)findViewById(R.id.pdf_tv);
        pass_tv = (TextView)findViewById(R.id.pass_tv);
        logout_tv = (TextView)findViewById(R.id.logout_tv);
        id_list_tv = (TextView)findViewById(R.id.id_list_tv);
        fisrt_title_tv = (TextView)findViewById(R.id.fisrt_title_tv);
        close_tv_popup = (TextView) findViewById(R.id.close_tv_popup);
        creation_tv = (TextView) findViewById(R.id.creation_tv);
        usrname_tv = (TextView) findViewById(R.id.usrname_tv);
        ids_list_cardview = (CardView)findViewById(R.id.ids_list_cardview);
        logout_cardview = (CardView)findViewById(R.id.logout_cardview);
        pdf_cardview = (CardView)findViewById(R.id.pdf_cardview);
        first_cardview = (CardView)findViewById(R.id.first_cardview);
        menu_img = (ImageView)findViewById(R.id.menu_img);
        drawerlayout = (DrawerLayout)findViewById(R.id.drawerlayout);
        bannerslider_vp = (ViewPager)findViewById(R.id.bannerslider_vp);
        adapterBanners = new AdapterBanners(HomeActivity.this,dataBanners);
        currentPage = bannerslider_vp.getCurrentItem();
      //  dataBanners.add(new DataBanners(R.drawable.ad1));
     //   dataBanners.add(new DataBanners(R.drawable.ad2));
       // showMainpopup();
//        if (!Session.getNoofIds(HomeActivity.this).toString().equals("0")){
//            rem_cards_tv.setText("Remaining\n"+Session.getNoofIds(HomeActivity.this));
//        }
//        else {
//            rem_cards_tv.setText("");
//        }
        main_popup_ll.setVisibility(View.GONE);
        //slideViewpager(dataBanners.size());
        adapterBanners.notifyDataSetChanged();
        bannerslider_vp.setAdapter(adapterBanners);
        usrname_tv.setText(Session.getUserEmail(HomeActivity.this));
        menu_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerlayout.openDrawer(GravityCompat.START);
            }
        });
        logout_tv.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                showAlert("Are you sure you want to Logout?");
            }
        });
        sidemenu_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        if(Session.getType(HomeActivity.this).equals("0")){
            first_img.setImageDrawable(getResources().getDrawable(R.drawable.shg));
            fisrt_title_tv.setText("SHG");
//            rem_cards_tv.setText("");
        }
        else if (Session.getType(HomeActivity.this).equals("1")){
            first_img.setImageDrawable(getResources().getDrawable(R.drawable.student));
            fisrt_title_tv.setText("STUDENT");
        }
        else if (Session.getType(HomeActivity.this).equals("2")){
            first_img.setImageDrawable(getResources().getDrawable(R.drawable.employee));
            fisrt_title_tv.setText("EMPLOYEE");
        }
        else if (Session.getType(HomeActivity.this).equals("3")){
            first_img.setImageDrawable(getResources().getDrawable(R.drawable.employee));
            fisrt_title_tv.setText("NREGS");
        }
        if (getIntent().hasExtra("showHomePop")){
            showMainpopup();
        }
        main_popup_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showMainpopup();
            }
        });
        close_tv_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_popup_ll.setVisibility(View.GONE);
            }
        });
        help_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerlayout.closeDrawer(GravityCompat.START);
                showMainpopup();
            }
        });
        contactus_tv.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                drawerlayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(HomeActivity.this,ContactUsActivity.class);
                startActivity(intent);
            }
        });
        first_cardview.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (Session.getType(HomeActivity.this).equals("0")){
                    Intent intent = new Intent(HomeActivity.this,SHGFormActivity.class);
                    //Intent intent = new Intent(HomeActivity.this,SHGFormActivity.class);
                    startActivity(intent);
                }else if (Session.getType(HomeActivity.this).equals("1")){
                    Intent intent = new Intent(HomeActivity.this,StudentFormActivity.class);
                    //Intent intent = new Intent(HomeActivity.this,SHGFormActivity.class);
                    startActivity(intent);
                }
                else if (Session.getType(HomeActivity.this).equals("2")){
                    Intent intent = new Intent(HomeActivity.this,Emp_temp.class);
                    //Intent intent = new Intent(HomeActivity.this,SHGFormActivity.class);
                    startActivity(intent);
                }
                else if (Session.getType(HomeActivity.this).equals("3")){
                    Intent intent = new Intent(HomeActivity.this,NREGSFormActivity.class);
                    //Intent intent = new Intent(HomeActivity.this,SHGFormActivity.class);
                    startActivity(intent);
                }
            }
        });
        ids_list_cardview.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                if (Session.getType(HomeActivity.this).equals("0")){
                    Intent intent = new Intent(HomeActivity.this,IDListActivity.class);
                    intent.putExtra("type","0");
                    startActivity(intent);
                }else if (Session.getType(HomeActivity.this).equals("1")){
                    Intent intent = new Intent(HomeActivity.this,IDListActivity.class);
                    intent.putExtra("type","1");
                    startActivity(intent);
                }
                else if (Session.getType(HomeActivity.this).equals("2")){
                    Intent intent = new Intent(HomeActivity.this,IDListActivity.class);
                    intent.putExtra("type","2");
                    startActivity(intent);
                }
                else if (Session.getType(HomeActivity.this).equals("3")){
                    Intent intent = new Intent(HomeActivity.this,IDListActivity.class);
                    intent.putExtra("type","3");
                    startActivity(intent);
                }
            }
        });
        pdf_cardview.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (Session.getType(HomeActivity.this).equals("0")){
                    Intent intent = new Intent(HomeActivity.this,PDFActivity.class);
                    intent.putExtra("type","0");
                    startActivity(intent);
                }else if (Session.getType(HomeActivity.this).equals("1")){
                    Intent intent = new Intent(HomeActivity.this,PDFActivity.class);
                    intent.putExtra("type","1");
                    startActivity(intent);
                }
                else if (Session.getType(HomeActivity.this).equals("2")){
                    Intent intent = new Intent(HomeActivity.this,PDFActivity.class);
                    intent.putExtra("type","2");
                    startActivity(intent);
                }
                else if (Session.getType(HomeActivity.this).equals("3")){
                    Intent intent = new Intent(HomeActivity.this,PDFActivity.class);
                    intent.putExtra("type","3");
                    startActivity(intent);
                }
            }
        });
        logout_cardview.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
          logout_tv.callOnClick();
            }
        });
        creation_tv.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                drawerlayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(HomeActivity.this,CreationActivity.class);
                if(Session.getType(HomeActivity.this).equals("0")){
                    intent.putExtra("from","0");

                }
                else if (Session.getType(HomeActivity.this).equals("1")){
                    intent.putExtra("from","1");

//                    startActivity(intent);
                }
                else if (Session.getType(HomeActivity.this).equals("2")){
                    intent.putExtra("from","2");

//                    startActivity(intent);
                }
                else if (Session.getType(HomeActivity.this).equals("3")){
                    intent.putExtra("from","3");

//                    startActivity(intent);
                }

//                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(HomeActivity.this).toBundle());
                startActivity(intent);

            }
        });

        id_list_tv.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                drawerlayout.closeDrawer(GravityCompat.START);
                ids_list_cardview.callOnClick();
//                drawerlayout.closeDrawer(GravityCompat.START);
//                Intent intent = new Intent(HomeActivity.this,IDListActivity.class);
//                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(HomeActivity.this).toBundle());
            }
        });

        pdf_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerlayout.closeDrawer(GravityCompat.START);
                pdf_cardview.callOnClick();
                drawerlayout.closeDrawer(GravityCompat.START);
            }
        });

        getDetails();
        getBanners();
    }

    public void slideViewpager(final int i){
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == i) {
                    currentPage = 0;
                }
                bannerslider_vp.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);
    }

    public void getDetails()  {
        //nothing to do with this method as we call once again MemberDetails for id's count
        Log.e( "getDetails: ", "true");
        try {
            JSONArray jsonArrar = new JSONArray(Session.getMemberJSON(HomeActivity.this));
            JSONObject jsonObject = jsonArrar.getJSONObject(0);
            pass_tv.setText(jsonObject.getString("password"));
           /* if (jsonObject.getJSONArray("relations").length()<1){
                //pass_tv.setVisibility(View.VISIBLE);

            }*/
        }
        catch (JSONException j){
            j.printStackTrace();
        }
    }
    public void getBanners() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String Url = Session.BASE_URL + "home";
        Log.e("url1",Url);
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.e("onResponse: ", response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("banners");
                    for (int i=0;i<jsonArray.length();i++){
                        DataBanners temp = new DataBanners(jsonArray.getJSONObject(i));
                        dataBanners.add(temp);
                    }
                    slideViewpager(jsonArray.length());
                    adapterBanners = new AdapterBanners(HomeActivity.this,dataBanners);
                    adapterBanners.notifyDataSetChanged();
                    bannerslider_vp.setAdapter(adapterBanners);
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
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                Log.e( "getParams: ", params.toString());
                return params;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);
    }
    public void showAlert(String msg){
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(HomeActivity.this);
        builder.setTitle("Alert !");
        builder.setMessage(msg);
        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent intent = new Intent(HomeActivity.this,WelcomePageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(HomeActivity.this).toBundle());
                Session.setMemberJSON(HomeActivity.this,"");
                Session.logoutUser(HomeActivity.this);
                dialog.dismiss();
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void showMainpopup(){
        main_popup_ll.setVisibility(View.VISIBLE);
        String s= Session.getSettings(HomeActivity.this);
        Log.e("Settings",s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            Log.e("showmainpopup",jsonObject.toString());
            String imgURL = jsonObject.getJSONObject("settings").getString("info_image");
          //  Log.e( "showMainpopup: ", jsonObject.toString());
            Picasso.get().load(imgURL).into(img_popup);
            //  main_popup_ll.setVisibility(View.VISIBLE);
        } catch (JSONException e) {
            e.printStackTrace();
            getSettings();
            Log.e( "jsonexc: ", e.getMessage());
        }
    }
    public void  getSettings() {
       /* final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);*/
        String Url = Session.BASE_URL;
        Log.e("getSettings",Url);
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
//                if (progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                }
                Log.e("onResponse: ", response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);
//                    Session.setSettings(HomeActivity.this,response);
                    Picasso.get().load(jsonObject.getJSONObject("settings").getString("info_image").toString()).into(img_popup);
                    Log.e("info_image: ", jsonObject.getJSONObject("settings").getString("info_image").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("jsonEXcep",e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                if (progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                }
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
    private boolean checkPermissionRead() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    private boolean checkPermissionWrite() {
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        return  result1 == PackageManager.PERMISSION_GRANTED;
    }
    private boolean checkPermissionCamera() {
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        return  result2 == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermissionRead() {
        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }
    private void requestPermissionWrite() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }
    private void requestPermissionCamera() {
      //  ActivityCompat.requestPermissions(this, new String[]{CAMERA}, PERMISSION_REQUEST_CODE);
        if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
        }
    }
    public void  getMemberDetails() {
        String Url = Session.BASE_URL + "members/"+Session.getUserid(HomeActivity.this);
        Log.e("url1",Url);
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("onResponse: ", response.toString());
                    Session.setMemberJSON(HomeActivity.this,response);
                    JSONArray jsonArrar = new JSONArray(response.toString());
                    JSONObject jsonObject = jsonArrar.getJSONObject(0);
                    String s = Session.getNoofIds(HomeActivity.this);
                    pass_tv.setText(jsonObject.getString("password"));
//                    if(Session.getType(HomeActivity.this).equals("0")){
                        rem_cards_tv.setText(jsonObject.getString("ids_completed")+"/"+jsonObject.getString("ids_cnt"));//ids_completed
//                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("resLoginError", error.toString());
                VolleyLog.e("Error: ", error.getMessage());
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
