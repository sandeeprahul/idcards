package com.develapp.idcards;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PDFActivity extends AppCompatActivity {

    ImageView back_img;
    TextView no_data_tv;
    RecyclerView todays_rv;
    AdapterImages adapterImages;
    ArrayList<DataImages> dataImages = new ArrayList<DataImages>();
    WebView webview;
    TextView openinbrower_tv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdflist);


        back_img = (ImageView)findViewById(R.id.back_img);
        webview = (WebView)findViewById(R.id.webview);
        openinbrower_tv = (TextView)findViewById(R.id.openinbrower_tv);
        if (Build.VERSION.SDK_INT >= 19) {
            webview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        final ProgressDialog pd = ProgressDialog.show(PDFActivity.this, "", "Please wait...", false);
        pd.setCancelable(false);
        pd.show();
        //gen_employee_pdf
        //gen_student_pdf
        String s="";
        switch (Session.getType(PDFActivity.this)) {
            case "0":
                s = "corecode/gen_pdf.php?id=";
                break;
            case "1":
                s = "corecode/gen_student_pdf.php?id=";

                break;
            case "2":
                s = "corecode/gen_employee_pdf.php?id=";

                break;
            case "3":
                s= "corecode/gen_nregs_pdf.php?id=";
        }


        final String Url = Session.PDF_URL + s+Session.getUserid(PDFActivity.this);//shg_ids?user_id=1&type=today

        openinbrower_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Url));
                startActivity(browserIntent);
            }
        });

        if (Session.getType(PDFActivity.this).equals("0")){

        }
        else {

        }

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Log.e( "onCreate: ", Url);
        webview.getSettings().setDomStorageEnabled(true); // Ad
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                pd.show();
                pd.dismiss();
               view.loadUrl(url);
                return false;
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
//                pd.show();
                pd.dismiss();
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                pd.dismiss();
            }
        });
      //  webview.loadUrl("https://docs.google.com/gview?embedded=true&url="+Url);
        // https://idcards.yellowsoft.in/corecode/gen_pdf.php?id=10
        webview.loadUrl(Url);


    }



}
