package com.example.hozuryab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.GridView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Check_list extends AppCompatActivity {

    String get_attendees = "http://194.5.195.193/load_attendees.php";
    GridView attendees_checklist;
    String[] ids , names , statuses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);

        Bundle b = getIntent().getExtras();
        ids = b.getStringArray("ids");
        names=b.getStringArray("names");
        statuses = b.getStringArray("statuses");
        attendees_checklist = findViewById(R.id.attendees_checklist_grid);

        Abpresence_grid_adapter abpresence_grid_adapter = new Abpresence_grid_adapter(ids,names,statuses,Check_list.this);
        attendees_checklist.setAdapter(abpresence_grid_adapter);
        attendees_checklist.setNumColumns(1);
        attendees_checklist.setHorizontalSpacing(5);

    }
}