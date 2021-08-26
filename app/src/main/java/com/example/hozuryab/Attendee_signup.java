package com.example.hozuryab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Attendee_signup extends AppCompatActivity {
    // attendee sign up
    Button submit;
    EditText username;
    EditText pass;
    EditText date;
    ImageView image;
    EditText lname;
    EditText name;
    Button choose_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendee_signup);
        submit = (Button) findViewById(R.id.submit);
        username = (EditText) findViewById(R.id.username);
        name = (EditText) findViewById(R.id.name);
        lname = (EditText) findViewById(R.id.lname);
        pass = (EditText) findViewById(R.id.pass);
        date = (EditText) findViewById(R.id.date);
        image = (ImageView) findViewById(R.id.image);
        choose_pic = (Button) findViewById(R.id.choose);
        choose_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
    }
}