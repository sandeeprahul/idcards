package com.develapp.idcards;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import com.android.volley.toolbox.StringRequest;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

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

//mandals_popup_ll is mainly used as main popup (****IMP****)

public class StudentFormActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 44;
    public String class_name;
    public String section;
    EditText parent_name_edt, phoneno_edt, phone_edt, adminno_edt, address_edt,address_edt4,address_edt5, transport_edt, bloodgroup_edt;
    EditText student_name_edt, idno_edt, dob_edt, aadhar_edt,relation_edt;
    RelativeLayout class_rl, inst_name_rl, section_rl;
    TextView submit_tv, close_tv_mandals_popup, close_tv_popup, mandal_tv, title_tv_popup,relation_tv,class_edt,section_edt;
    TextView class_tv, nodata_tv, logout_tv, creation_tv, desg_tv, inst_name_tv, section_tv;
    LinearLayout desg_popup_ll, gallery_ll, capture_ll, mandals_popup_ll, calender_popup,blood_grp_ll;
    RecyclerView mandals_rv, desig_rv;
    Bitmap bitmap;
    ArrayList<String> relationsArray = new ArrayList<String>();

    RecyclerView todays_rv;
    String bloodGroup;
    ListView bloodgroup_lv;
    //    ArrayList<String> dataBldGrp = new ArrayList<String>();
    String[] dataBldGrp = {"O+", "O-", "A+", "A-",
            "B+", "B-", "AB+", "AB-"};
    TextView o_p,o_n,a_n,a_p,b_p,b_n,ab_p,ab_n;
    DatePicker simpleDatePicker;

    ArrayList<String> details = new ArrayList<String>();
    public String filePath;
    ImageView back_img;
    String inst_name = "", mandalId = "", relations = "S/O", desg = "";
    AdapterMandals adapterMandals;
    AdapterTodayListStudents adapterTodayListStudents;
    ArrayList<DataMandals> dataMandals = new ArrayList<DataMandals>();
    ImageView capture_img, gallery_img;
    ImageView usr_img;
    ArrayList<DataImages> dataImages_ = new ArrayList<DataImages>();
    ArrayList<DataStudents> dataStudents = new ArrayList<DataStudents>();
    ArrayList<DataStudents> dataStudents_ = new ArrayList<DataStudents>();
    ArrayList<String> relation = new ArrayList<String>();
    private Bitmap profile;
    private String ext;
    String encodedString = "";
    public boolean notEdit = true;
    Button okCalender;
    boolean imgChanged;
    Spinner institution_spinner, relation_spinner, desg_spinner;
    ArrayAdapter<String> adapter;

    @Override
    public void onBackPressed() {
        if (desg_popup_ll.getVisibility() == View.VISIBLE) {
            desg_popup_ll.setVisibility(View.GONE);
        } else if (mandals_popup_ll.getVisibility() == View.VISIBLE) {
            mandals_popup_ll.setVisibility(View.GONE);
        } else {
//            finish();
            super.onBackPressed();

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_studentform);

        if (ActivityCompat.checkSelfPermission(StudentFormActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(StudentFormActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(StudentFormActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        if (ActivityCompat.checkSelfPermission(StudentFormActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(StudentFormActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(StudentFormActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        simpleDatePicker = (DatePicker) findViewById(R.id.simpleDatePicker);
        institution_spinner = (Spinner) findViewById(R.id.institution_spinner);
        section_edt = (TextView) findViewById(R.id.section_edt);
        todays_rv = (RecyclerView) findViewById(R.id.todays_rv);
        class_edt = (TextView) findViewById(R.id.class_edt);
        aadhar_edt = (EditText) findViewById(R.id.aadhar_edt);
        dob_edt = (EditText) findViewById(R.id.dob_edt);
        idno_edt = (EditText) findViewById(R.id.idno_edt);
        relation_edt = (EditText) findViewById(R.id.relation_edt);
        okCalender = (Button) findViewById(R.id.okCalender);
        student_name_edt = (EditText) findViewById(R.id.student_name_edt);
        section_tv = (TextView) findViewById(R.id.section_tv);
        o_p = (TextView) findViewById(R.id.o_p);
        o_n = (TextView) findViewById(R.id.o_n);
        a_p = (TextView) findViewById(R.id.a_p);
        b_p = (TextView) findViewById(R.id.b_p);
        ab_p = (TextView) findViewById(R.id.ab_p);
        a_n = (TextView) findViewById(R.id.a_n);
        b_n = (TextView) findViewById(R.id.b_n);
        ab_n = (TextView) findViewById(R.id.ab_n);
        blood_grp_ll = (LinearLayout) findViewById(R.id.blood_grp_ll);
        desg_tv = (TextView) findViewById(R.id.desg_tv);
        inst_name_tv = (TextView) findViewById(R.id.inst_name_tv);
        logout_tv = (TextView) findViewById(R.id.logout_tv);
        creation_tv = (TextView) findViewById(R.id.creation_tv);
        nodata_tv = (TextView) findViewById(R.id.nodata_tv);
        class_tv = (TextView) findViewById(R.id.class_tv);
        section_rl = (RelativeLayout) findViewById(R.id.section_rl);
        back_img = (ImageView) findViewById(R.id.back_img);
        desig_rv = (RecyclerView) findViewById(R.id.desig_rv);
        close_tv_popup = (TextView) findViewById(R.id.close_tv_popup);
        close_tv_mandals_popup = (TextView) findViewById(R.id.close_tv_mandals_popup);
        mandals_popup_ll = (LinearLayout) findViewById(R.id.mandals_popup_ll);
        calender_popup = (LinearLayout) findViewById(R.id.calender_popup);
        desg_popup_ll = (LinearLayout) findViewById(R.id.desg_popup_ll);
        parent_name_edt = (EditText) findViewById(R.id.parent_name_edt);
        phoneno_edt = (EditText) findViewById(R.id.phoneno_edt);
        mandal_tv = (TextView) findViewById(R.id.mandal_tv);
        mandals_rv = (RecyclerView) findViewById(R.id.mandals_rv);
        phone_edt = (EditText) findViewById(R.id.phone_edt);
        title_tv_popup = (TextView) findViewById(R.id.title_tv_popup);
        adminno_edt = (EditText) findViewById(R.id.adminno_edt);
        address_edt = (EditText) findViewById(R.id.address_edt);
        address_edt5 = (EditText) findViewById(R.id.address_edt5);
        address_edt4 = (EditText) findViewById(R.id.address_edt4);
        transport_edt = (EditText) findViewById(R.id.transport_edt);
        bloodgroup_edt = (EditText) findViewById(R.id.bloodgroup_edt);
        class_rl = (RelativeLayout) findViewById(R.id.class_rl);
        inst_name_rl = (RelativeLayout) findViewById(R.id.inst_name_rl);
        usr_img = (ImageView) findViewById(R.id.usr_img);
        capture_img = (ImageView) findViewById(R.id.capture_img);
        gallery_img = (ImageView) findViewById(R.id.gallery_img);
        submit_tv = (TextView) findViewById(R.id.submit_tv);
        capture_ll = (LinearLayout) findViewById(R.id.capture_ll);
        gallery_ll = (LinearLayout) findViewById(R.id.gallery_ll);
        bloodgroup_lv = (ListView)findViewById(R.id.bloodgroup_lv);
        relation_tv = (TextView)findViewById(R.id.relation_tv);

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.item_bldgrp,R.id.textView, dataBldGrp);
        bloodgroup_lv.setAdapter(adapter);
        bloodgroup_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                blood_grp_ll.setVisibility(View.GONE);
                bloodGroup = dataBldGrp[position];
                bloodgroup_edt.setText(dataBldGrp[position]);

            }
        });


        relation_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDetailsRelations();
//                institution_spinner.setVisibility(View.VISIBLE);
//                institution_spinner.performClick();
                title_tv_popup.setText("Select Relation");
                mandals_popup_ll.setVisibility(View.VISIBLE);
            }
        });
//        TextView o_p,o_n,a_n,a_p,b_p,b_n,ab_p,ab_n;

        o_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blood_grp_ll.setVisibility(View.GONE);
                bloodGroup = "o_p";
                bloodgroup_edt.setText("O+");
            }
        });
        o_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blood_grp_ll.setVisibility(View.GONE);
                bloodGroup = "o_n";
                bloodgroup_edt.setText("O-");

            }
        });
        a_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blood_grp_ll.setVisibility(View.GONE);
                bloodGroup = "a_p";
                bloodgroup_edt.setText("A+");

            }
        });
        b_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blood_grp_ll.setVisibility(View.GONE);
                bloodGroup = "b_p";
                bloodgroup_edt.setText("B+");

            }
        });
        ab_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blood_grp_ll.setVisibility(View.GONE);
                bloodGroup = "ab_p";
                bloodgroup_edt.setText("AB+");

            }
        });
        a_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blood_grp_ll.setVisibility(View.GONE);
                bloodGroup = "a_n";
                bloodgroup_edt.setText("A-");

            }
        });
        b_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blood_grp_ll.setVisibility(View.GONE);
                bloodGroup = "b_n";
                bloodgroup_edt.setText("B-");

            }
        });
        ab_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blood_grp_ll.setVisibility(View.GONE);
                bloodGroup = "ab_n";
                bloodgroup_edt.setText("AB-");

            }
        });
        bloodgroup_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blood_grp_ll.setVisibility(View.VISIBLE);
            }
        });
        institution_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("onItemSelected: ", "" + details.get(position) + "");
                inst_name_tv.setText(details.get(position));
                inst_name = details.get(position);
                institution_spinner.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        getMemberDetails();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(StudentFormActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mandals_rv.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(StudentFormActivity.this);
        linearLayoutManager1.setOrientation(RecyclerView.VERTICAL);
        todays_rv.setLayoutManager(linearLayoutManager1);
        dob_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calender_popup.setVisibility(View.VISIBLE);
            }
        });

        okCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calender_popup.setVisibility(View.GONE);
                String day = "" + simpleDatePicker.getDayOfMonth();
                String month = "" + (simpleDatePicker.getMonth() + 1);
                String year = "" + simpleDatePicker.getYear();
                dob_edt.setText(day + "-" + month + "-" + year);
            }
        });
        inst_name_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDetailsInstitutions();
//                institution_spinner.setVisibility(View.VISIBLE);
//                institution_spinner.performClick();
//                relation_spinner
                title_tv_popup.setText("Select Instution");
                mandals_popup_ll.setVisibility(View.VISIBLE);
            }
        });
        class_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title_tv_popup.setText("Select Class");
                mandals_popup_ll.setVisibility(View.VISIBLE);
                getDetailsClasses();
            }
        });
        section_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDetailsSections();
                title_tv_popup.setText("Select Section");
                mandals_popup_ll.setVisibility(View.VISIBLE);
            }
        });
        capture_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notEdit) {
                    ImagePicker.Companion.with(StudentFormActivity.this).cameraOnly().compress(512).crop().start();
                } else {
                    ImagePicker.Companion.with(StudentFormActivity.this).cameraOnly().compress(512).crop().start();
                    imgChanged = true;
                }
            }
        });
        gallery_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);*/
                if (notEdit) {
                    ImagePicker.Companion.with(StudentFormActivity.this).galleryOnly().compress(512).crop().start();
                } else {
                    ImagePicker.Companion.with(StudentFormActivity.this).galleryOnly().compress(512).crop().start();
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

                if (class_edt.getText().toString().equals("")) {
                    showAlert("Please Enter Class");
//                    Toast.makeText(SHGFormActivity.this,"Please Enter MemberName",Toast.LENGTH_SHORT).show();
                }
             /*   else if (relations.equals("")){
                    Toast.makeText(SHGFormActivity.this,"Please Select RelationType\nEx: W/O , S/O",Toast.LENGTH_SHORT).show();
                }*/
                else if (section_edt.getText().toString().equals("")) {
                    showAlert("Please Enter Section");
//                    Toast.makeText(SHGFormActivity.this,"Please Enter RelationName",Toast.LENGTH_SHORT).show();
                } else if (student_name_edt.getText().toString().equals("")) {
                    showAlert("Please enter Student name");
//                    Toast.makeText(SHGFormActivity.this,"Phone number must be 10 numbers",Toast.LENGTH_SHORT).show();
                } else if (relation_edt.getText().toString().equals("")) {
                    showAlert("Please Enter Parent name");
//                    Toast.makeText(SHGFormActivity.this,"Please Enter Group Name",Toast.LENGTH_SHORT).show();
                } else if (phoneno_edt.getText().toString().length() != 10) {
                    showAlert("Phone number must be 10 numbers");
//                    Toast.makeText(SHGFormActivity.this,"Please Enter GroupID",Toast.LENGTH_SHORT).show();
                } else {
                    if (notEdit) {
                        if (encodedString.equals("")) {
                            showAlert("Please Upload Image");
                        } else {
                            sendDetails();
                        }
                    } else {
                        sendDetails();
                    }
//                    if (noof_cards_edt.getText().toString().length() != 0) {
//                        Session.setNoofIds(SHGFormActivity.this, noof_cards_edt.getText().toString());
//                    }
                }
                //sendImage("s");
            }
        });


        if (getIntent().hasExtra("data")) {
            DataStudents temp = (DataStudents) getIntent().getSerializableExtra("data");
            dataStudents_.add(temp);

            edit(Integer.parseInt(getIntent().getStringExtra("pos")), temp);
        }
        getTodaysList();

    }

    public void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //IMAGE CAPTURE CODE
        startActivityForResult(intent, 0);
    }

    public void getDetailsRelations() {
        Log.e("getDetails: ", "true");
        try {
            dataMandals.clear();
            JSONArray jsonArrar = new JSONArray(Session.getMemberJSON(StudentFormActivity.this));
            JSONObject jsonObject = jsonArrar.getJSONObject(0);
            Log.e("getDetails: ", jsonObject.toString());
            ArrayList<String> details = new ArrayList<String>();
            relationsArray = new ArrayList<String>();
            if (jsonObject.getJSONArray("relations").length() == 0) {
                nodata_tv.setVisibility(View.VISIBLE);
                nodata_tv.setText("To Add items\nMenu->Creations");
                // showAlert("To Add RelationType\nMenu->Creations");
//                relation_spinner.setVisibility(View.GONE);
            }
            for (int i = 0; i < jsonObject.getJSONArray("relations").length(); i++) {
                details.add(jsonObject.getJSONArray("relations").get(i).toString());
                relationsArray.add(jsonObject.getJSONArray("relations").get(i).toString());
            }
            adapter = new ArrayAdapter<String>(StudentFormActivity.this, R.layout.item_bldgrp,R.id.textView, relationsArray);
//            relation_spinner.setAdapter(adapter);
            AdapterRelations adapterRelations = new AdapterRelations(StudentFormActivity.this, details, 11);
            adapterRelations.notifyDataSetChanged();
            mandals_rv.setAdapter(adapterRelations);
        } catch (JSONException j) {
            j.printStackTrace();
        }
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
                Log.e("onResponse: ", response.toString());
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        DataMandals temp = new DataMandals(jsonArray.getJSONObject(i));
                        dataMandals.add(temp);
                    }
                    adapterMandals = new AdapterMandals(StudentFormActivity.this, dataMandals, 1);
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

    public void sendDetails() {
        try {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please Wait....");
            progressDialog.show();
            progressDialog.setCancelable(false);
            String Url = Session.BASE_URL + "register_student";
            Log.e("url1", Url);
            final JSONObject jsonBody = new JSONObject();
            jsonBody.put("user_id", Session.getUserid(StudentFormActivity.this));//6154043824,school_id, pickup_address pickup_latitude pickup_longitude subscription_duration amount_paid
            // params.put("member_name", membername_edt.getText().toString());//271012306
            jsonBody.put("student_name", student_name_edt.getText().toString());
            jsonBody.put("rel_type",relations);
            jsonBody.put("parent_name", relation_edt.getText().toString());
            jsonBody.put("phone", phoneno_edt.getText().toString());
            jsonBody.put("admin_number", adminno_edt.getText().toString());
            jsonBody.put("address", ""+address_edt.getText().toString()+","+address_edt4.getText().toString()+","+address_edt5.getText().toString());
            jsonBody.put("transport", transport_edt.getText().toString());
            jsonBody.put("blood_group", bloodGroup);
            jsonBody.put("id_number", idno_edt.getText().toString());
            jsonBody.put("dob", dob_edt.getText().toString());
            jsonBody.put("aadhar", aadhar_edt.getText().toString());
            jsonBody.put("inst_name", inst_name);
            // jsonBody.put("class", class_name);
            jsonBody.put("class", class_edt.getText().toString());
            //  jsonBody.put("section", section);
            jsonBody.put("section", section_edt.getText().toString());
            if (!notEdit) {
                jsonBody.put("student_id_update", dataStudents_.get(0).id);
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
            final String mRequestBody = jsonBody.toString();
            Log.e("sendDetails: ", mRequestBody);
            StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Log.e("onResponse: ", response.toString());
 /*{"status": "Success","shg_id": "5","message": "Posted Successfully"
   }*/
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("status").equals("Success")) {
                            submit_tv.setEnabled(true);
                            Toast.makeText(StudentFormActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            showSuccessAlert(jsonObject.getString("message"));
//                            Session.setNoofIds(StudentFormActivity.this, noof_cards_edt.getText().toString());

                            clearFields();
                            usr_img.setVisibility(View.GONE);
                            capture_ll.setVisibility(View.VISIBLE);
                            Log.e("onResponse: ", jsonObject.toString());
                            // sendImage(jsonObject.getString("student_id"));
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
        inst_name_tv.setText(R.string.instname);
        class_edt.setText(R.string.class_);
        phoneno_edt.getText().clear();
//        relation_edt.getText().clear();
//        relation_tv.setText("Relation Type");
        section_edt.setText(R.string.section);
        student_name_edt.getText().clear();
        bloodgroup_edt.getText().clear();
        relation_edt.getText().clear();
        student_name_edt.setHint(R.string.stud);
        parent_name_edt.getText().clear();
        adminno_edt.getText().clear();
        address_edt.getText().clear();
        address_edt4.getText().clear();
        address_edt5.getText().clear();
        transport_edt.getText().clear();
        aadhar_edt.getText().clear();
        idno_edt.getText().clear();
        dob_edt.getText().clear();
        // desg_tv.setText(getResources().getString(R.string.desig));
        usr_img.setVisibility(View.GONE);
        capture_ll.setVisibility(View.VISIBLE);
        gallery_ll.setVisibility(View.VISIBLE);
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

    public void getTodaysList() {
        dataStudents.clear();
        //dataImages.clear();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String Url = Session.BASE_URL + "student_ids";//shg_ids?user_id=1&type=today
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
                        DataStudents temp = new DataStudents(jsonArray.getJSONObject(i));
                        dataStudents.add(temp);
                    }
                    adapterTodayListStudents = new AdapterTodayListStudents(StudentFormActivity.this, dataStudents, 0);
                    adapterTodayListStudents.notifyDataSetChanged();
                    todays_rv.setAdapter(adapterTodayListStudents);
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
                params.put("user_id", Session.getUserid(StudentFormActivity.this));
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


    public void edit(final int pos, final DataStudents data) {
        Log.e("edit: ", "true");
        //  capture_img.setVisibility(View.GONE);
        //   gallery_img.setVisibility(View.GONE);
        notEdit = false;
        student_name_edt.setText(data.student_name);
        inst_name_tv.setText(data.inst_name);
        phoneno_edt.setText(data.phone);
        class_edt.setText(data.class_);
        section_edt.setText(data.section);
        relation_edt.setText(data.parent_name);
        adminno_edt.setText(data.admin_number);
        address_edt.setText(data.address);
        transport_edt.setText(data.transport);
        bloodgroup_edt.setText(data.blood_group);

        idno_edt.setText(data.id_number);
        dob_edt.setText(data.dob);
        aadhar_edt.setText(data.aadhar);
        usr_img.setVisibility(View.VISIBLE);
        Picasso.get().load(data.image).into(usr_img);

        inst_name = data.inst_name;

        // desg = data.designation;
        // desg_tv.setText(data.designation);
        //todayslist_title_tv.setVisibility(View.VISIBLE);
        submit_tv.setText("Update");
    }


    public void getDetailsInstitutions() {
        Log.e("getDetails: ", "true");
        try {
            details.clear();
            dataMandals.clear();
            JSONArray jsonArrar = new JSONArray(Session.getMemberJSON(StudentFormActivity.this));
            JSONObject jsonObject = jsonArrar.getJSONObject(0);
            Log.e("getDetails: ", jsonObject.toString());

            details = new ArrayList<String>();
            if (jsonObject.getJSONArray("inst_names").length() < 1) {
                nodata_tv.setVisibility(View.VISIBLE);
                nodata_tv.setText("To Add items\nMenu->Creations");
                showAlert("To Add Institutions\nMenu->Creations");
//                desg_spinner.setVisibility(View.INVISIBLE);
            }
            for (int i = 0; i < jsonObject.getJSONArray("inst_names").length(); i++) {
                details.add(jsonObject.getJSONArray("inst_names").get(i).toString());
            }
            adapter = new ArrayAdapter<String>(StudentFormActivity.this, R.layout.item_bldgrp,R.id.textView, details);
            institution_spinner.setAdapter(adapter);
            AdapterRelations adapterRelations = new AdapterRelations(StudentFormActivity.this, details, 2);
            adapterRelations.notifyDataSetChanged();

            mandals_rv.setAdapter(adapterRelations);


        } catch (JSONException j) {
            j.printStackTrace();
        }
    }

    public void getDetailsClasses() {
        Log.e("getDetails: ", "true");
        try {
            dataMandals.clear();
            JSONArray jsonArrar = new JSONArray(Session.getMemberJSON(StudentFormActivity.this));
            JSONObject jsonObject = jsonArrar.getJSONObject(0);
            Log.e("getDetails: ", jsonObject.toString());

            ArrayList<String> details = new ArrayList<String>();
            if (jsonObject.getJSONArray("classes").length() == 0) {
                nodata_tv.setVisibility(View.VISIBLE);
                nodata_tv.setText("To Add items\nMenu->Creations");
            }
            for (int i = 0; i < jsonObject.getJSONArray("classes").length(); i++) {
                details.add(jsonObject.getJSONArray("classes").get(i).toString());
            }
            AdapterRelations adapterRelations = new AdapterRelations(StudentFormActivity.this, details, 3);
            adapterRelations.notifyDataSetChanged();

            mandals_rv.setAdapter(adapterRelations);

        } catch (JSONException j) {
            j.printStackTrace();
        }
    }

    public void getDetailsSections() {
        Log.e("getDetails: ", "true");
        try {
            dataMandals.clear();
            JSONArray jsonArrar = new JSONArray(Session.getMemberJSON(StudentFormActivity.this));
            JSONObject jsonObject = jsonArrar.getJSONObject(0);
            Log.e("getDetails: ", jsonObject.toString());

            ArrayList<String> details = new ArrayList<String>();
            if (jsonObject.getJSONArray("sections").length() == 0) {
                nodata_tv.setVisibility(View.VISIBLE);
                nodata_tv.setText("To Add items\nMenu->Creations");
            }
            for (int i = 0; i < jsonObject.getJSONArray("sections").length(); i++) {
                details.add(jsonObject.getJSONArray("sections").get(i).toString());
            }
            AdapterRelations adapterRelations = new AdapterRelations(StudentFormActivity.this, details, 4);
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
        String Url = Session.BASE_URL + "members/" + Session.getUserid(StudentFormActivity.this);
        Log.e("url1", Url);


        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.e("onResponse: ", response.toString());
                Session.setMemberJSON(StudentFormActivity.this, response);
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

    public void showAlert(String msg) {
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(StudentFormActivity.this);

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

    public void calldelete(final String id) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String Url = Session.BASE_URL + "student_delete";
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
                        Toast.makeText(StudentFormActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
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

}


//{"user_id":"376","student_name":"test","parent_name":"Test",
// "phone":"9876543210","admin_number":"123123",
// "address":"Vijayawada","transport":"Gunturn",
// "blood_group":"A+","id_number":"11111","dob":"01\/01\/1995","aadhar":"123456789012","inst_name":"PVRS",
// "class":"1st Class","section":"A Section","student_id_update":"2","file":"","ext":""}