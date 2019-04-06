package kpopstationaplicativo_kpopstation_android_kpopstation_facadownload.com.br.kpopstation;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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


public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener {


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
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Set the home as default
        Fragment fragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit();

//posts

        ConnectivityManager CN = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

//        if (CN.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
//                || CN.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
//        {
//
//            postList = (ListView)findViewById(R.id.postList);
//            progressDialog = new ProgressDialog(MenuActivity.this);
//            progressDialog.setMessage("Carregando...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.show();
//
//            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String s) {
//                    gson = new Gson();
//                    list = (List) gson.fromJson(s, List.class);
//                    postTitle = new String[list.size()];
//
//                    for(int i=0;i<list.size();++i){
//                        mapPost = (Map<String,Object>)list.get(i);
//                        mapTitle = (Map<String, Object>) mapPost.get("title");
//                        postTitle[i] = (String) mapTitle.get("rendered");
//                    }
//
//                    postList.setAdapter(new ArrayAdapter(MenuActivity.this,android.R.layout.simple_list_item_1,postTitle));
//                    progressDialog.dismiss();
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError volleyError) {
//                    Toast.makeText(MenuActivity.this, "Sua rede demorou ao sincronizar, pressione 'voltar'", Toast.LENGTH_LONG).show();
//                }
//            });
//
//            RequestQueue rQueue = Volley.newRequestQueue(MenuActivity.this);
//            rQueue.add(request);
//
//            postList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    mapPost = (Map<String,Object>)list.get(position);
//                    postID = ((Double)mapPost.get("id")).intValue();
//
//                    Intent intent = new Intent(getApplicationContext(),PostActivity.class);
//                    intent.putExtra("id", ""+postID);
//                    intent.putExtra("title" , "" + postTitle);
//                    startActivity(intent);
//                }
//            });
//
//        }
//        else if (CN.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
//                || CN.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED)
//        {
//            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MenuActivity.this);
//            alertDialogBuilder.setTitle("Problemas de rede");
//            alertDialogBuilder
//                    .setMessage("Clique em sim para fechar")
//                    .setCancelable(false)
//                    .setPositiveButton("Sim",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    moveTaskToBack(true);
//                                    android.os.Process.killProcess(android.os.Process.myPid());
//                                    System.exit(1);
//                                }
//                            })
//
//                    .setNegativeButton("Não", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//
//                            dialog.cancel();
//                            Intent mandaparahomedenovo = new Intent(MenuActivity.this, MainActivity.class);
//                            startActivity(mandaparahomedenovo);
//                        }
//                    });
//
//            AlertDialog alertDialog = alertDialogBuilder.create();
//            alertDialog.show();
//        }
//        else {
//
//            Toast.makeText(this, "Sem rede", Toast.LENGTH_SHORT).show();
//            System.exit(1);
//
//        }

        //end

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            return;
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // Create a new fragment and specify the planet to show based on position
        Fragment fragment;

        if (id == R.id.nav_home) {
            // Home
            fragment = new HomeFragment();
        }
        else if (id == R.id.nav_settings) {
            // eventos
            fragment = new SettingsFragment();
        }
        else if (id == R.id.nav_estudo) {

            fragment = new AulasFragment();
        }
        else if (id == R.id.nav_social) {

            fragment = new SeguirFragment();
        }
        else if (id == R.id.nav_produtos) {

            fragment = new ProdutosFragment();
        }
        else if (id == R.id.nav_promo) {

            fragment = new PromocoesFragment();
        }
        else if (id == R.id.nav_pp) {

            fragment = new ParceirosFragment();
        }
        else if(id == R.id.sobre_nav){
            fragment = new SobreFragment();
        }
        else {
            fragment = new HomeFragment();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

