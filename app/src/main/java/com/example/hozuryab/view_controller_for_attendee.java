package com.example.hozuryab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class view_controller_for_attendee extends AppCompatActivity {

    String cid ;
    private static final String GET_URL = "http://194.5.195.193/controller_profile.php";
    Bundle b;
    TextView uname , name, lname , email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_controller_for_attendee);

        b = getIntent().getExtras();
        cid = b.getString("cid");
        uname = findViewById(R.id.controller_view_uname);
        name = findViewById(R.id.controller_view_name);
        lname = findViewById(R.id.controller_view_lname);
        email = findViewById(R.id.controller_view_email);
        String[] response;
        profile_fetch profile = new profile_fetch(cid);
        profile.execute();

        String data ="data";
        try{
            data = profile.get().toString();
        }catch(Exception e){ e.printStackTrace(); }
        System.out.println("reponseeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee   -> "+data);
        response = data.split("\"");

        uname.setText(response[7]);
        name.setText(response[15]);
        lname.setText(response[23]);
        email.setText(response[39]);
    }
    class profile_fetch extends AsyncTask {
        String ciD;

        String res = "none";

        public profile_fetch(String username) {
            ciD = username;
        }

        @Override
        protected String doInBackground(Object... objects) {

            try {

                String data = "id=" + URLEncoder.encode(ciD, "UTF-8");
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
                String line;

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
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}