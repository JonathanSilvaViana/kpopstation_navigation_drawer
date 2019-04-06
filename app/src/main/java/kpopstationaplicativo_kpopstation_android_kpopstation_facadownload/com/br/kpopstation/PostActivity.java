package kpopstationaplicativo_kpopstation_android_kpopstation_facadownload.com.br.kpopstation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;

import java.util.Map;

public class PostActivity extends AppCompatActivity {

    TextView title;
    WebView content;
    ProgressDialog progressDialog;
    Gson gson;
    Map<String, Object> mapPost;
    Map<String, Object> mapTitle;
    Map<String, Object> mapContent;

    AdView mAdView;
    String titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        //ads

        MobileAds.initialize(this,
                "ca-app-pub-2931850328324162~3800224969");

        //banner ad
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        titulo = "Matéria selecionada";

        setTitle(titulo);

        final String id = getIntent().getExtras().getString("id");

        title = (TextView) findViewById(R.id.title);
        content = (WebView)findViewById(R.id.content);
        content.getSettings().setJavaScriptEnabled(true);

        progressDialog = new ProgressDialog(PostActivity.this);
        progressDialog.setMessage("Carregando...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        String url = "https://www.kpopstation.com.br/wp-json/wp/v2/posts/"+id+"?fields=title,content,date";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                gson = new Gson();
                mapPost = (Map<String, Object>) gson.fromJson(s, Map.class);
                mapTitle = (Map<String, Object>) mapPost.get("title");
                mapContent = (Map<String, Object>) mapPost.get("content");
                //mapDataPublicado = (Map<String, Object>) mapPost.get("date");

                title.setText(mapTitle.get("rendered").toString());
                content.loadData(mapContent.get("rendered").toString(),"text/html; charset=utf-8", "utf-8");

                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                Toast.makeText(PostActivity.this, "Aproveite a leitura", Toast.LENGTH_SHORT).show();


                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                Toast.makeText(PostActivity.this, id, Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(PostActivity.this);
        rQueue.add(request);


    }}