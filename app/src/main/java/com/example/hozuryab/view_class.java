package com.example.hozuryab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
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

public class view_class extends AppCompatActivity {

    String GET_CLASS = "http://194.5.195.193/load_class.php";

    TextView id , title , st , et , sd,ed ,place;
    GridView sessions;
    Button new_session , attendees_list , add_attendee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_class);

        Bundle b = getIntent().getExtras();
        String Id = b.getString("id");
        id = findViewById(R.id.class_id_info);
        title = findViewById(R.id.class_title_info);
        place = findViewById(R.id.class_place_info);
        st = findViewById(R.id.class_stime_info);
        et = findViewById(R.id.class_etime_info);
        sd = findViewById(R.id.class_sdate_info);
        ed = findViewById(R.id.class_edate_info);
        new_session = findViewById(R.id.new_session);
        sessions = findViewById(R.id.sessions_grid);
        attendees_list = findViewById(R.id.attendees_list);
        add_attendee = findViewById(R.id.add_attendees);


        add_attendee.setOnClickListener(view -> {
            Intent i = new Intent(view_class.this,add_attendee_to_class.class);
            i.putExtra("classid",id.getText().toString());
            startActivity(i);
        });


        new_session.setOnClickListener(view -> {
            Intent i = new Intent(view_class.this,Check_list.class);
            i.putExtra("classid",Id);
            startActivity(i);
        });
        attendees_list.setOnClickListener(view -> {
            Intent i = new Intent(view_class.this,View_attendees_for_controller.class);
            i.putExtra("classid",Id);
            i.putExtra("classtitle",title.getText().toString());
            startActivity(i);

        });


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