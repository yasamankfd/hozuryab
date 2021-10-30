package com.example.hozuryab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    String classId,classTitle;
    TextView classtitle , classid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendees_for_controller);

        Bundle b = getIntent().getExtras();
        classId = b.getString("classid");
        classTitle = b.getString("classtitle");

        classid = findViewById(R.id.class_id_in_attendee_list);
        classtitle = findViewById(R.id.class_title_in_attendee_list);
        gridView = findViewById(R.id.attendees_list);
        classid.setText   ("class ID :    "+classId);
        classtitle.setText("class title : "+classTitle);

        get_attendees getAttendees = new get_attendees(classId);
        getAttendees.execute();
        String result = "nothing";
        try{
            result = getAttendees.get().toString();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++  "+result);
        String[] raw_attendees_data = result.split("_");
        int length = raw_attendees_data.length , len = length/5,i= 2, j = 4;
        String rawId = "",rawName = "";
        for(int k =0 ; k<len ; k++)
        {
            rawId+=raw_attendees_data[i]+"_";
            i+=5;
            rawName+=raw_attendees_data[j]+"_";
            j+=5;
        }
        String[] ids = rawId.split("_") , names = rawName.split("_");
        String[] temp = names[len-1].split("-");
        names[len-1]=temp[0];
        Attendee_list_grid_adapter attendee_list_grid_adapter = new Attendee_list_grid_adapter(View_attendees_for_controller.this,names,ids);
        gridView.setAdapter(attendee_list_grid_adapter);
        gridView.setNumColumns(1);
        gridView.setHorizontalSpacing(5);
        gridView.setOnItemClickListener((adapterView, view, i12, l) ->{
            String id = ids[i12];

            Intent intent = new Intent(View_attendees_for_controller.this, Show_attendee_for_controller.class);
            intent.putExtra("classid",classId);
            intent.putExtra("aid",id);
            startActivity(intent);
        });




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