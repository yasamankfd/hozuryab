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

public class Check_list extends AppCompatActivity {

    String get_attendees = "http://194.5.195.193/load_attendees.php";
    GridView attendees_checklist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);

        Bundle b = getIntent().getExtras();
        String Id = b.getString("classid");
        attendees_checklist = findViewById(R.id.attendees_checklist_grid);
        System.out.println("pppppppppppppppppppppppppppppppppppppppppppppppppp "+Id);
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
        Abpresence_grid_adapter abpresence_grid_adapter = new Abpresence_grid_adapter(ids,names,Check_list.this);
        attendees_checklist.setAdapter(abpresence_grid_adapter);
        attendees_checklist.setNumColumns(1);
        attendees_checklist.setHorizontalSpacing(5);

    }
    class get_attendees extends AsyncTask {
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
                System.out.println("oooooooooooooooooooooooooooooooooo "+res);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res;
        }
    }
}