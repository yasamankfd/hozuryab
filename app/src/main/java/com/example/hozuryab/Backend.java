package com.example.hozuryab;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

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
import java.nio.charset.StandardCharsets;


public class Backend extends AsyncTask<String,Void,String> {

  Context ctx;
  AlertDialog alert ;

  public Backend(Context context)
  {
    ctx = context;
  }
  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  @Override
  protected String doInBackground(String... strings) {
    String type = strings[0];

    String loginUrl = "http://194.5.195.193/attendee_login.php";
    String login2Url = "http://194.5.195.193/controller_login.php";
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
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        OutputStream op = httpURLConnection.getOutputStream();
        BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(op , StandardCharsets.UTF_8));
        String postData = URLEncoder.encode("id" , "UTF-8")+"="+URLEncoder.encode(username , "UTF-8")+"&"+
                URLEncoder.encode("pass" , "UTF-8")+"="+URLEncoder.encode(password , "UTF-8");
        bfw.write(postData);
        bfw.flush();
        bfw.close();
        op.close();
        InputStream ip = httpURLConnection.getInputStream();
        BufferedReader bfr = new BufferedReader(new InputStreamReader(ip , StandardCharsets.ISO_8859_1));

        String result = "" , line ;
        while ((line = bfr.readLine())!= null)
        {
          result+=line;
        }
        bfr.close();
        ip.close();
        httpURLConnection.disconnect();


        return result;

      } catch (MalformedURLException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }else System.out.println("not login nor login2 --------------------------");
    return null;
  }

  @Override
  protected void onPreExecute() {
    alert = new AlertDialog.Builder(ctx).create();
    alert.setTitle("log in");
  }

  @Override
  protected void onPostExecute(String s) {
    //alert.setMessage(s);
    //alert.show();
  }

  @Override
  protected void onProgressUpdate(Void... values) {
    super.onProgressUpdate(values);
  }
}
