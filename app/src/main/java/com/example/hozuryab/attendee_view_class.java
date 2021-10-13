package com.example.hozuryab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class attendee_view_class extends AppCompatActivity {

    String GET_CLASS = "http://194.5.195.193/attendee_load_class.php";
    TextView id , title , st , et , sd,ed ,place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendee_view_class);
        Bundle b = getIntent().getExtras();
        String Id = b.getString("id");
        id = findViewById(R.id.attendee_class_id_info);
        title = findViewById(R.id.attendee_class_title_info);
        place = findViewById(R.id.attendee_class_place_info);
        st = findViewById(R.id.attendee_class_stime_info);
        et = findViewById(R.id.attendee_class_etime_info);
        sd = findViewById(R.id.attendee_class_sdate_info);
        ed = findViewById(R.id.attendee_class_edate_info);

        get_class getClass = new get_class(Id);
        getClass.execute();

        String class_info ="none";
        try{
            class_info = getClass.get().toString();
            System.out.println("claaaaaaaaaaassssssssssssssssssss iiiiiinnnnnnnnfffffffooooorrrmation "+class_info);
        }catch (Exception e){e.printStackTrace();}
        String[] splitesd_data = class_info.split("\"");
        id.setText(splitesd_data[3]);
        title.setText(splitesd_data[55]);
        place.setText(splitesd_data[31]);
        st.setText(splitesd_data[15]);
        et.setText(splitesd_data[23]);
        sd.setText(splitesd_data[39]);
        ed.setText(splitesd_data[47]);
    }

    class get_class extends AsyncTask {
        String class_id = "";
        String res = "nothing";
        public get_class(String id)
        {
            class_id = id;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {

                String data = "id=" + URLEncoder.encode(class_id, "UTF-8");
                URL url = new URL(GET_CLASS);

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