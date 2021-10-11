package com.example.hozuryab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.widget.GridView;

import com.google.android.material.navigation.NavigationView;

import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Attendee_account extends AppCompatActivity {
    Context ctx = this;
    String user;
    GridView grid;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    String[] titles = {"class1","class2","class3","class4","class5","class6"} , ids = { "111","222","333","444","555","666"};

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

        grid = findViewById(R.id.attendee_classes);
        Atten_grid_adapter atten_grid_adapter = new Atten_grid_adapter(ctx,titles,ids);
        grid.setAdapter(atten_grid_adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_attendee_account:
                        Intent i = new Intent(ctx,Attendee_profile.class);
                        startActivity(i);
                        return true;
                    case R.id.nav_attendee_contact:
                        return true;

                    case R.id.nav_attendee_logout:
                        Intent intent = new Intent(ctx,MainActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }


}