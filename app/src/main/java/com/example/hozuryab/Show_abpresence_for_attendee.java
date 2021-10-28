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

public class Show_abpresence_for_attendee extends AppCompatActivity {

    String GET_SESSIONS_FOR_ATTENDEE = "http://194.5.195.193/get_sessions_for_attendee.php";
    GridView gird;
    String[] dates,statuses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_abpresence_for_attendee);

        Bundle b = getIntent().getExtras();
        String aid = b.getString("aid") , classid = b.getString("classid");
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

        gird = findViewById(R.id.checklist_for_attendee_grid);
        Show_ab_for_attendee_adapter show_ab_for_attendee_adapter = new Show_ab_for_attendee_adapter(dates,statuses,Show_abpresence_for_attendee.this);
        gird.setAdapter(show_ab_for_attendee_adapter);
        gird.setNumColumns(1);
        gird.setHorizontalSpacing(5);
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
}