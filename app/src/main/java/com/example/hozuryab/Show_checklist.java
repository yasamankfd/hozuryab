package com.example.hozuryab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.GridView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Show_checklist extends AppCompatActivity {

    String GET_ABPRESENCE="http://194.5.195.193/show_checklist.php";
    String Id,sDate;
    String[] ids,names,statuses;
    GridView checklist_grid;
    FloatingActionButton edit_checklist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_checklist);

        Bundle b = getIntent().getExtras();
        Id = b.getString("classid");
        sDate = b.getString("date");
        checklist_grid = findViewById(R.id.checklist_grid);
        edit_checklist = findViewById(R.id.floatingAction_edit_checklist);


        edit_checklist.setOnClickListener(view -> {
            Intent i = new Intent(Show_checklist.this,Check_list.class);
            i.putExtra("classid",Id);
            i.putExtra("date",sDate);
            i.putExtra("mode","editing");
            startActivity(i);
            finish();
        });

        get_attendees getAttendees = new get_attendees(Id,sDate);
        getAttendees.execute();

        String result = "nothing";
        try{
            result = getAttendees.get().toString();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++  show checklist "+result);
        String[] raw_attendees_data = result.split("_");
        int length = raw_attendees_data.length , len = length/7,i= 2, j = 4,k=6;
        String rawId = "",rawName = "",rawStatus = "";
        for(int l =0 ; l<len ; l++)
        {
            rawId+=raw_attendees_data[i];
            i+=7;
            rawName+=raw_attendees_data[j];
            j+=7;
            rawStatus+=raw_attendees_data[k];
            k+=7;
            if(len-l>1)
            {
                rawId+="_";
                rawName+="_";
                rawStatus+="_";
            }
        }
        ids = rawId.split("_");
        names = rawName.split("_");
        statuses=rawStatus.split("_");
        Show_checklist_adapter show_checklist_adapter = new Show_checklist_adapter(ids,names,statuses,Show_checklist.this);
        checklist_grid.setAdapter(show_checklist_adapter);
        checklist_grid.setNumColumns(1);
        checklist_grid.setHorizontalSpacing(2);

    }

    class get_attendees extends AsyncTask {
        String classid , date;
        public get_attendees(String classid,String date)
        {
            this.classid = classid;
            this.date = date;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String res = "none";
            try {
                String data = "classid=" + URLEncoder.encode(classid,"UTF-8")+"&"+"date="+date;
                URL url = new URL(GET_ABPRESENCE);

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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent i = new Intent(this,view_class.class);
            i.putExtra("id",Id);
            startActivity(i);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}