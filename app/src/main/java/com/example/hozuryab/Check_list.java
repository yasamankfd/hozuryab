package com.example.hozuryab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
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

    String GET_ABPRESENCE="http://194.5.195.193/show_checklist.php";
    String LOAD_ATTENDEES = "http://194.5.195.193/load_attendees.php";
    GridView attendees_checklist;
    String[] ids , names , statuses;
    String classid,sdate;
    String mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);

        Bundle b = getIntent().getExtras();
        classid = b.getString("classid");
        sdate = b.getString("date");
        mode = b.getString("mode");
        attendees_checklist = findViewById(R.id.attendees_checklist_grid);

        if(mode.equals("editing"))
        {

            get_attendees getAttendees = new get_attendees(classid,sdate,GET_ABPRESENCE);
            getAttendees.execute();
            String result = "nothing";
            try{
                result = getAttendees.get().toString();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ edit check list "+result);
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
        }else if(mode.equals("first time"))
        {
            get_attendees getAttendees = new get_attendees(classid,sdate,LOAD_ATTENDEES);
            getAttendees.execute();
            String result = "nothing";
            try{
                result = getAttendees.get().toString();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ first time inserting to ab "+result);

            String[] raw_attendees_data = result.split("_");
            int length = raw_attendees_data.length , len = length/5,i= 2, j = 4;
            String rawId = "",rawName = "";
            for(int k =0 ; k<len ; k++)
            {
                rawId+=raw_attendees_data[i];
                i+=5;
                rawName+=raw_attendees_data[j];
                j+=5;
                if(len-k>1)
                {
                    rawId+="_";
                    rawName+="_";
                }
            }
            ids = rawId.split("_");
            names = rawName.split("_");
            String[] temp = names[len-1].split("-");
            names[len-1]=temp[0];
        }

        Abpresence_grid_adapter abpresence_grid_adapter = new Abpresence_grid_adapter(ids,names,statuses,classid,sdate,mode,Check_list.this);
        attendees_checklist.setAdapter(abpresence_grid_adapter);
        attendees_checklist.setNumColumns(1);
        attendees_checklist.setHorizontalSpacing(5);
    }

    class get_attendees extends AsyncTask {
        String classid , date , url = " ";
        public get_attendees(String classid,String date,String url)
        {
            this.classid = classid;
            this.date = date;
            this.url = url;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String res = "none";
            try {
                String data = "classid=" + URLEncoder.encode(classid,"UTF-8")+"&"+"date="+date;
                URL urll = new URL(url);

                HttpURLConnection conn = (HttpURLConnection) urll.openConnection();
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
        if (keyCode == KeyEvent.KEYCODE_BACK && mode.equals("first time")) {
            Intent i = new Intent(Check_list.this,view_class.class);
            i.putExtra("id",classid);
            startActivity(i);
            finish();
        }else{
            Intent i = new Intent(Check_list.this,Show_checklist.class);
            i.putExtra("classid",classid);
            i.putExtra("date",sdate);
            startActivity(i);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}