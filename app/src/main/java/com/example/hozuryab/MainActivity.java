 package com.example.hozuryab;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.concurrent.ExecutionException;

 public class MainActivity extends AppCompatActivity {
     EditText loginusername;
     EditText password;
     Button login;
     Button signup;
     @SuppressLint("UseSwitchCompatOrMaterialCode")
     Switch type;
     boolean Type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginusername = findViewById(R.id.loginusername);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        type = findViewById(R.id.type);

        login.setOnClickListener(view -> {
            try {
                log_in();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        signup.setOnClickListener(view -> sign_up());


        type.setOnCheckedChangeListener((buttonView, isChecked) -> Type = type.isChecked());
    }

     public void log_in() throws ExecutionException, InterruptedException {

         String username = loginusername.getText().toString();

         String pass = password.getText().toString();
         String type2;
         Backend b = new Backend(this);



         if (Type) {

             // attendee
             type2 = "login";

            AsyncTask<String, Void, String> bb =  b.execute(type2, username, pass);
             System.out.println("-----------------------------------------------------"+bb.get());
             if(bb.get().equals("o"))
             {
                 try{
                     final String TESTSTRING = username;

                     FileOutputStream fOut = openFileOutput("hozuryab_data_aten.txt", MODE_PRIVATE);
                     OutputStreamWriter osw = new OutputStreamWriter(fOut);

                     System.out.println("befoooorrrreeeeeeeee    -> "+username);
                     osw.write(TESTSTRING+"!");
                     osw.flush();
                     osw.close();

                 }catch (Exception e){ }
                 Intent intent = new Intent(this, Attendee_account.class);
                 startActivity(intent);
                 finish();
                 Toast.makeText(getApplication(), "???? ???????????? ???????? ???????? !", Toast.LENGTH_SHORT).show();
             }else Toast.makeText(getApplication(), "?????? ???????????? ???? ?????????????? ???????????? ?????? !", Toast.LENGTH_SHORT).show();

         } else {

             // controller
             type2 = "login2";
             AsyncTask<String, Void, String> bb =  b.execute(type2, username, pass);
             System.out.println("-----------------------------------------------------"+bb.get());
             if(bb.get().equals("o"))
             {

                 try{
                     final String TESTSTRING = username;

                     FileOutputStream fOut = openFileOutput("hozuryab_data_con.txt", MODE_PRIVATE);
                     OutputStreamWriter osw = new OutputStreamWriter(fOut);
                     osw.write(TESTSTRING+"!");
                     osw.flush();
                     osw.close();

                 }catch (Exception e){ }
                 Intent intent = new Intent(this, Controller_account.class);
                 startActivity(intent);
                 finish();
                 Toast.makeText(getApplication(), "???? ???????????? ???????? ???????? !", Toast.LENGTH_SHORT).show();
             }else Toast.makeText(getApplication(), "?????? ???????????? ???? ?????????????? ???????????? ?????? !", Toast.LENGTH_SHORT).show();

         }
        }
         public void sign_up (){

             if (Type) {
                 Intent intent = new Intent(this, Attendee_signup.class);
                 startActivity(intent);
                 finish();

             } else {
                 Intent intent = new Intent(this, Controller_signup.class);
                 startActivity(intent);
                 finish();
             }
         }
     @Override
     public boolean onKeyDown(int keyCode, KeyEvent event) {
         if (keyCode == KeyEvent.KEYCODE_BACK) {
             finish();
         }
         return super.onKeyDown(keyCode, event);
     }
 }

