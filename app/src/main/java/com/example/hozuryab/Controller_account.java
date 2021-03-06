package com.example.hozuryab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
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
    public DrawerLayout drawerLayout;
    GridView grid;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView ;

    String[] titles ={} , ids={} ;

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.nav_controller_account:
                    Intent i = new Intent(ctx,Controller_profile.class);
                    startActivity(i);
                    finish();
                    return true;
                case R.id.nav_controller_contact:
                    Intent intent2 = new Intent(Intent.ACTION_SENDTO);
                    intent2.setData(Uri.parse("mailto:"));
                    intent2.putExtra(Intent.EXTRA_EMAIL, new String[]{"yas003@gmail.com"});
                    intent2.putExtra(Intent.EXTRA_SUBJECT, "Your subject here...");
                    intent2.putExtra(Intent.EXTRA_TEXT,"Your message here...");
                    startActivity(intent2);
                    return true;

                case R.id.nav_controller_logout:
                    Intent intent = new Intent(ctx,MainActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.nav_controller_newClass:
                    Intent intent3 = new Intent(ctx, New_Class.class);
                    startActivity(intent3);
                    finish();
                    return true;
            }
            return false;
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
        if(raw_class_data.length>1)
        {
            int len = raw_class_data.length/5, i=3,j=5;
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
            String[] temp = titles[len-1].split("_");
            titles[len-1] = temp[0];
        }

        grid = findViewById(R.id.controller_classes);
        Con_grid_adapter con_grid_adapter = new Con_grid_adapter(Controller_account.this,titles,ids);
        grid.setAdapter(con_grid_adapter);
        grid.setNumColumns(1);
        grid.setHorizontalSpacing(1);
        grid.setOnItemClickListener((adapterView, view, i1, l) -> {

            String id = ids[i1];

            Intent intent = new Intent(Controller_account.this, view_class.class);
            intent.putExtra("id",id);
            startActivity(intent);
            this.finish();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent i = new Intent(ctx,MainActivity.class);
            startActivity(i);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}