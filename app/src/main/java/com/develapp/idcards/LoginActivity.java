package com.develapp.idcards;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    TextView sub_tv,login_title_tv,signup_tv,forgetp_tv;
    LinearLayout signup_ll,entity_ll;
    ImageView back_img_login;
    EditText password_et_login,email_et_login;

//forgert password , (SHG*)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_login);

        back_img_login  = (ImageView) findViewById(R.id.back_img_login);
        login_title_tv = (TextView) findViewById(R.id.login_title_tv);
        //signup_tv = (TextView) findViewById(R.id.signup_tv);
        //signup_ll = (LinearLayout) findViewById(R.id.signup_ll);
        forgetp_tv = (TextView) findViewById(R.id.forgetp_tv);
        sub_tv = (TextView) findViewById(R.id.sub_tv);
        password_et_login = (EditText) findViewById(R.id.password_et_login);
        email_et_login = (EditText) findViewById(R.id.email_et_login);

        sub_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(email_et_login.getText().toString().equals("")){
                        showAlert("Please enter UserName");
                    }
                    else if (password_et_login.getText().toString().equals("")){
                        showAlert("Password length must be atlest 6 characters");
                    }
                    else {
                        callLoginService();
                    }



            }
        });
        back_img_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void callLoginService() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String Url = Session.BASE_URL + "login";
        Log.e("LoginURL",Url);
        /*   final RequestQueue requestQueue = Volley.newRequestQueue(this);
           final JSONObject jsonBody = new JSONObject();
           jsonBody.put("username", email_et_login.getText().toString());
           jsonBody.put("password", password_et_login.getText().toString());

           final String mRequestBody = jsonBody.toString();*/

        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.e("resLogin",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("1")){
                        Session.setUserDetails(LoginActivity.this,jsonObject.getString("username"),jsonObject.getString("phone"));
                        Session.setUserid(LoginActivity.this,jsonObject.getString("id"));
                        String typeId = jsonObject.getString("type");

                        if (typeId.equals("0")){
                            Session.setType(LoginActivity.this,"0");
                        }
                        else if (typeId.equals("1")){
                            Session.setType(LoginActivity.this,"1");
                        }else if (typeId.equals("2")){
                            Session.setType(LoginActivity.this,"2");
                        }
                        else if (typeId.equals("3")){
                            Session.setType(LoginActivity.this,"3");
                        }

                        getMemberDetails(jsonObject.getString("id"));
                    }
                    else {
                        showAlert(jsonObject.getString("message"));
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
                //showAlert(String.valueOf(response));

              /*  try {
                    if (response.getString("status").equals("1")){
                       // Toast.makeText(LoginActivity.this,""+response.getString("message"),Toast.LENGTH_LONG).show();
                     //   Session.sendRegistrationToServer(LoginActivity.this,"parent");
                       // Session.settype(LoginActivity.this,"parent");
                        Session.setUserid(LoginActivity.this,response.getJSONObject("data").getString("pa_id"),response.getJSONObject("data").getString("pa_firstname"),response.getJSONObject("data").getString("pa_lastname"));
                     //   Session.setUserIsParentOrDriver(LoginActivity.this,"1");
                        Session.setUserDetails(LoginActivity.this,response.getJSONObject("data").getString("pa_email"),response.getJSONObject("data").getString("pa_phone"));
                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);

                        //intent.putExtra("parentSignin","1");
                       // startActivity(intent);
                       // finish();
                    }
                    else {
                      showAlert(response.getString("message"));
                    }
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
                showAlert(error.getMessage().toString());
            }
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                params.put("username",email_et_login.getText().toString());
                params.put("password",password_et_login.getText().toString());
                Log.e("paramsLogin",params.toString());
                return params;
            }

/*
            @Override
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
//            Log.e("CallSubURL",Session.BASE_URL+"api/parents/login.php?"+jsonBody);
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);


    }
    public void  getMemberDetails(final String s) {


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String Url = Session.BASE_URL + "members/"+s;
        Log.e("url1",Url);


        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.e("onResponse: ", response.toString());
                Session.setMemberJSON(LoginActivity.this,response);
                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("showHomePop","1");
                startActivity(intent);
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

    public void showAlert(String msg){
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(LoginActivity.this);

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
