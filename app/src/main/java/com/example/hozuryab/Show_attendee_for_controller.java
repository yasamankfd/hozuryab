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
import java.util.concurrent.ExecutionException;

public class Show_attendee_for_controller extends AppCompatActivity {

    String GET_SESSIONS_FOR_ATTENDEE = "http://194.5.195.193/get_sessions_for_attendee.php";
    private static final String GET_URL = "http://194.5.195.193/attendee_profile.php";
    GridView grid;
    String classid , aid;
    TextView uname , lname , phone , name , date;
    String[] dates,statuses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_attendee_for_controller);

        Bundle b = getIntent().getExtras();
        aid = b.getString("aid");
        classid = b.getString("classid");

        grid = findViewById(R.id.sessions_single_attendee_grid);
        name = findViewById(R.id.aname_in_show_attendee);
        uname = findViewById(R.id.uname_show_attendee);
        lname = findViewById(R.id.lname_in_show_attendee);
        phone = findViewById(R.id.aphone_in_show_attendee);
        date = findViewById(R.id.bdate_in_show_attendee);

        String[] response;
        profile_fetch profile = new profile_fetch(aid);
        profile.execute();
        try {
            String data = profile.get().toString();
            System.out.println("responseeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee    "+data);
            response = data.split("\"");

            uname.setText(response[3]);
            name.setText(response[15]);
            lname.setText(response[23]);
            phone.setText(response[59]);

            System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{  "+response[51]);
            String[] birthdate = response[51].split("-");

            Integer yyy= Integer.parseInt(birthdate[0]);
            String ss = yyy.toString();
            String bdate = ss +"-" + birthdate[1]+"-"+birthdate[2];
            date.setText(bdate);


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        Get_sessions getSessions = new Get_sessions(classid,aid);
        getSessions.execute();
        String result = "none";
        try{
            result = getSessions.get().toString();
            System.out.println("777777777777777777777777777777777777777777777777777777777777->"+result);
        }catch (Exception e){e.printStackTrace();}
        String[] splited_raw_data = result.split("_");
        int i = 2 , j = 4 , l = splited_raw_data.length/5;
        String rawDate ="" ,rawStatus="" ;
        for (int k=0;k<l;k++)
        {
            rawDate +=splited_raw_data[i];
            i+=5;
            rawStatus+=splited_raw_data[j];
            j+=5;


            if(l-k>1)
            {
                rawDate +="_";

                rawStatus+="_";
            }
        }
        dates = rawDate.split("_");
        statuses = rawStatus.split("_");

        Show_ab_for_attendee_adapter show_ab_for_attendee_adapter = new Show_ab_for_attendee_adapter(dates,statuses,Show_attendee_for_controller.this);
        grid.setAdapter(show_ab_for_attendee_adapter);
        grid.setNumColumns(1);
        grid.setHorizontalSpacing(5);

    }

    class Get_sessions extends AsyncTask {
        String classid , aid;
        public Get_sessions(String classid , String aid)
        {
            this.classid = classid;
            this.aid = aid;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String res = "nothing";
            try{
                String data = "classid="+ URLEncoder.encode(classid,"UTF-8")+"&"+"aid="+aid;
                URL url = new URL(GET_SESSIONS_FOR_ATTENDEE);

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
            } catch (Exception e){e.printStackTrace();}
            return res;
        }
    }

    class profile_fetch extends AsyncTask
    {
        String res="none";
        public profile_fetch(String username)
        {
            aid = username;
        }
        @Override
        protected String doInBackground(Object... objects) {

            try {
                System.out.println("ooooooooohhhhhhhhhhgggggggggggggggg  -> "+aid);

                String data = "id=" + URLEncoder.encode(aid, "UTF-8");
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
        @Override
        protected void onPreExecute() {

        }
    }
}