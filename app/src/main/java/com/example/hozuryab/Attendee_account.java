package com.example.hozuryab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

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

public class Attendee_account extends AppCompatActivity {
    Context ctx = this;
    String user;
    GridView grid;
    String get_classes = "http://194.5.195.193/attendee_load_classes.php";

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    String[] titles, ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendee_account);

        try{

            FileInputStream fIn = openFileInput("hozuryab_data_aten.txt");
            InputStreamReader isr = new InputStreamReader(fIn);

            char[] inputBuffer = new char[20];

            isr.read(inputBuffer);
            user = new String(inputBuffer);
            String[] ss = user.split("!");
            user = ss[0];
        }catch(Exception e){e.printStackTrace();}
        drawerLayout = findViewById(R.id.attendee_drawer_layout);
        NavigationView navigationView = findViewById(R.id.attendee_nac_view);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Get_classes getClasses = new Get_classes(user);
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
        int len = raw_class_data.length/5,i=3,j=5;
        String rawId = "",rawTitle = "";
        for(int k =0 ; k<len ; k++)
        {
            rawId+=raw_class_data[i]+"-";
            i+=5;
            rawTitle+=raw_class_data[j]+"-";
            j+=5;
        }
        ids = rawId.split("-");
        titles = rawTitle.split("-");
        grid = findViewById(R.id.attendee_classes);
        Con_grid_adapter con_grid_adapter = new Con_grid_adapter(Attendee_account.this,titles,ids);
        grid.setAdapter(con_grid_adapter);
        grid.setNumColumns(1);
        grid.setHorizontalSpacing(1);

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.nav_attendee_account:
                    Intent i1 = new Intent(ctx,Attendee_profile.class);
                    startActivity(i1);
                    return true;
                case R.id.nav_attendee_contact:
                    return true;

                case R.id.nav_attendee_logout:
                    Intent intent = new Intent(ctx,MainActivity.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        });

        grid.setOnItemClickListener((adapterView, view, i12, l) -> {

            String id = ids[i12];

            Intent intent = new Intent(Attendee_account.this, view_class_for_attendee.class);
            intent.putExtra("classid",id);
            intent.putExtra("aid",user);
            startActivity(intent);

        });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    class Get_classes extends AsyncTask {
        String attendee_id;
        String res = "nothing";

        public Get_classes(String attendee_id)
        {
            this.attendee_id = attendee_id;
        }
        @Override
        protected Object doInBackground(Object[] objects) {
            try {

                String data = "id=" + URLEncoder.encode(attendee_id, "UTF-8");
                URL url = new URL(get_classes);

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


}