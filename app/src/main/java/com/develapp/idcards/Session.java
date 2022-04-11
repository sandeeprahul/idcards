package com.develapp.idcards;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;


import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mac on 7/2/18.
 */

public class Session {

    static String SESSION_ID="session_id";
//    public static final String BASE_URL_ = "https://projects.yellowsoft.in/idcards/app/";
    public static final String BASE_URL = "http://idcards.yellowsoft.in/app/";
    public static final String PDF_URL = "http://idcards.yellowsoft.in/";
    public static final String PDF_URL_ = "https://projects.yellowsoft.in/idcards/";
    //https://projects.yellowsoft.in/idcards/app/register
    static String DEVICE_ID="device_id";
    static String USER_ID="user_id";
    static String USER_mobile="user_mobile";
    public static final String USERNAME = "";
    static String USER_IMAGE="user_image";


    public static void setUserDetails(Context context, String email, String phone) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("phone", phone);
        editor.apply();
    }



    public static String getUserPhone(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("phone", "");
    }
    public static String getUserEmail(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("email", "-1");
    }



    public static void setMemberJSON(Context context, String membercode) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("memberJSON", membercode);
        editor.apply();
    }


    public static void setSettings(Context context, String membercode) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("settings", membercode);
        editor.apply();
    }
//setSettings


    public static String getMemberJSON(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("memberJSON", "");
    }


    public static String getSettings(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("settings", "");
    }


    public static void setNoofIds(Context context, String noofids) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("noofids", noofids);
        editor.apply();
    }



    public static String getNoofIds(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("noofids", "0");
    }

    //shipping charges
    public static void setType(Context context, String typeID) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("typeID", typeID);
        //
        editor.apply();
    }



    public static String getType(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("typeID", "-1");
    }



    //set total price
    public static void setEntity(Context context, String entity) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("entity", entity);
        editor.apply();
    }



    public static String getEntity(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("entity", "kids");
    }

    //set user id (or) memberid

    public static void setUserid(Context context, String member_id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID, member_id);
        editor.apply();

    }
    public static String getUserid(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(USER_ID, "-1");

    }
    public static void settype(Context context, String type) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("type", type);
        editor.apply();

    }
    public static String gettype(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("type", "");

    }
    public static String getUserName(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(USERNAME, "");
    }




    public static String getUserImage(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(USER_IMAGE, "Mobile");
    }



    public static void setUserImage(Context context, String session_id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_IMAGE, session_id);
        editor.apply();
    }

    public static void logoutUser(Context context){
        // Clearing all data from Shared Preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        sharedPreferences.getString(USER_ID, "-1");
        sharedPreferences.getString(USERNAME, " ");
        sharedPreferences.getString("email", " ");
        editor.clear();
        editor.apply();


    }

    /*public static void sendRegistrationToServer(Context context, String type) {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        final String[] p_id = {""};
        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                Log.d("debug", "User:" + userId);
                if (registrationId != null) {
                    Log.d("debug", "registrationId:" + registrationId);
                    p_id[0] = userId;
                }
            }
        });


        String Url = Session.BASE_URL+"api/push/register.php";
        try {
            final JSONObject jsonBody = new JSONObject();
            jsonBody.put("player_id", p_id[0]);
            jsonBody.put("member_id", Session.getUserid(context));
            jsonBody.put("device_token",refreshedToken);
            jsonBody.put("type", type);
            jsonBody.put("dev_type", "android");

            final String mRequestBody = jsonBody.toString();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("token_register",response.toString());

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }


                @Override
                public byte[] getBody() {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                                mRequestBody, "utf-8");
                        return null;
                    }
                }


            };
            ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

}
