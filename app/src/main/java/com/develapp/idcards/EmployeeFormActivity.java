package com.develapp.idcards;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import com.github.dhaval2404.imagepicker.ImagePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

import static android.content.RestrictionsManager.RESULT_ERROR;

public class EmployeeFormActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 44;
    public String class_name;
    public String section;
    EditText parent_name_edt,phoneno_edt,phone_edt,adminno_edt,address_edt,transport_edt,bloodgroup_edt;
    EditText student_name_edt,idno_edt,dob_edt,aadhar_edt;
    RelativeLayout relation_rl,inst_name_rl,desg_rl;
    TextView submit_tv,close_tv_mandals_popup,close_tv_popup,mandal_tv,title_tv_popup;
    TextView class_tv,nodata_tv,logout_tv,creation_tv,desg_tv,inst_name_tv,section_tv,relation_tv;
    LinearLayout desg_popup_ll,gallery_ll,capture_ll,mandals_popup_ll;
    RecyclerView mandals_rv,desig_rv;
    Bitmap bitmap;
    ArrayList<DataDesgnations> dataDesgnations = new ArrayList<DataDesgnations>();
    public String filePath;
    ImageView back_img;
    String inst_name="",mandalId="",relations="",desg="";
    AdapterMandals adapterMandals;
    ArrayList<DataMandals> dataMandals = new ArrayList<DataMandals>();
    ImageView capture_img,gallery_img;
    ImageView usr_img;
    ArrayList<String> relation = new ArrayList<String>();
    private Bitmap profile;
    private String ext;
    String encodedString;
    final int CROP_PIC_REQUEST_CODE = 1;
    Uri d=null;



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (desg_popup_ll.getVisibility()==View.VISIBLE){
            desg_popup_ll.setVisibility(View.GONE);
        }
        else if (mandals_popup_ll.getVisibility()==View.VISIBLE){
            mandals_popup_ll.setVisibility(View.GONE);
        }
        else {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_employeeform);

        if (ActivityCompat.checkSelfPermission(EmployeeFormActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EmployeeFormActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EmployeeFormActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        //storage wirte perimission
        if (ActivityCompat.checkSelfPermission(EmployeeFormActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EmployeeFormActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EmployeeFormActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
     /*   CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);*/

        aadhar_edt = (EditText)findViewById(R.id.aadhar_edt);
        dob_edt = (EditText)findViewById(R.id.dob_edt);
        idno_edt = (EditText)findViewById(R.id.idno_edt);
        student_name_edt = (EditText)findViewById(R.id.student_name_edt);
        relation_tv = (TextView)findViewById(R.id.relation_tv);
        section_tv = (TextView)findViewById(R.id.section_tv);
        desg_tv = (TextView)findViewById(R.id.desg_tv);
        inst_name_tv = (TextView)findViewById(R.id.inst_name_tv);
        logout_tv = (TextView)findViewById(R.id.logout_tv);
        creation_tv = (TextView)findViewById(R.id.creation_tv);
        nodata_tv = (TextView)findViewById(R.id.nodata_tv);
        class_tv = (TextView)findViewById(R.id.class_tv);
        desg_rl = (RelativeLayout)findViewById(R.id.desg_rl);
        back_img = (ImageView)findViewById(R.id.back_img);
        desig_rv = (RecyclerView)findViewById(R.id.desig_rv);
        close_tv_popup = (TextView) findViewById(R.id.close_tv_popup);
        close_tv_mandals_popup = (TextView)findViewById(R.id.close_tv_mandals_popup);
        mandals_popup_ll = (LinearLayout)findViewById(R.id.mandals_popup_ll);
        desg_popup_ll = (LinearLayout)findViewById(R.id.desg_popup_ll);
        parent_name_edt = (EditText)findViewById(R.id.parent_name_edt);
        phoneno_edt = (EditText)findViewById(R.id.phoneno_edt);
        mandal_tv = (TextView) findViewById(R.id.mandal_tv);
        mandals_rv = (RecyclerView)findViewById(R.id.mandals_rv);
        phone_edt = (EditText)findViewById(R.id.phone_edt);
        title_tv_popup = (TextView)findViewById(R.id.title_tv_popup);
        adminno_edt = (EditText)findViewById(R.id.adminno_edt);
        address_edt = (EditText)findViewById(R.id.address_edt);
        transport_edt = (EditText)findViewById(R.id.transport_edt);
        bloodgroup_edt = (EditText)findViewById(R.id.bloodgroup_edt);
        relation_rl = (RelativeLayout) findViewById(R.id.relation_rl);
        inst_name_rl = (RelativeLayout)findViewById(R.id.inst_name_rl);
        usr_img = (ImageView) findViewById(R.id.usr_img);
        capture_img = (ImageView) findViewById(R.id.capture_img);
        gallery_img = (ImageView)findViewById(R.id.gallery_img);
        submit_tv = (TextView) findViewById(R.id.submit_tv);
        capture_ll = (LinearLayout)findViewById(R.id.capture_ll);
        gallery_ll = (LinearLayout)findViewById(R.id.gallery_ll);

        //getDetails();
        getMemberDetails();
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(EmployeeFormActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mandals_rv.setLayoutManager(linearLayoutManager);
       // getMandals();

        inst_name_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // getMandals();
                getDetailsInstitutions();
                title_tv_popup.setText("Select Institution");
                mandals_popup_ll.setVisibility(View.VISIBLE);
            }
        });

        relation_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getMandals();
                title_tv_popup.setText("Select Relation Tpye");
                mandals_popup_ll.setVisibility(View.VISIBLE);
                getDetailsRelations();
            }
        });

        desg_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDetailsDesg();
                title_tv_popup.setText("Select Designation");
                mandals_popup_ll.setVisibility(View.VISIBLE);

            }
        });
        capture_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(EmployeeFormActivity.this).cameraOnly().compress(1024).crop().start();
                //takePicture();

//                ImagePicker.cameraOnly().start(EmployeeFormActivity.this) ;
            }
        });
        gallery_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               ImagePicker.Companion.with(EmployeeFormActivity.this).galleryOnly().compress(1024).crop().start();

              /*  Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);*/

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
                sendDetails();
                //sendImage("s");
            }
        });
    }
    public void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //IMAGE CAPTURE CODE
        startActivityForResult(intent, PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                Uri resultUri = result.getUri();
//                usr_img.setImageURI(resultUri);
//                usr_img.setVisibility(View.VISIBLE);
//                capture_ll.setVisibility(View.GONE);
//                gallery_ll.setVisibility(View.GONE);
//
//                filePath=result.getUri().getPath();
//
//                Log.e("pathhhhh",result.getUri().getPath());
//                String currentString = result.getUri().getPath();
//                String[] separated = currentString.split("\\.");
//                Log.e("pathhhhh",separated[separated.length-1]);
//                ext = separated[separated.length-1];
//                //filePath = currentString;
////                final InputStream imageStream;
////                bitmap=(Bitmap)data.getExtras().get("data");
////
////
////                try {
////                    imageStream = getContentResolver().openInputStream(resultUri);
////                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
////                    String encodedImage = encodeImage(selectedImage);
////                    base64Image = encodedImage;
////                    Log.e("base64img",base64Image);
////                } catch (FileNotFoundException e) {
////                    e.printStackTrace();
////                    Log.e( "onActivityResult: ", e.getMessage());
////                }
//                encodeImagetoString_lawyer();
//
//
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//            }
//        }
//        else
            if (requestCode == CROP_PIC_REQUEST_CODE) {
            if (data != null) {
                Bundle extras = data.getExtras();
                Bitmap bitmap= extras.getParcelable("data");
                usr_img.setImageBitmap(bitmap);
                usr_img.setVisibility(View.VISIBLE);
            }
        }
            else if (requestCode==ImagePicker.REQUEST_CODE&&resultCode==RESULT_OK){

                Log.e("resultOK",data.toString());
                final Bundle extras = data.getExtras();
                usr_img.setImageURI(data.getData());
                usr_img.setVisibility(View.VISIBLE);
                filePath=data.getData().getPath();

                Log.e("pathhhhh",data.getData().getPath());
                String currentString = data.getData().getPath();
                String[] separated = currentString.split("\\.");
                Log.e("pathhhhh",separated[separated.length-1]);
                ext = separated[separated.length-1];
//                Log.e("newProfilePic",newProfilePic.toString());

//               // ImagePicker.Builder res=
//                final Bundle extras = data.getExtras();
//                Bitmap newProfilePic = extras.getParcelable("data");
//                          Image image = ImagePicker.Companion.with(EmployeeFormActivity.this).;
//                usr_img.setImageURI(getImageUri_(EmployeeFormActivity.this,newProfilePic));

            }
        else if(requestCode==PICK_IMAGE){
            final Bundle extras = data.getExtras();
            if (extras != null) {
                //Get image
//                Bitmap newProfilePic = extras.getParcelable("data");
//                usr_img.setImageURI(data.getData());
//                usr_img.setVisibility(View.VISIBLE);
                Uri s = data.getData();

//                ImagePicker.Companion.with(EmployeeFormActivity.this).crop().start();
                //ImagePicker.Companion.with(EmployeeFormActivity.this).cameraOnly().crop().start();
//                doCrop(getImageUri_(EmployeeFormActivity.this,newProfilePic));
//                CropImage.activity(getImageUri_(EmployeeFormActivity.this,newProfilePic))
//                        .start(this);

            }
        }
//        else if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
//            // Get a list of picked images
//            // or get a single image only
//            Image image = ImagePicker.getFirstImageOrNull(data);
//            Log.e("image",image.getPath());
//
//            Bitmap myBitmap = BitmapFactory.decodeFile(image.getPath());
//           // getFileDataFromDrawable(image.getPath());
//
//
//
//
//
//        }


        else if (resultCode == RESULT_ERROR) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }


//    You can try this.

    private void doCrop(Uri picUri) {
        try {

            Intent cropIntent = new Intent("com.android.camera.action.CROP");

            cropIntent.setDataAndType(picUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 128);
            cropIntent.putExtra("outputY", 128);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, CROP_PIC_REQUEST_CODE);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
       // bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }
    public byte[] imaagedat(){
        Bitmap bmp = bitmap;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bmp.recycle();
        return byteArray;
    }

    public  byte[] getFileDataFromDrawable(String file_name) {
        String[] separated = file_name.split("/");

        String t=separated[separated.length-1];
        Log.e("extttt",t);
        String[] ext=t.split("\\.");
        Log.e("extttt",ext[0]);
        Log.e("extttt",ext[1]);
        Log.e("file_title_org",file_name);
        String file_title="";
        int p=0;
        for(int i=0;i<separated.length;i++){
            if(separated[i].toLowerCase().equals("storage")){
                file_title="/"+separated[i];
                p=1;
            }else{
                if(p==1){
                    file_title=file_title+"/"+separated[i];
                }
            }
        }
        Log.e("file_title",file_title);
        File file = new File(file_title);
        int size = (int) file.length();
        Log.e("sizeeee",file.getAbsolutePath());
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
        Log.e("byteeee",String.valueOf(bytes.length));
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
                    adapterMandals = new AdapterMandals(EmployeeFormActivity.this,dataMandals,1);
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
    public void  sendDetails() {


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String Url = Session.BASE_URL + "student_id";
        Log.e("url1",Url);


        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.e("onResponse: ", response.toString());
//
 /*{"status": "Success","shg_id": "5","message": "Posted Successfully"
   }*/
                try {
                     JSONObject jsonObject = new JSONObject(response);
                     if (jsonObject.getString("status").equals("Success")){
                         //sendImage(jsonObject.getString("student_id"));
                     }

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

                params.put("user_id", Session.getUserid(EmployeeFormActivity.this));//6154043824,school_id, pickup_address pickup_latitude pickup_longitude subscription_duration amount_paid
               // params.put("member_name", membername_edt.getText().toString());//271012306
                params.put("student_name",student_name_edt.getText().toString() );
                params.put("parent_name",parent_name_edt.getText().toString() );
                params.put("rel_type", relations);
                params.put("phone", phone_edt.getText().toString());
                params.put("adminno", adminno_edt.getText().toString());
                params.put("address", address_edt.getText().toString());
                params.put("transport", transport_edt.getText().toString());
                params.put("bloodgrp", bloodgroup_edt.getText().toString());
                params.put("idno", idno_edt.getText().toString());
                params.put("dob", dob_edt.getText().toString());
                params.put("aadhar", aadhar_edt.getText().toString());
                params.put("instname", inst_name);
                /*params.put("class", class_name);
                params.put("section", section);*/
                return params;
            }

        };

        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);

    }




public void getDetailsInstitutions()  {
    Log.e( "getDetails: ", "true");
        try {
            dataMandals.clear();
            JSONArray jsonArrar = new JSONArray(Session.getMemberJSON(EmployeeFormActivity.this));
            JSONObject jsonObject = jsonArrar.getJSONObject(0);
            Log.e("getDetails: ", jsonObject.toString());

            ArrayList<String> details = new ArrayList<String>();
            if (jsonObject.getJSONArray("inst_names").length()<1){
                nodata_tv.setVisibility(View.VISIBLE);
                nodata_tv.setText("To Add items\nMenu->Creations");
            }
            for (int i = 0; i < jsonObject.getJSONArray("inst_names").length(); i++) {
                details.add(jsonObject.getJSONArray("inst_names").get(i).toString());
            }
            AdapterRelations adapterRelations = new AdapterRelations(EmployeeFormActivity.this, details,5);
            adapterRelations.notifyDataSetChanged();

            mandals_rv.setAdapter(adapterRelations);



        }
        catch (JSONException j){
            j.printStackTrace();
        }
}

    public void getDetailsRelations()  {
        Log.e( "getDetails: ", "true");
        try {
            dataMandals.clear();
            JSONArray jsonArrar = new JSONArray(Session.getMemberJSON(EmployeeFormActivity.this));
            JSONObject jsonObject = jsonArrar.getJSONObject(0);
            Log.e("getDetails: ", jsonObject.toString());

            ArrayList<String> details = new ArrayList<String>();
            if (jsonObject.getJSONArray("relations").length()==0){
                nodata_tv.setVisibility(View.VISIBLE);
                nodata_tv.setText("To Add items\nMenu->Creations");
            }
            for (int i = 0; i < jsonObject.getJSONArray("relations").length(); i++) {
                details.add(jsonObject.getJSONArray("relations").get(i).toString());
            }
            AdapterRelations adapterRelations = new AdapterRelations(EmployeeFormActivity.this, details,6);
            adapterRelations.notifyDataSetChanged();

            mandals_rv.setAdapter(adapterRelations);

        }
        catch (JSONException j){
            j.printStackTrace();
        }
    }
    public void clearFields() {
        submit_tv.setText("Submit");
//        inst_name_tv.setText(R.string.instname);
//        inst_name_tv;
        inst_name_tv.setText("Company Name");
        phoneno_edt.getText().clear();
//        emp_code_edt.getText().clear();
        bloodgroup_edt.getText().clear();
        student_name_edt.setText(R.string.stud);
        parent_name_edt.getText().clear();
        adminno_edt.getText().clear();
        address_edt.getText().clear();
        transport_edt.getText().clear();
        aadhar_edt.getText().clear();

        idno_edt.getText().clear();
        dob_edt.getText().clear();
        // desg_tv.setText(getResources().getString(R.string.desig));
        usr_img.setVisibility(View.GONE);
        capture_ll.setVisibility(View.VISIBLE);
        gallery_ll.setVisibility(View.VISIBLE);
    }
    public void  getDetailsDesg() {
        dataDesgnations.clear();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String Url = Session.BASE_URL + "designations";
        Log.e("url1",Url);


        final StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.e("onResponse: ", response.toString());
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        DataDesgnations temp = new DataDesgnations(jsonArray.getJSONObject(i));
                        dataDesgnations.add(temp);
                    }
                    AdapterDesgination adapterDesgination = new AdapterDesgination(EmployeeFormActivity.this, dataDesgnations,2);
                    adapterDesgination.notifyDataSetChanged();
                    mandals_rv.setAdapter(adapterDesgination);

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



    public void  getMemberDetails() {


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String Url = Session.BASE_URL + "members/"+Session.getUserid(EmployeeFormActivity.this);
        Log.e("url1",Url);


        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.e("onResponse: ", response.toString());
                Session.setMemberJSON(EmployeeFormActivity.this,response);
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

                bitmap = BitmapFactory.decodeFile(filePath , options);
//                bitmap=sample;

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Must compress the Image to reduce image size to make upload easy
                bitmap.compress(Bitmap.CompressFormat.JPEG, 40, stream);
                byte[] byte_arr = stream.toByteArray();
                // Encode Image to String
                encodedString = Base64.encodeToString(byte_arr, Base64.NO_WRAP);
                Log.e("encodedString",encodedString);

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

}


