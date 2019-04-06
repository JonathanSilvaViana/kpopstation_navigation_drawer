package kpopstationaplicativo_kpopstation_android_kpopstation_facadownload.com.br.kpopstation;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

public class Posts extends AppCompatActivity {

    String url = "https://www.kpopstation.com.br/wp-json/wp/v2/posts?filter[posts_per_page]=10&fields=id,title,date";
    //http ou https
    List<Object> list;
    Gson gson;
    ProgressDialog progressDialog;
    ListView postList;
    Map<String,Object> mapPost;
    Map<String,Object> mapTitle;
    int postID;
    String postTitle[];
    FloatingActionButton VoltarView;
    String titlePG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        ConnectivityManager CN = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (CN.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || CN.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {

            postList = (ListView)findViewById(R.id.postList);
            progressDialog = new ProgressDialog(Posts.this);
            progressDialog.setMessage("Carregando...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();

            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    gson = new Gson();
                    list = (List) gson.fromJson(s, List.class);
                    postTitle = new String[list.size()];

                    for(int i=0;i<list.size();++i){
                        mapPost = (Map<String,Object>)list.get(i);
                        mapTitle = (Map<String, Object>) mapPost.get("title");
                        postTitle[i] = (String) mapTitle.get("rendered");
                    }

                    postList.setAdapter(new ArrayAdapter(Posts.this,android.R.layout.simple_list_item_1,postTitle));
                    progressDialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(Posts.this, "Sua rede demorou ao sincronizar, pressione 'voltar'", Toast.LENGTH_LONG).show();
                }
            });

            RequestQueue rQueue = Volley.newRequestQueue(Posts.this);
            rQueue.add(request);

            postList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mapPost = (Map<String,Object>)list.get(position);
                    postID = ((Double)mapPost.get("id")).intValue();

                    Intent intent = new Intent(getApplicationContext(),PostActivity.class);
                    intent.putExtra("id", ""+postID);
                    //intent.putExtra("title" , "" + postTitle);
                    startActivity(intent);
                }
            });

        }
        else if (CN.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                || CN.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED)
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Posts.this);
            alertDialogBuilder.setTitle("Problemas de rede");
            alertDialogBuilder
                    .setMessage("Clique em sim para fechar")
                    .setCancelable(false)
                    .setPositiveButton("Sim",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    moveTaskToBack(true);
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                    System.exit(1);
                                }
                            })

                    .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                            Intent mandaparahomedenovo = new Intent(Posts.this, MainActivity.class);
                            startActivity(mandaparahomedenovo);
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else {

            Toast.makeText(this, "Sem rede", Toast.LENGTH_SHORT).show();
            System.exit(1);

        }

    }
}
