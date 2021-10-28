package com.example.hozuryab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class Add_session extends AppCompatActivity {
    String SESSION_CREATION_URL= "http://194.5.195.193/add_session.php" , Id ,get_attendees = "http://194.5.195.193/load_attendees.php";
    String[] ids , names , statuses;
    DatePicker date;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_session);


        Bundle b = getIntent().getExtras();
        Id = b.getString("classid");
        date = findViewById(R.id.session_date_);
        submit = findViewById(R.id.add_the_session);

        submit.setOnClickListener(view -> {
            String sdate = date.getYear()+"-"+date.getMonth()+"-"+date.getDayOfMonth();
            create_session createSession = new create_session(Id,sdate);
            createSession.execute();
            String res;
            try {
                res = createSession.get();
                System.out.println("reeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeees   "+res);
                if (res.contains("o")) {

                    get_attendees getAttendees = new get_attendees(Id);
                    getAttendees.execute();

                    String result = "nothing";

                    try{
                        result = getAttendees.get().toString();
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++  "+result);
                    String[] raw_attendees_data = result.split("-");
                    int length = raw_attendees_data.length , len = length/5,i= 2, j = 4;
                    String rawId = "",rawName = "";
                    for(int k =0 ; k<len ; k++)
                    {
                        rawId+=raw_attendees_data[i]+"-";
                        i+=5;
                        rawName+=raw_attendees_data[j]+"-";
                        j+=5;
                    }
                    String[] ids = rawId.split("-") , names = rawName.split("-");

                    Intent intent = new Intent(Add_session.this,Check_list.class);
                    intent.putExtra("classid",Id);
                    intent.putExtra("ids",ids);
                    intent.putExtra("names",names);
                    intent.putExtra("statuses",ids);
                    intent.putExtra("date",sdate);
                    intent.putExtra("mode","first time");
                    startActivity(intent);
                    Toast.makeText(getApplication(), "جلسه با موفقیت ثبت شد !", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(getApplication(), "مشکلی پیش امد دوباره امتحان کنید !", Toast.LENGTH_SHORT).show();

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        });





    }
    class create_session extends AsyncTask<Object,Object,String>
    {
        String id ;
        String sdate;
        public create_session(String id,String sdate)
        {
            this.id = id;
            this.sdate = sdate;
        }

        @Override
        protected String doInBackground(Object... objects) {
            String res = "not initialized";
            try{
                String data = "classid=" + URLEncoder.encode(id,"UTF-8") +"&"+"date="+sdate;
                URL url = new URL(SESSION_CREATION_URL);
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

                while((line=reader.readLine())!=null)
                {
                    sb.append(line+"\n");
                }

                res = sb.toString();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return res;
        }
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