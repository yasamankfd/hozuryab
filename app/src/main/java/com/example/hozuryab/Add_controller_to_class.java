package com.example.hozuryab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class Add_controller_to_class extends AppCompatActivity {
    String ADD_URL = "http://194.5.195.193/add_controller_to_class.php";
    Button add ;
    EditText aid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_controller_to_class);

        Bundle b = getIntent().getExtras();
        String classid = b.getString("classid");
        aid = findViewById(R.id.controller_id_to_add_to_class);
        add = findViewById(R.id.addc_to_class);

        add.setOnClickListener(view -> {
            add_con addCon = new add_con(classid , aid.getText().toString());
            addCon.execute();

            try{
                String res = addCon.get().toString();
                if(res.contains("o"))
                {
                    Toast.makeText(getApplication(), "به کلاس اضافه شد !", Toast.LENGTH_SHORT).show();
                }else if(res.contains("n")){ Toast.makeText(getApplication(), "عملیات انجام نشد !", Toast.LENGTH_SHORT).show();}else { Toast.makeText(getApplication(), "مشکلی بوجود امد !", Toast.LENGTH_SHORT).show(); }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
    }
    class add_con extends AsyncTask {
        String classid, cid;
        public add_con(String classid , String cid)
        {
            this.classid = classid;
            this.cid = cid;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String res = "-1";
            try {
                String data = "classid=" + URLEncoder.encode(classid,"UTF-8")
                        +"&"+"cid="+cid;
                URL url = new URL(ADD_URL);

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

            System.out.println("ppppppppppppppppppppppppppppppppppppppppppppppppppppppppp "+res);
            return res;
        }
    }

}