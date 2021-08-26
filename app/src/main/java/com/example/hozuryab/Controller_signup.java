package com.example.hozuryab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Controller_signup extends AppCompatActivity {
    //controller sign up
    Button submit;
    EditText cusername;
    EditText cpass;
    EditText cemail;
    ImageView cimage;
    EditText clname;
    EditText cname;
    Button cchoose_pic;
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
        cimage = (ImageView) findViewById(R.id.cimage);
        cchoose_pic = (Button) findViewById(R.id.cchoose);

    }
}