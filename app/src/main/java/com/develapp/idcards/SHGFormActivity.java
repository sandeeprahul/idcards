package com.develapp.idcards;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;
import static android.content.RestrictionsManager.RESULT_ERROR;


//mandals_popup_ll is mainly used as main popup (****IMP****)

public class SHGFormActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 44;
    EditText membername_edt, wof_name_edt, phone_edt, groupname_edt, groupid_edt, voname_edt, village_edt;
    EditText relation_name_tv, noof_cards_edt;
    RelativeLayout desg_rl, mandal_rl, relation_rl;
    TextView submit_tv, close_tv_mandals_popup, close_tv_popup, mandal_tv, title_tv_popup;
    TextView relation_tv, nodata_tv, logout_tv, creation_tv, desg_tv, todayslist_title_tv;
    LinearLayout desg_popup_ll, gallery_ll, capture_ll, mandals_popup_ll;
    RecyclerView mandals_rv, desig_rv, todays_rv;
    Bitmap bitmap;
    String memMandalID;
    Spinner institution_spinner, relation_spinner, desg_spinner;
    AdapterTodayList adapterTodayList;
    public String filePath;
    ImageView back_img;
    String desgID = "", mandalId = "", relations = "", desg = "";
    AdapterMandals adapterMandals;
    ArrayList<DataMandals> dataMandals = new ArrayList<DataMandals>();
    ArrayList<DataImages> dataImages = new ArrayList<DataImages>();
    ArrayList<DataImages> dataImages_ = new ArrayList<DataImages>();
    ArrayList<DataDesgnations> dataDesgnations = new ArrayList<DataDesgnations>();
    ImageView capture_img, gallery_img;
    ImageView usr_img;
    boolean notEdit = true;
    private String ext;
    String encodedString = "";
    boolean imgChanged;
    ArrayAdapter<String> adapter;
    ArrayList<String> desgArray = new ArrayList<String>();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (desg_popup_ll.getVisibility() == View.VISIBLE) {
            desg_popup_ll.setVisibility(View.GONE);
        } else if (mandals_popup_ll.getVisibility() == View.VISIBLE) {
            mandals_popup_ll.setVisibility(View.GONE);
        } else {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_shgform);

        if (ActivityCompat.checkSelfPermission(SHGFormActivity.this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SHGFormActivity.this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SHGFormActivity.this, new String[]{READ_EXTERNAL_STORAGE}, 1);
        }
        //storage wirte perimission
        /*if (ActivityCompat.checkSelfPermission(SHGFormActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SHGFormActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SHGFormActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }*/
        desg_spinner = (Spinner) findViewById(R.id.desg_spinner);
        todayslist_title_tv = (TextView) findViewById(R.id.todayslist_title_tv);
        desg_tv = (TextView) findViewById(R.id.desg_tv);
        logout_tv = (TextView) findViewById(R.id.logout_tv);
        creation_tv = (TextView) findViewById(R.id.creation_tv);
        nodata_tv = (TextView) findViewById(R.id.nodata_tv);
        relation_tv = (TextView) findViewById(R.id.relation_tv);
        relation_rl = (RelativeLayout) findViewById(R.id.relation_rl);
        back_img = (ImageView) findViewById(R.id.back_img);
        desig_rv = (RecyclerView) findViewById(R.id.desig_rv);
        todays_rv = (RecyclerView) findViewById(R.id.todays_rv);
        close_tv_popup = (TextView) findViewById(R.id.close_tv_popup);
        close_tv_mandals_popup = (TextView) findViewById(R.id.close_tv_mandals_popup);
        mandals_popup_ll = (LinearLayout) findViewById(R.id.mandals_popup_ll);
        desg_popup_ll = (LinearLayout) findViewById(R.id.desg_popup_ll);
        membername_edt = (EditText) findViewById(R.id.membername_edt);
        noof_cards_edt = (EditText) findViewById(R.id.noof_cards_edt);
        wof_name_edt = (EditText) findViewById(R.id.wof_name_edt);
        mandal_tv = (TextView) findViewById(R.id.mandal_tv);
        mandals_rv = (RecyclerView) findViewById(R.id.mandals_rv);
        phone_edt = (EditText) findViewById(R.id.phone_edt);
        title_tv_popup = (TextView) findViewById(R.id.title_tv_popup);
        groupname_edt = (EditText) findViewById(R.id.groupname_edt);
        groupid_edt = (EditText) findViewById(R.id.groupid_edt);
        voname_edt = (EditText) findViewById(R.id.voname_edt);
        village_edt = (EditText) findViewById(R.id.village_edt);
        desg_rl = (RelativeLayout) findViewById(R.id.desg_rl);
        mandal_rl = (RelativeLayout) findViewById(R.id.mandal_rl);
        usr_img = (ImageView) findViewById(R.id.usr_img);
        capture_img = (ImageView) findViewById(R.id.capture_img);
        gallery_img = (ImageView) findViewById(R.id.gallery_img);
        submit_tv = (TextView) findViewById(R.id.submit_tv);
        capture_ll = (LinearLayout) findViewById(R.id.capture_ll);
        gallery_ll = (LinearLayout) findViewById(R.id.gallery_ll);
        village_edt.setImeOptions(EditorInfo.IME_ACTION_DONE);
        desgArray = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(SHGFormActivity.this, android.R.layout.simple_spinner_item, desgArray);
        desg_spinner.setAdapter(adapter);
        desg_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("onItemSelected: ", "" + desgArray.get(position) + "");
                desg_tv.setText(desgArray.get(position));
                desg = desgArray.get(position);
                desg_spinner.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        getMemberDetails();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SHGFormActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mandals_rv.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(SHGFormActivity.this);
        linearLayoutManager1.setOrientation(RecyclerView.VERTICAL);
        todays_rv.setLayoutManager(linearLayoutManager1);
        desg_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title_tv_popup.setText("Select Designation");
                mandals_popup_ll.setVisibility(View.VISIBLE);
                getDetailsDesg();
//                desg_spinner.setVisibility(View.VISIBLE);
//                desg_spinner.performClick();
            }
        });
//        if (!Session.getNoofIds(SHGFormActivity.this).toString().equals("0")){
////            noof_cards_edt.setVisibility(View.GONE);
//        }
//        else {
//            noof_cards_edt.setVisibility(View.VISIBLE);
//        }
        capture_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //V3 Playstore
//                if (notEdit){
//                    ImagePicker.Companion.with(SHGFormActivity.this).cameraOnly().compress(512).crop(2f,2f).start();
//                }
//                else {
//                    ImagePicker.Companion.with(SHGFormActivity.this).cameraOnly().compress(512).crop(5f,5f).start();
//                    imgChanged = true;
//                }

                //V3

                if (notEdit) {
                    ImagePicker.Companion.with(SHGFormActivity.this).cameraOnly().compress(512).start();
                } else {
                    ImagePicker.Companion.with(SHGFormActivity.this).cameraOnly().compress(512).start();
                    imgChanged = true;
                }

            }
        });
        gallery_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //V3 Playstore
//                if (notEdit) {
//                    ImagePicker.Companion.with(SHGFormActivity.this).galleryOnly().compress(512).crop(2f, 2f).start();
//                } else {
//                    ImagePicker.Companion.with(SHGFormActivity.this).galleryOnly().compress(512).crop(5f, 5f).start();
//                    imgChanged = true;
//                }

                //V3

                if (notEdit) {
                    ImagePicker.Companion.with(SHGFormActivity.this).galleryOnly().compress(512).start();
                } else {
                    ImagePicker.Companion.with(SHGFormActivity.this).galleryOnly().compress(512).start();
                    imgChanged = true;
                }
            }
        });
        close_tv_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desg_popup_ll.setVisibility(View.GONE);
            }
        });
        close_tv_mandals_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mandals_popup_ll.setVisibility(View.GONE);
            }
        });
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                if (membername_edt.getText().toString().equals("")) {
                    showAlert("Please Enter MemberName");
//                    Toast.makeText(SHGFormActivity.this,"Please Enter MemberName",Toast.LENGTH_SHORT).show();
                }
             /*   else if (relations.equals("")){
                    Toast.makeText(SHGFormActivity.this,"Please Select RelationType\nEx: W/O , S/O",Toast.LENGTH_SHORT).show();
                }*/
                else if (wof_name_edt.getText().toString().equals("")) {
                    showAlert("Please Enter RelationName");
//                    Toast.makeText(SHGFormActivity.this,"Please Enter RelationName",Toast.LENGTH_SHORT).show();
                } else if (phone_edt.getText().toString().length() != 10) {
                    showAlert("Phone number must be 10 numbers");
//                    Toast.makeText(SHGFormActivity.this,"Phone number must be 10 numbers",Toast.LENGTH_SHORT).show();
                } else if (groupname_edt.getText().toString().equals("")) {
                    showAlert("Please Enter Group Name");
//                    Toast.makeText(SHGFormActivity.this,"Please Enter Group Name",Toast.LENGTH_SHORT).show();
                } else if (groupid_edt.getText().toString().equals("")) {
                    showAlert("Please Enter GroupID");
//                    Toast.makeText(SHGFormActivity.this,"Please Enter GroupID",Toast.LENGTH_SHORT).show();
                } else if (voname_edt.getText().toString().equals("")) {
                    showAlert("Please Enter VO Name");
//                    Toast.makeText(SHGFormActivity.this,"Please Enter VO Name",Toast.LENGTH_SHORT).show();
                } else if (village_edt.getText().toString().equals("")) {
                    showAlert("Please Enter Village Name");
//                    Toast.makeText(SHGFormActivity.this,"Please Enter Village Name",Toast.LENGTH_SHORT).show();
                } else {
                    if (notEdit) {
                        if (encodedString.equals("")) {
                            showAlert("Please Upload Image");
                        } else {
                            registerImage();
                        }
                    } else {
                        registerImage();
                    }

                  /*  if (noof_cards_edt.getText().toString().length() != 0) {
                        Session.setNoofIds(SHGFormActivity.this, noof_cards_edt.getText().toString());
                    }*/
                }
               /* else if (desg.equals("")){
                    Toast.makeText(SHGFormActivity.this,"Please Select Designation",Toast.LENGTH_SHORT).show();
                }*/
                // sendImage("6");
            }
        });
        if (getIntent().hasExtra("data")) {
            DataImages temp = (DataImages) getIntent().getSerializableExtra("data");
            dataImages_.add(temp);
            edit(Integer.parseInt(getIntent().getStringExtra("pos")), temp);
        }
        getTodaysList();
    }

    public void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //IMAGE CAPTURE CODE
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImagePicker.REQUEST_CODE && resultCode == RESULT_OK) {
            Log.e("resultOK", data.toString());
            final Bundle extras = data.getExtras();
            usr_img.setImageURI(data.getData());
            Picasso.get().load(data.getData()).placeholder(R.drawable.blogo).into(usr_img);
            usr_img.setVisibility(View.VISIBLE);
            filePath = data.getData().getPath();
            Log.e("pathhhhh", data.getData().getPath());
            String currentString = data.getData().getPath();
            String[] separated = currentString.split("\\.");
            Log.e("pathhhhh", separated[separated.length - 1]);
            ext = separated[separated.length - 1];
            encodeImagetoString_lawyer();
        } else if (resultCode == RESULT_ERROR) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }

    public byte[] imaagedat() {
        Bitmap bmp = bitmap;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bmp.recycle();
        return byteArray;
    }

    public byte[] getFileDataFromDrawable(String file_name) {
        String[] separated = file_name.split("/");

        String t = separated[separated.length - 1];
        Log.e("extttt", t);
        String[] ext = t.split("\\.");
        Log.e("extttt", ext[0]);
        Log.e("extttt", ext[1]);
        Log.e("file_title_org", file_name);
        String file_title = "";
        int p = 0;
        for (int i = 0; i < separated.length; i++) {
            if (separated[i].toLowerCase().equals("storage")) {
                file_title = "/" + separated[i];
                p = 1;
            } else {
                if (p == 1) {
                    file_title = file_title + "/" + separated[i];
                }
            }
        }
        Log.e("file_title", file_title);
        File file = new File(file_title);
        int size = (int) file.length();
        Log.e("sizeeee", file.getAbsolutePath());
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.e("byteeee", String.valueOf(bytes.length));
        Log.e("byteeee_str", Arrays.toString(bytes));
        // image_byte = bytes;
        return bytes;
    }

    public Uri getImageUri_(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        // inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(),
                inImage, "Title", null);
        //Log.e("ansImg",path);

        return Uri.parse(path);
    }

    public void getMandals() {
        dataMandals.clear();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String Url = Session.BASE_URL + "mandals";
        Log.e("url1", Url);
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.e("onResponseMandals: ", response.toString());
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    mandal_tv.setText(jsonArray.getJSONObject(0).getString("title"));
                    /* for (int i=0;i<jsonArray.length();i++){
                     *//*if (jsonArray.getJSONObject(i).getString("id").equals(mandalId)){

                            mandal_tv.setText(jsonArray.getJSONObject(i).getString("title"));
                        }*//*
                        DataMandals temp = new DataMandals(jsonArray.getJSONObject(i));
                        dataMandals.add(temp);
                    }*/
                    adapterMandals = new AdapterMandals(SHGFormActivity.this, dataMandals, 1);
                    adapterMandals.notifyDataSetChanged();
//                    mandals_rv.setAdapter(adapterMandals);
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
                params.put("id", memMandalID);
         /*       params.put("name", name_edt.getText().toString());//6154043824,school_id, pickup_address pickup_latitude pickup_longitude subscription_duration amount_paid
                params.put("username", usrname_edt.getText().toString());//271012306
                params.put("password", password_edt.getText().toString());
                params.put("phone", phone_edt.getText().toString());
                params.put("email", email_edt.getText().toString());
                params.put("whatsapp", wspnumber_edt.getText().toString());
                params.put("address", address_edt.getText().toString());*/
                Log.e("params", params.toString());
                return params;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public void registerImage() {
        submit_tv.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String Url = Session.BASE_URL + "register_image";//shg_id=5&file="+encodedString+""+ext;
        Log.e("url1", Url);
       String s =  groupname_edt.getText().toString().replaceAll(" ","");
        try {
            final JSONObject jsonBody = new JSONObject();
            jsonBody.put("user_id", Session.getUserid(SHGFormActivity.this));
            jsonBody.put("member_name", membername_edt.getText().toString());//271012306
            jsonBody.put("rel_name", wof_name_edt.getText().toString());
            jsonBody.put("rel_type", "W/O");//relations
            jsonBody.put("phone", phone_edt.getText().toString());
            jsonBody.put("group_name",s.toUpperCase());
            jsonBody.put("group_id", groupid_edt.getText().toString());
            jsonBody.put("vo_name", voname_edt.getText().toString());
            jsonBody.put("village_name", village_edt.getText().toString());
            jsonBody.put("designation", desg);
//            jsonBody.put("ids_cnt",noof_cards_edt.getText().toString());
            jsonBody.put("mandal_id", memMandalID);
            if (!notEdit) {
                jsonBody.put("shg_id_update", dataImages_.get(0).id);
                if (encodedString.equals("")) {
                    jsonBody.put("file", "");
                    jsonBody.put("ext", "");
                } else {
                    jsonBody.put("file", encodedString);
                    jsonBody.put("ext", ext);
                }
            } else {
                jsonBody.put("file", encodedString);
                jsonBody.put("ext", ext);
            }
            Log.e("params", jsonBody.toString());
            final String mRequestBody = jsonBody.toString();
            StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Log.e("onResponse: ", response.toString());
//{ "status": "Success", "message": "Uploaded Successfully" }
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("status").equals("Success")) {
                            submit_tv.setEnabled(true);
                            Toast.makeText(SHGFormActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            showSuccessAlert(jsonObject.getString("message"));
                            Session.setNoofIds(SHGFormActivity.this, noof_cards_edt.getText().toString());

//                            if(!Session.getNoofIds(SHGFormActivity.this).toString().equals("0")){
//                                int i = Integer.parseInt(Session.getNoofIds(SHGFormActivity.this).toString());
//                                int j = i-1;
//                                Session.setNoofIds(SHGFormActivity.this,""+j);
//                            }
                            clearFields();
                            // finish();
                            usr_img.setVisibility(View.GONE);
                            capture_ll.setVisibility(View.VISIBLE);
                            gallery_ll.setVisibility(View.VISIBLE);
                            noof_cards_edt.setVisibility(View.GONE);
                        } else if (jsonObject.getString("status").equals("Failed")) {
                            submit_tv.setEnabled(true);
                            showAlert(jsonObject.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        submit_tv.setEnabled(true);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    submit_tv.setEnabled(true);
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Log.e("resLoginError", error.toString());
                    VolleyLog.e("Error: ", error.getMessage());
                    Toast.makeText(SHGFormActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    // showAlert(error.getMessage().toString());
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
        } catch (JSONException j) {
            j.printStackTrace();
        }
    }

    public void clearFields() {
        submit_tv.setText("Submit");
        membername_edt.getText().clear();
        wof_name_edt.getText().clear();
        phone_edt.getText().clear();
        groupid_edt.getText().clear();
        groupname_edt.getText().clear();
        // voname_edt.getText().clear();
        village_edt.getText().clear();
        desg_tv.setText(getResources().getString(R.string.desig));
        usr_img.setVisibility(View.GONE);
        capture_ll.setVisibility(View.VISIBLE);
        gallery_ll.setVisibility(View.VISIBLE);
        noof_cards_edt.getText().clear();
    }

    public void getDetails() {
        Log.e("getDetails: ", "true");
        try {
            dataMandals.clear();
            JSONArray jsonArrar = new JSONArray(Session.getMemberJSON(SHGFormActivity.this));
            JSONObject jsonObject = jsonArrar.getJSONObject(0);
            Log.e("getDetails: ", jsonObject.toString());
            ArrayList<String> details = new ArrayList<String>();
            if (jsonObject.getJSONArray("relations").length() < 1) {
                nodata_tv.setVisibility(View.VISIBLE);
                nodata_tv.setText("To Add items\nMenu->Creations");
            }
            for (int i = 0; i < jsonObject.getJSONArray("relations").length(); i++) {
                details.add(jsonObject.getJSONArray("relations").get(i).toString());
            }
            AdapterRelations adapterRelations = new AdapterRelations(SHGFormActivity.this, details, 0);
            adapterRelations.notifyDataSetChanged();
            mandals_rv.setAdapter(adapterRelations);
        } catch (JSONException j) {
            j.printStackTrace();
        }
    }

    public void getDetailsDesg_() {
        Log.e("getDetails: ", "true");
        try {
            dataMandals.clear();
            JSONArray jsonArrar = new JSONArray(Session.getMemberJSON(SHGFormActivity.this));
            JSONObject jsonObject = jsonArrar.getJSONObject(0);
            Log.e("getDetails: ", jsonObject.toString());
            ArrayList<String> details = new ArrayList<String>();
            if (jsonObject.getJSONArray("designations").length() == 0) {
                nodata_tv.setVisibility(View.VISIBLE);
                nodata_tv.setText("To Add items\nMenu->Creations");
            }
            for (int i = 0; i < jsonObject.getJSONArray("designations").length(); i++) {
                details.add(jsonObject.getJSONArray("designations").get(i).toString());
            }
            AdapterRelations adapterRelations = new AdapterRelations(SHGFormActivity.this, details, 1);
            adapterRelations.notifyDataSetChanged();
            mandals_rv.setAdapter(adapterRelations);
        } catch (JSONException j) {
            j.printStackTrace();
        }
    }

    public void getMemberDetails() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String Url = Session.BASE_URL + "members/" + Session.getUserid(SHGFormActivity.this);
        Log.e("url1", Url);
        final StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.e("onResponse: ", response.toString());
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    //mandal_tv.setText(jsonArray.getJSONObject(0).getString("mandal").toString());
                    memMandalID = jsonArray.getJSONObject(0).getString("mandal");
                    mandal_tv.setText(jsonArray.getJSONObject(0).getString("mandal_title"));
                    voname_edt.setText(jsonArray.getJSONObject(0).getString("vo_name"));
//                    getMandals();
                    Log.e("mandalsID", jsonArray.getJSONObject(0).getString("mandal").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("jsonExc", e.getMessage());
                }
                Session.setMemberJSON(SHGFormActivity.this, response);
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

    public void calldelete(final String id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String Url = Session.BASE_URL + "shg_delete";
        Log.e("url1", Url);
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
                        Toast.makeText(SHGFormActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("jsonExc", e.getMessage());
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
                params.put("id", id);
                Log.e("getParams: ", params.toString());
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

    public void getDetailsDesg() {
        dataDesgnations.clear();
        desgArray.clear();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String Url = Session.BASE_URL + "designations";
        Log.e("url1", Url);
        final StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.e("onResponse: ", response.toString());
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    desgArray = new ArrayList<String>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        DataDesgnations temp = new DataDesgnations(jsonArray.getJSONObject(i));
                        dataDesgnations.add(temp);
                        desgArray.add(jsonArray.getJSONObject(i).getString("title"));
                    }
                    adapter = new ArrayAdapter<String>(SHGFormActivity.this, android.R.layout.simple_spinner_item, desgArray);
                    desg_spinner.setAdapter(adapter);
                    AdapterDesgination adapterDesgination = new AdapterDesgination(SHGFormActivity.this, dataDesgnations, 0);
                    adapterDesgination.notifyDataSetChanged();
                    mandals_rv.setAdapter(adapterDesgination);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("jsonExc", e.getMessage());
                }
//                Session.setMemberJSON(SHGFormActivity.this,response);
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

    public void encodeImagetoString_lawyer() {
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute() {

            }

            ;

            @Override
            protected String doInBackground(Void... params) {
                BitmapFactory.Options options = null;
                options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                encodedString = "";

                bitmap = BitmapFactory.decodeFile(filePath, options);
//                bitmap=sample;

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Must compress the Image to reduce image size to make upload easy
                bitmap.compress(Bitmap.CompressFormat.JPEG, 40, stream);
                byte[] byte_arr = stream.toByteArray();
                // Encode Image to String
                encodedString = Base64.encodeToString(byte_arr, Base64.NO_WRAP);
                Log.e("encodedString", encodedString);

                return "";
            }

            @Override
            protected void onPostExecute(String msg) {

                // Put converted Image string into Async Http Post param
                // Trigger Image upload

//                kyc_image();


            }
        }.execute(null, null, null);
    }

    public void getTodaysList() {
        // dataMandals.clear();
        dataImages.clear();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String Url = Session.BASE_URL + "shg_ids";//shg_ids?user_id=1&type=today
        Log.e("url1", Url);
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.e("onResTdy: ", response.toString());
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    // mandal_tv.setText(jsonArray.getJSONObject(0).getString("title"));
                    if (jsonArray.length() == 0) {
                        // todayslist_title_tv.setVisibility(View.GONE);
                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        DataImages temp = new DataImages(jsonArray.getJSONObject(i));
                        dataImages.add(temp);
                    }
                    adapterTodayList = new AdapterTodayList(SHGFormActivity.this, dataImages, 0);
                    adapterTodayList.notifyDataSetChanged();
                    todays_rv.setAdapter(adapterTodayList);
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
                params.put("user_id", Session.getUserid(SHGFormActivity.this));
                params.put("type", "today");
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

    public void showSuccessAlert(final String title) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.success_popup, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        deleteDialog.setView(deleteDialogView);
       /* TextView text = (TextView) deleteDialog.findViewById(R.id.success_title_tv);
        text.setText(title);*/
       /* Button btn = (Button)deleteDialog.findViewById(R.id.ok_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
            }
        });*/
        deleteDialogView.findViewById(R.id.ok_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                deleteDialog.dismiss();
                getTodaysList();
            }
        });
        deleteDialog.show();
    }

    public void edit(final int pos, final DataImages data) {
        Log.e("edit: ", "true");
        //  capture_img.setVisibility(View.GONE);
        //   gallery_img.setVisibility(View.GONE);
        notEdit = false;
        membername_edt.setText(data.member_name);
        wof_name_edt.setText(data.rel_name);
        phone_edt.setText(data.phone);
        groupname_edt.setText(data.group_name);
        groupid_edt.setText(data.group_id);
        voname_edt.setText(data.vo_name);
        village_edt.setText(data.village_name);
        mandal_tv.setText(data.mandal_name);
        usr_img.setVisibility(View.VISIBLE);
        Picasso.get().load(data.image).into(usr_img);
        desg = data.designation;
        desg_tv.setText(data.designation);
        todayslist_title_tv.setVisibility(View.VISIBLE);
        submit_tv.setText("Update");
    }

    public void showAlert(String msg) {
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(SHGFormActivity.this);
        builder.setTitle("Alert !");
        builder.setMessage(msg);
        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}


