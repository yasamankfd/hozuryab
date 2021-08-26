package com.example.hozuryab;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;




public class Backend extends AsyncTask<String,Void,String> {

    Context ctx;
    AlertDialog alert ;
    public Backend(Context context)
    {
        ctx = context;
    }
    @Override
    protected String doInBackground(String... strings) {
        String type = strings[0];

        String loginUrl = "http://194.5.195.193/login.php";
        String login2Url = "http://194.5.195.193/login2.php";
        String URL;


        if (type.equals("login") || type.equals("login2"))
        {
            if(type.equals("login"))
            {
                URL = loginUrl;
            }else URL = login2Url;
            try {
                String username = strings[1];
                String password = strings[2];
                URL url = new URL(URL);
                HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoOutput(true);
                httpsURLConnection.setDoInput(true);
                OutputStream op = httpsURLConnection.getOutputStream();
                BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(op , "UTF-8"));
                String postData = URLEncoder.encode("id" , "UTF-8")+"="+URLEncoder.encode(username , "UTF-8")+"&"+
                        URLEncoder.encode("pass" , "UTF-8")+"="+URLEncoder.encode(password , "UTF-8");
                bfw.write(postData);
                bfw.flush();
                bfw.close();
                op.close();
                InputStream ip = httpsURLConnection.getInputStream();
                BufferedReader bfr = new BufferedReader(new InputStreamReader(ip ,"iso-8859-1"));
                String result = "" , line = "";
                while ((line = bfr.readLine())!= null)
                {
                    result+=line;
                }
                bfr.close();
                ip.close();
                httpsURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alert = new AlertDialog.Builder(ctx).create();
        alert.setTitle("log in");
    }


}
