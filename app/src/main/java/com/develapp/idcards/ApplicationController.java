package com.develapp.idcards;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by sriven on 5/7/2018.
 */

public class ApplicationController extends Application {
    private RequestQueue mRequestQueue;
    public static final String TAG = "VolleyPatterns";
    JSONObject settings,bannersAndoffers;
    //LatLng latLng;
    String type_s;
    JSONObject driver_json;
    JSONArray content_json;
    String fbEmail;
   // GPSTracker gpsTracker;

    private static ApplicationController sInstance;
    public String stringLatitude="0";
    public String stringLongitude="0";

    @Override
    public void onCreate() {
        super.onCreate();
        settings = new JSONObject();

        // initialize the singleton//
//         gpsTracker = new GPSTracker(this);
//         set_refresh_timer();
//        FirebaseApp.initializeApp(this);
//        OneSignal.startInit(this)
//                .setNotificationOpenedHandler(new ExampleNotificationOpenedHandler(this))
//                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
////                .unsubscribeWhenNotificationsAreDisabled(true)
//                .init();
//        OneSignal.setSubscription(true);
//        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
//            @Override
//            public void idsAvailable(String userId, String registrationId) {
//                Log.d("debug", "User:" + userId);
//                if (registrationId != null)
//                    Log.d("debug", "registrationId:" + registrationId);
//            }
//        });


        sInstance = this;
    }
    final Handler h = new Handler();
    final int delay = 1000 * 10; //milliseconds
//    final Runnable r = new Runnable() {
//        @Override
//        public void run() {
//            Log.e("time", "ticked");
//            h.postDelayed(this, delay);
//            if (gpsTracker.isGPSEnabled){
//                 stringLatitude = String.valueOf(gpsTracker.getLatitude());
//                 stringLongitude = String.valueOf(gpsTracker.getLongitude());
//                Log.e("laat_long",stringLatitude+" , "+stringLongitude);
//            }
//        }
//    };

//    private void set_refresh_timer() {
//        h.removeCallbacks(r);
//        h.postDelayed(r, delay);
//    }


    /**
     * @return co.pixelmatter.meme.ApplicationController singleton instance
     */
    public static synchronized ApplicationController getInstance() {
        return sInstance;
    }
    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        req.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        getRequestQueue().add(req);
    }

    //
   /* public void setCharges(final String shippingcharges,final String schemeamt){
        Log.e("charges",""+shippingcharges+""+schemeamt);
    }*/



}
