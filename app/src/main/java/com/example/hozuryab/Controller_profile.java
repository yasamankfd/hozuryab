package com.example.hozuryab;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class Controller_profile extends AppCompatActivity {

    EditText uname , name, lname , email;
    ImageView profilepic;
    Context ctx;
    String Edit_URL = "http://194.5.195.193/controller_edit_profile.php";
    String[] response ;
    Button edit ;
    String user;
    Menu item;
    private static final String GET_URL = "http://194.5.195.193/controller_profile.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller_profile);

        uname = findViewById(R.id.controller_profile_uname);
        name = findViewById(R.id.controller_profile_name);
        lname = findViewById(R.id.controller_profile_lname);
        email = findViewById(R.id.controller_profile_email);
        edit = findViewById(R.id.controller_profile_edit);
        item = findViewById(R.id.nav_controller_account);
        uname.setEnabled(false);
        uname.setClickable(false);

        try{

            FileInputStream fIn = openFileInput("hozuryab_data_con.txt");
            InputStreamReader isr = new InputStreamReader(fIn);

            char[] inputBuffer = new char[20];

            isr.read(inputBuffer);
            user = new String(inputBuffer);
            String[] ss = user.split("!");
            user = ss[0];
        }catch(Exception e){e.printStackTrace();}

        profile_fetch profile = new profile_fetch(user);
        profile.execute();

        String data ="data";
        try{
            data = profile.get().toString();
        }catch(Exception e){ e.printStackTrace(); }
        System.out.println("reponseeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee   -> "+data);
        response = data.split("\"");

        uname.setText(response[7]);
        name.setText(response[15]);
        lname.setText(response[23]);
        email.setText(response[39]);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.length()<1 | lname.length()<1 | email.length()<1 )
                {
                    Toast.makeText(getApplication(),"لطفا تمام فیلد ها را پر کنید !",Toast.LENGTH_SHORT).show();
                }else {
                    edit_profile e = new edit_profile(ctx,name.getText().toString(),lname.getText().toString(),uname.getText().toString(),email.getText().toString());
                    e.execute();
                    try {
                        String res = e.get();
                        if(res.contains("y"))
                        {
                            Toast.makeText(getApplication(),"اطلاعات با موفقیت ویرایش شد !",Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(getApplication(),"مشکلی پیش امد دوباره امتحان کنید !"+res,Toast.LENGTH_SHORT).show();
                    } catch (ExecutionException ee) {
                        ee.printStackTrace();
                    } catch (InterruptedException ee) {
                        ee.printStackTrace();
                    }
                }
            }
        });

    }


    private class edit_profile extends AsyncTask<Object,Object,String>{
        String name, lname , email , uname;
        Context contxt;

        public edit_profile(Context context ,String name, String lname,String uname,String email)
        {
            this.name = name;
            this.uname = uname;
            this.lname = lname;
            contxt = context;
            this.email = email;
        }
        @Override
        protected String doInBackground(Object... objects) {
            String res = null;
            try {
                String data = "name=" + URLEncoder.encode(name, "UTF-8")
                        + "&" + "lname=" + lname  + "&" + "email=" + email+ "&" + "id=" + uname;
                URL url = new URL(Edit_URL);

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

            } catch (UnsupportedEncodingException | MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res;

        }
    }


    class profile_fetch extends AsyncTask
    {
        String res="none";
        public profile_fetch(String username)
        {
            user = username;
        }
        @Override
        protected String doInBackground(Object... objects) {

            try {

                String data = "id=" + URLEncoder.encode(user, "UTF-8");
                URL url = new URL(GET_URL);

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
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent i = new Intent(ctx,Controller_account.class);
            startActivity(i);
        }
        return super.onKeyDown(keyCode, event);
    }
}