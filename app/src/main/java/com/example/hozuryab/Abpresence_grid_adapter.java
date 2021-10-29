package com.example.hozuryab;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class Abpresence_grid_adapter extends BaseAdapter {
    String EDIT_ABPRESENCE="http://194.5.195.193/edit_abpresence.php";
    String INSERT_TO_ABPRESENCE="http://194.5.195.193/insert_to_abpresence.php";
    Context ctx ;
    String classid, sdate;
    String[] names , ids , statuses;
    LayoutInflater layoutInflater;
    String mode;
    public Abpresence_grid_adapter(String[] ids,String[] names , String[] statuses,String classid,String sdate ,String mode, Context ctx)
    {
        this.ids = ids;
        this.statuses = statuses;
        this.names = names;
        this.ctx = ctx;
        this.classid = classid;
        this.sdate = sdate;
        this.mode = mode;
    }
    @Override
    public int getCount() {
        return ids.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(layoutInflater == null)
        {
            layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(view == null)
        {
            view = layoutInflater.inflate(R.layout.abpresent_grid,null);
        }
        TextView name,id;
        Switch status = view.findViewById(R.id.status);
        name = view.findViewById(R.id.name_in_abpresence);
        id = view.findViewById(R.id.id_in_abpresence);
        name.setText(names[i]);
        id.setText(ids[i]);
        if(mode.equals("first time"))
        {
            status.setChecked(false);
            sendDataToServer sendDataToServer1 = new sendDataToServer(ids[i],classid,sdate,"a",INSERT_TO_ABPRESENCE);
            sendDataToServer1.execute();
            try {
                String res = sendDataToServer1.get();
                System.out.println("reeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeees insert for the first time  "+res);
                if (res.contains("o")) {
                    System.out.println("first edit saved---------------------------------------------------------------------------------------");
                } else System.out.println("first edit not saved---------------------------------------------------------------------------------------------");


            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else if(mode.equals("editing")){

            if(statuses[i].contains("p"))
            {
                status.setChecked(true);
                status.setText("حاضر");
            }else {status.setChecked(false);   status.setText("غایب");}
        }
        status.setOnClickListener(view1 -> {
            String stat ;
            if(status.isChecked())
            {
                stat="p";
                status.setText("حاضر");
            }else {stat="a"; status.setText("غایب");}

            System.out.println("$#$#$#$#$#$#$#$#$#$#$#$#$#$#$#$#$#$#$#$#$#$#$#$#$#$##$#$#$#$#$ ->"+ids[i]+"-"+classid+"-"+sdate+"-"+stat);
            sendDataToServer sendDataToServer1 = new sendDataToServer(ids[i],classid,sdate,stat,EDIT_ABPRESENCE);
            sendDataToServer1.execute();
            try {
                String res = sendDataToServer1.get();
                System.out.println("reeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeees  edit abpresence "+res);
                if (res.contains("o")) {
                    System.out.println("edit saved---------------------------------------------------------------------------------------");
                } else System.out.println("edit not saved---------------------------------------------------------------------------------------------");

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return view;
    }
    public class sendDataToServer extends AsyncTask<Object,Object,String> {

        String aid, classid, status, date , phpurl;

        public sendDataToServer(String aid, String classid, String date, String status,String phpurl) {
            this.date = date;
            this.classid = classid;
            this.aid = aid;
            this.status = status;
            this.phpurl = phpurl;
        }
        @Override
        protected String doInBackground(Object... objects) {
            String res = null;
            try {
                String data = "aid=" + URLEncoder.encode(aid, "UTF-8")+"&"+ "classid=" + classid + "&" + "status=" + status + "&" + "date=" + date;
                URL url = new URL(phpurl);

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

            } catch (UnsupportedEncodingException | MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res;
        }
    }
}
