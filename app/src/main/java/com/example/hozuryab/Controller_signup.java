package com.example.hozuryab;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class Controller_signup extends AppCompatActivity  {
    Button submit;
    EditText cusername;
    EditText cpass;
    EditText cemail;
    EditText clname;
    EditText cname;
    Context ctx = this;

    static final String UPLOAD_URL = "http://194.5.195.193/controller_signup.php";

    private final static int PICK_IMAGE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller_signup);
        submit = (Button) findViewById(R.id.csubmit);
        cusername = (EditText) findViewById(R.id.cusername);
        cname = (EditText) findViewById(R.id.cname);
        clname = (EditText) findViewById(R.id.clname);
        cpass = (EditText) findViewById(R.id.cpass);
        cemail = (EditText) findViewById(R.id.cemail);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cusername.length()<1 | cname.length()<1 | cpass.length()<1 | clname.length()<1 | cemail.length()<1 )
                {
                    Toast.makeText(getApplication(),"لطفا تمام فیلد ها را پر کنید !",Toast.LENGTH_SHORT).show();
                }else {
                    sendDataToServer s = new sendDataToServer(cname.getText().toString(),clname.getText().toString(),cpass.getText().toString(),cemail.getText().toString(),cusername.getText().toString());
                    s.execute();

                    try {
                        String res = s.get();
                        if(res.contains("o"))
                        {
                            try{
                                final String TESTSTRING = cusername.getText().toString();

                                FileOutputStream fOut = openFileOutput("hozuryab_data_con.txt", MODE_PRIVATE);
                                OutputStreamWriter osw = new OutputStreamWriter(fOut);

                                osw.write(TESTSTRING+"!");

                                osw.flush();
                                osw.close();

                            }catch (Exception e){ }
                            Intent i = new Intent(ctx,MainActivity.class);
                            startActivity(i);
                            finish();
                            Toast.makeText(getApplication(),"با موفقیت ثبتنام کردید !",Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(getApplication(),"نام کاربری قبلا انتخاب شده است !",Toast.LENGTH_SHORT).show();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }
    public class sendDataToServer extends AsyncTask<Object,Object,String>{

        String name , lname,pass,username,email;

        public sendDataToServer(String name,String lname , String pass , String email , String username)
        {
            this.name = name;
            this.lname = lname;
            this.pass = pass;
            this.username = username;
            this.email = email;
        }

        @Override
        protected String doInBackground(Object... objects) {
            String res = null;
            try {
                String data = "name=" + URLEncoder.encode(name,"UTF-8")
                        +"&"+"email="+email+"&"+"pass="+pass+"&"+"lname="+lname+"&"+"id="+username;
                URL url = new URL(UPLOAD_URL);

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

            } catch (UnsupportedEncodingException | MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent i = new Intent(ctx,MainActivity.class);
            startActivity(i);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}