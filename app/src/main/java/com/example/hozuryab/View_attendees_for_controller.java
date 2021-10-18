package com.example.hozuryab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class View_attendees_for_controller extends AppCompatActivity {

    String get_attendees = "http://194.5.195.193/load_attendees.php";
    GridView gridView;
    TextView classtitle , classid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendees_for_controller);

        Bundle b = getIntent().getExtras();
        String classId = b.getString("classid") , classTitle = b.getString("classtitle");

        classid = findViewById(R.id.class_id_in_attendee_list);
        classtitle = findViewById(R.id.class_title_in_attendee_list);
        gridView = findViewById(R.id.attendees_list);
        classid.setText(classId);
        classtitle.setText(classTitle);

        get_attendees getAttendees = new get_attendees(classId);
        getAttendees.execute();
        String res = "nothing";
        try{
            res = getAttendees.get().toString();


        }catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++  "+res);


    }
    class get_attendees extends AsyncTask{
        String classid;
        public get_attendees(String classid)
        {
            this.classid = classid;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String res = "none";
            try {

                String data = "classid=" + URLEncoder.encode(classid, "UTF-8");
                URL url = new URL(get_attendees);

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