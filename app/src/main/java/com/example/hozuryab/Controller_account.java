package com.example.hozuryab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.AbstractThreadedSyncAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;


import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class Controller_account extends AppCompatActivity {
    Context ctx = this;
    String user = "";
    String GET_URL = "http://194.5.195.193/load_classes.php";
    String GET_TABLE = "http://194.5.195.193/load_ctable.php";
    public DrawerLayout drawerLayout;
    GridView grid;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView ;

    String[] titles  , ids ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller_account);
        try{

            FileInputStream fIn = openFileInput("hozuryab_data_con.txt");
            InputStreamReader isr = new InputStreamReader(fIn);

            char[] inputBuffer = new char[20];

            isr.read(inputBuffer);
            user = new String(inputBuffer);
            String[] ss = user.split("!");
            user = ss[0];
        }catch(Exception e){e.printStackTrace();}


        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  "+user);

        drawerLayout = findViewById(R.id.controller_drawer_layout);
        navigationView = findViewById(R.id.controller_nac_view);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_controller_account:
                        Intent i = new Intent(ctx,Controller_profile.class);
                        startActivity(i);
                        return true;
                    case R.id.nav_controller_contact:
                        return true;

                    case R.id.nav_controller_logout:
                        Intent intent = new Intent(ctx,MainActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_controller_newClass:
                        Intent intent2 = new Intent(ctx, New_Class.class);
                        startActivity(intent2);
                        return true;
                }
                return false;
            }
        });

        get_classes getClasses = new get_classes(user);
        getClasses.execute();
        String classdata = "";
        try{
            classdata = getClasses.get().toString();
            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%5  "+classdata);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        String[] raw_class_data = classdata.split("-");
        int len = classdata.length() , i=3,j=5;
        String rawId = "",rawTitle = "";
        for(int k =0 ; k<3 ; k++)
        {
            rawId+=raw_class_data[i]+"-";
            i+=5;
            rawTitle+=raw_class_data[j]+"-";
            j+=5;

        }
        ids = rawId.split("-");
        titles = rawTitle.split("-");
        grid = findViewById(R.id.controller_classes);
        Con_grid_adapter con_grid_adapter = new Con_grid_adapter(Controller_account.this,titles,ids);
        grid.setAdapter(con_grid_adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String id = ids[i];


                Intent intent = new Intent(Controller_account.this, view_class.class);
                intent.putExtra("id",id);
                startActivity(intent);

            }
        });
    }

    class get_classes extends AsyncTask {
        String controller_id;
        String res = "nothing";

        public get_classes(String controller_id)
        {
            this.controller_id = controller_id;
        }
        @Override
        protected Object doInBackground(Object[] objects) {
            try {

                String data = "id=" + URLEncoder.encode(controller_id, "UTF-8");
                URL url = new URL(GET_URL);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                OutputStreamWriter outstream = new OutputStreamWriter(conn.getOutputStream());

                outstream.write(data);
                outstream.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line ;

                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                res = sb.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res;
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}