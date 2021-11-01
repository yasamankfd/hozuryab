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

public class view_class_for_attendee extends AppCompatActivity {

    String GET_CLASS = "http://194.5.195.193/load_class.php";
    String GET_CONTROLLERS = "http://194.5.195.193/load_controllers.php";
    TextView id , title , st , et , sd,ed ,place;
    Button show_list;
    String classid , aid;
    GridView controllers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_class_for_attendee);

        Bundle b = getIntent().getExtras();
        classid = b.getString("classid");
        aid = b.getString("aid");
        id = findViewById(R.id.class_id_info_attendee);
        controllers = findViewById(R.id.controllers_grid);
        title = findViewById(R.id.class_title_info_attendee);
        place = findViewById(R.id.class_place_info_attendee);
        st = findViewById(R.id.class_stime_info_attendee);
        et = findViewById(R.id.class_etime_info_attendee);
        sd = findViewById(R.id.class_sdate_info_attendee);
        show_list = findViewById(R.id.show_sessions_abpresence_for_aten);
        ed = findViewById(R.id.class_edate_info_attendee);

        show_list.setOnClickListener(view -> {
            Intent intent = new Intent(view_class_for_attendee.this,Show_abpresence_for_attendee.class);
            intent.putExtra("classid",classid);
            intent.putExtra("aid",aid);
            startActivity(intent);
        });

        get_class getClass = new get_class(classid);
        getClass.execute();

        String class_info ="none";
        try{
            class_info = getClass.get().toString();
            System.out.println("claaaaaaaaaaassssssssssssssssssss iiiiiinnnnnnnnfffffffooooorrrmation "+class_info);
        }catch (Exception e){e.printStackTrace();}
        String[] splited_data = class_info.split("\"");
        id.setText(splited_data[3]);
        title.setText(splited_data[55]);
        place.setText(splited_data[31]);
        st.setText(splited_data[15]);
        et.setText(splited_data[23]);
        sd.setText(splited_data[39]);
        ed.setText(splited_data[47]);

        get_controlers getCon = new get_controlers(splited_data[3]);
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
            rawId+=raw_attendees_data[i]+"_";
            i+=5;
            rawName+=raw_attendees_data[j]+"_";
            j+=5;
        }
        String[] ids = rawId.split("_") , names = rawName.split("_");
        String[] temp = names[len-1].split("-");
        names[len-1]=temp[0];
        Attendee_list_grid_adapter attendee_list_grid_adapter = new Attendee_list_grid_adapter(view_class_for_attendee.this,names,ids);
        controllers.setAdapter(attendee_list_grid_adapter);
        controllers.setNumColumns(1);
        controllers.setHorizontalSpacing(5);

        controllers.setOnItemClickListener((adapterView, view, i12, l) ->{
            String id = ids[i12];

            Intent intent = new Intent(view_class_for_attendee.this, view_controller_for_attendee.class);
            intent.putExtra("cid",id);
            startActivity(intent);
        });

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