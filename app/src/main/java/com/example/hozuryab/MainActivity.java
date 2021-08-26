 package com.example.hozuryab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

 public class MainActivity extends AppCompatActivity {
     EditText loginusername;
     EditText password;
     Button login;
     Button signup;
     Switch type;
     boolean Type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginusername = (EditText) findViewById(R.id.loginusername);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);
        type = (Switch) findViewById(R.id.type);

        type.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Type = type.isChecked();
            }
        });
    }

     public void log_in(View view) {
         boolean stype = type.isChecked();
         String username =  loginusername.getText().toString();
         String pass = password.getText().toString();
         String type2;
         Backend b = new Backend(this);


         if(Type)
         {
             // attendee
             type2 = "login";


             Intent intent = new Intent(this, Account.class);
             startActivity(intent);
             b.execute(type2,username,pass);


         }else{
             // controller
             type2 = "login2";

             Intent intent = new Intent(this, Account.class);
             startActivity(intent);
             b.execute(type2,username,pass);
         }





     }

     public void sign_up(View view) {


         if(Type)
         {
             Intent intent = new Intent(this, Attendee_signup.class);
             startActivity(intent);
             // attendee
         }else{
             Intent intent = new Intent(this, Controller_signup.class);
             startActivity(intent);
         }


     }


 }