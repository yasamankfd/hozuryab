package com.example.hozuryab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class Show_controllers_for_controller extends AppCompatActivity {
    String GET_CONTROLLERS = "http://194.5.195.193/load_controllers.php";
    GridView grid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_controllers_for_controller);

        Bundle b = getIntent().getExtras();
        String Id = b.getString("classid");
        grid = findViewById(R.id.controllers_grid_for_cons);

        get_controlers getCon = new get_controlers(Id);
        getCon.execute();
        String result = "nothing";
        try{
            result = getCon.get().toString();
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
            System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
            rawId+=raw_attendees_data[i]+"_";
            i+=5;
            rawName+=raw_attendees_data[j]+"_";
            j+=5;
        }
        String[] ids = rawId.split("_") , names = rawName.split("_");
        String[] temp = names[len-1].split("-");
        names[len-1]=temp[0];
        Attendee_list_grid_adapter attendee_list_grid_adapter = new Attendee_list_grid_adapter(this,names,ids);
        grid.setAdapter(attendee_list_grid_adapter);
        grid.setNumColumns(1);
        grid.setHorizontalSpacing(5);

        grid.setOnItemClickListener((adapterView, view, i12, l) ->{
            String id = ids[i12];

            Intent intent = new Intent(this, view_controller_for_attendee.class);
            intent.putExtra("cid",id);
            startActivity(intent);
            //finish();
        });

    }
    class get_controlers extends AsyncTask {
        String class_id = "";
        String res = "nothing";
        public get_controlers(String classid)
        {
            class_id = classid;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {

                String data = "classid=" + URLEncoder.encode(class_id, "UTF-8");
                URL url = new URL(GET_CONTROLLERS);

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