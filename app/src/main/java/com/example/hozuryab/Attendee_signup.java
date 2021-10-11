package com.example.hozuryab;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Attendee_signup extends AppCompatActivity  {
    ArrayList<String> arrayList1 = new ArrayList<>();
    ArrayList<String> arrayList2 = new ArrayList<>();
    ArrayList<String> arrayList3 = new ArrayList<>();
    Button submit;
    EditText ausername;
    EditText apass;
    EditText alname;
    EditText aname , aphone;
    Spinner d , m , y;
    Context ctx = this;

    static final String UPLOAD_URL = "http://194.5.195.193/attendee_signup.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendee_signup);
        submit = findViewById(R.id.submit);
        ausername = findViewById(R.id.username);
        aname = findViewById(R.id.name);
        alname = findViewById(R.id.lname);
        apass = findViewById(R.id.pass);
        aphone = findViewById(R.id.aphone);
        d = findViewById(R.id.aday);
        m = findViewById(R.id.amonth);
        y = findViewById(R.id.ayear);

        arrayList1.add("01");arrayList1.add("02");arrayList1.add("03");arrayList1.add("04");arrayList1.add("05");arrayList1.add("06");
        arrayList1.add("07");arrayList1.add("08");arrayList1.add("09");arrayList1.add("10");arrayList1.add("11");arrayList1.add("12");

        arrayList2.add("01");arrayList2.add("02");arrayList2.add("03");arrayList2.add("04");arrayList2.add("05");arrayList2.add("06");
        arrayList2.add("07");arrayList2.add("08");arrayList2.add("09");arrayList2.add("10");arrayList2.add("11");arrayList2.add("12");
        arrayList2.add("13");arrayList2.add("14");arrayList2.add("15");arrayList2.add("16");arrayList2.add("17");arrayList2.add("18");
        arrayList2.add("19");arrayList2.add("20");arrayList2.add("21");arrayList2.add("22");arrayList2.add("23");arrayList2.add("24");
        arrayList2.add("25");arrayList2.add("26");arrayList2.add("27");arrayList2.add("28");arrayList2.add("29");arrayList2.add("30");
        arrayList2.add("31");

        arrayList3.add("1398");arrayList3.add("1397");arrayList3.add("1396");arrayList3.add("1395");arrayList3.add("1394");arrayList3.add("1393");
        arrayList3.add("1392");arrayList3.add("1391");arrayList3.add("1390");arrayList3.add("1389");arrayList3.add("1388");arrayList3.add("1387");
        arrayList3.add("1384");arrayList3.add("1383");arrayList3.add("1382");arrayList3.add("1381");arrayList3.add("1380");arrayList3.add("1379");
        arrayList3.add("1378");arrayList3.add("1377");arrayList3.add("1376");arrayList3.add("1375");arrayList3.add("1374");arrayList3.add("1373");
        arrayList3.add("1371");arrayList3.add("1370");arrayList3.add("1369");arrayList3.add("1368");arrayList3.add("1367");arrayList3.add("1366");
        arrayList3.add("1365");arrayList3.add("1364");arrayList3.add("1363");arrayList3.add("1362");arrayList3.add("1361");arrayList3.add("1360");
        arrayList3.add("1359");arrayList3.add("1358");arrayList3.add("1358");arrayList3.add("1357");arrayList3.add("1356");arrayList3.add("1355");
        arrayList3.add("1354");arrayList3.add("1353");arrayList3.add("1352");arrayList3.add("1351");arrayList3.add("1350");arrayList3.add("1349");
        arrayList3.add("1348");arrayList3.add("1347");arrayList3.add("1346");arrayList3.add("1345");arrayList3.add("1344");arrayList3.add("1343");
        arrayList3.add("1342");arrayList3.add("1341");arrayList3.add("1340");arrayList3.add("1339");arrayList3.add("1338");arrayList3.add("1337");
        arrayList3.add("1336");arrayList3.add("1335");arrayList3.add("1334");arrayList3.add("1333");arrayList3.add("1332");arrayList3.add("1331");
        arrayList3.add("1330");arrayList3.add("1386");arrayList3.add("1385");arrayList3.add("1372");

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, arrayList2);
        arrayAdapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        d.setAdapter(arrayAdapter1);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item ,arrayList1);
        arrayAdapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        m.setAdapter(arrayAdapter2);
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item ,arrayList3);
        arrayAdapter3.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        y.setAdapter(arrayAdapter3);




        submit.setOnClickListener(view -> {
            if(ausername.length()<1 | aname.length()<1 | apass.length()<1 | alname.length()<1 | aphone.length()<1 |d.getSelectedItem().equals(null) | m.getSelectedItem().equals(null) | y.getSelectedItem().equals(null))
            {
                Toast.makeText(getApplication(),"لطفا همه فیلدهارا پر کنید !",Toast.LENGTH_SHORT).show();
            }else {
                String date = y.getSelectedItem().toString()+"-"+m.getSelectedItem().toString()+"-"+d.getSelectedItem().toString();
                sendDataToServer s = new sendDataToServer(aname.getText().toString(),alname.getText().toString(),apass.getText().toString(),date,ausername.getText().toString(),aphone.getText().toString());
                s.execute();

                try {
                    String res = s.get();
                    if(res.contains("y"))
                    {
                        try{
                            final String TESTSTRING = ausername.getText().toString();

                            FileOutputStream fOut = openFileOutput("hozuryab_data_con.txt", MODE_PRIVATE);
                            OutputStreamWriter osw = new OutputStreamWriter(fOut);

                            osw.write(TESTSTRING+"!");

                            osw.flush();
                            osw.close();

                        }catch (Exception e){ }
                        //Intent i = new Intent(ctx,Controller_account.class);
                        //startActivity(i);
                        Toast.makeText(getApplication(),"با موفقیت ثبتنام کردید!",Toast.LENGTH_SHORT).show();
                    }else Toast.makeText(getApplication(),"نام کاربری قبلا انتخاب شده !",Toast.LENGTH_SHORT).show();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });




    }
    public class sendDataToServer extends AsyncTask<Object,Object,String>{

        String name , lname,pass,username,date ,phone;

        public sendDataToServer(String name,String lname , String pass , String date , String username,String phone)
        {
            this.phone = phone;
            this.name = name;
            this.lname = lname;
            this.pass = pass;
            this.username = username;
            this.date = date;
        }

        @Override
        protected String doInBackground(Object... objects) {
            String res = null;
            try {
                String exist = "y";
                String data = "name=" + URLEncoder.encode(name,"UTF-8")
                        +"&"+"date="+date+"&"+"pass="+pass+"&"+"lname="+lname+"&"+"id="+username+"&"+"phone="+phone+"&"+"exist="+exist;
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
}