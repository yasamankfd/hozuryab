package com.example.hozuryab;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class New_Class extends AppCompatActivity {

    Context ctx = this;
    EditText title , id , place , cid;
    DatePicker startDate,endDate;
    TimePicker endTime,startTime;
    Button submit;
    String CLASS_CREATION_URL = "http://194.5.195.193/create_class.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_class);

        title = findViewById(R.id.class_title);
        id = findViewById(R.id.class_id);
        startDate = findViewById(R.id.start_date);
        endDate = findViewById(R.id.end_date);
        startTime = findViewById(R.id.start_time);
        endTime = findViewById(R.id.end_time);
        place = findViewById(R.id.place);
        submit = findViewById(R.id.submit_class);
        cid = findViewById(R.id.controller_id);

        place.setOnClickListener(view -> place.setText(""));
        id.setOnClickListener(view -> id.setText(""));
        title.setOnClickListener(view -> title.setText(""));

        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                if(id.length()<1 | title.length()<1 | place.length()<1 | cid.length()<1)
                {
                    Toast.makeText(getApplication(),"لطفا تمام فیلد ها را پر کنید !",Toast.LENGTH_SHORT).show();
                }else {
                    String starting_date = startDate.getYear()+"-"+startDate.getMonth()+"-"+startDate.getDayOfMonth();
                    String ending_date = endDate.getYear()+"-"+endDate.getMonth()+"-"+endDate.getDayOfMonth();

                    String starting_time = startTime.getHour()+":"+startTime.getMinute()+":00";
                    String ending_time = endTime.getHour()+":"+endTime.getMinute()+":00";


                    System.out.println("----------------------------------------start time = "+starting_time);
                    System.out.println("----------------------------------------end time = "+ending_time);
                    System.out.println("----------------------------------------start date = "+starting_date);
                    System.out.println("----------------------------------------end date = "+ending_date);


                    create_class createClass = new create_class(cid.getText().toString(),id.getText().toString(),title.getText().toString(),starting_time,ending_time,starting_date,ending_date,place.getText().toString());

                    createClass.execute();

                    try {
                        String res = createClass.get();
                        System.out.println("reeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeees   "+res);
                        if (res.contains("o")) {

                            Toast.makeText(getApplication(), "کلاس با موفقیت ثبت شد !", Toast.LENGTH_SHORT).show();
                        } else Toast.makeText(getApplication(), "مشکلی پیش امد دوباره امتحان کنید !", Toast.LENGTH_SHORT).show();

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } );

    }

    class create_class extends AsyncTask<Object,Object,String>
    {
        String id , cid;
        String title;
        String stime;
        String etime;
        String sdate;
        String edate;
        String place;
        public create_class(String cid,String id, String title, String stime, String etime, String sdate, String edate, String place)
        {
            this.cid = cid;
            this.id = id;
            this.title = title;
            this.stime = stime;
            this.etime = etime;
            this.sdate = sdate;
            this.edate = edate;
            this.place = place;
        }

        @Override
        protected String doInBackground(Object... objects) {
            String res = "not initialized";
            try{
                String data = "id=" + URLEncoder.encode(id,"UTF-8")
                        +"&"+"title="+title+"&"+"place="+place+"&"+"stime="+stime+"&"+"etime="+etime+"&"+"sdate="+sdate+"&"+"edate="+edate+"&"+"cid="+cid;
                URL url = new URL(CLASS_CREATION_URL);
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
}


