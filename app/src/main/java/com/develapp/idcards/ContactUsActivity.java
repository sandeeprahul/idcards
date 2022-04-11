package com.develapp.idcards;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class ContactUsActivity extends AppCompatActivity {
    ImageView back_img;
    TextView phno_tv;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_contactus);

        back_img = (ImageView)findViewById(R.id.back_img);
        phno_tv = (TextView) findViewById(R.id.phno_tv);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        try {
            JSONObject jsonObject = new JSONObject(Session.getSettings(ContactUsActivity.this));
            phno_tv.setText("Contact: "+jsonObject.getJSONObject("settings").getString("phone"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
