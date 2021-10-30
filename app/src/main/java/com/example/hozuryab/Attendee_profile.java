package com.example.hozuryab;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Attendee_profile extends AppCompatActivity {
    Context ctx;
    Menu item;
    String[] response;
    EditText name,lname,uname,phone ;
    TextView yy , mm , dd;
    ImageView profilepic;
    Spinner y,m,d;
    private  String encoded_string , image_name;
    private Bitmap bitmap;
    private File file;
    private Uri file_uri;
    Button choose ,edit;
    String Edit_URL = "http://194.5.195.193/attendee_edit_profile.php";

    private static final String GET_URL = "http://194.5.195.193/attendee_profile.php";
    String user = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendee_profile);

        edit = findViewById(R.id.attendee_editinfo);
        choose = findViewById(R.id.uploadpic);
        uname = findViewById(R.id.attende_profile_uname);
        name = findViewById(R.id.attende_profile_name);
        lname = findViewById(R.id.attende_profile_lname);
        phone = findViewById(R.id.attende_profile_phone);
        y = findViewById(R.id.attende_profile_bdate_year);
        m = findViewById(R.id.attende_profile_bdate_month);
        d = findViewById(R.id.attende_profile_bdate_day);
        yy = findViewById(R.id.attendee_year);
        mm = findViewById(R.id.attendee_month);
        dd = findViewById(R.id.attendee_day);
        profilepic = findViewById(R.id.attende_profile_image);
        item = findViewById(R.id.nav_attendee_account);

        uname.setEnabled(false);
        uname.setClickable(false);
        edit.setOnClickListener(view -> {

            if( name.length()<1 | lname.length()<1 | d.getSelectedItem().equals(null) | m.getSelectedItem().equals(null) | y.getSelectedItem().equals(null) | phone.length()<1 )
            {
                Toast.makeText(getApplication(),"لطفا تمام فیلد ها را پر کنید !",Toast.LENGTH_SHORT).show();
            }else {
                String birthdate = (Integer.parseInt(y.getSelectedItem().toString()) + 621) + "-" + m.getSelectedItem().toString() + "-" + d.getSelectedItem().toString();
                edit_profile e = new edit_profile(ctx,name.getText().toString(),lname.getText().toString(),birthdate,phone.getText().toString(),uname.getText().toString());
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

        });

        choose.setOnClickListener(view -> {
            Intent i  = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            i.setType("image/*");

            getFileUri();
            i.putExtra(MediaStore.EXTRA_OUTPUT,file_uri);
            profilepic.setImageBitmap(bitmap);
            startActivityForResult(i,10);

        });

        ArrayList<String> arrayList1 = new ArrayList<>();
        ArrayList<String> arrayList2 = new ArrayList<>();
        ArrayList<String> arrayList3 = new ArrayList<>();
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

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item ,arrayList2);
        arrayAdapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        d.setAdapter(arrayAdapter1);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item ,arrayList1);
        arrayAdapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        m.setAdapter(arrayAdapter2);
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item ,arrayList3);
        arrayAdapter3.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        y.setAdapter(arrayAdapter3);

        try{
            FileInputStream fIn = openFileInput("hozuryab_data_aten.txt");
            InputStreamReader isr = new InputStreamReader(fIn);

            char[] inputBuffer = new char[20] ;

            isr.read(inputBuffer);
            user = new String(inputBuffer);
            String[] ss = user.split("!");
            user = ss[0];
        }catch(Exception e){e.printStackTrace();}
        profile_fetch profile = new profile_fetch(user);
        profile.execute();
        try {
            String data = profile.get().toString();
            System.out.println("responseeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee    "+data);
            response = data.split("\"");

            uname.setText(response[3]);
            name.setText(response[15]);
            lname.setText(response[23]);
            phone.setText(response[59]);
            System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{  "+response[51]);
            String[] birthdate = response[51].split("-");
            y.setPrompt(birthdate[0]);
            Integer yyy= Integer.parseInt(birthdate[0])-621;
            String ss = yyy.toString();
            yy.setText(ss);
            m.setPrompt(birthdate[1]);
            mm.setText(birthdate[1]);
            d.setPrompt(birthdate[2]);
            dd.setText(birthdate[2]);

            image_name = uname.getText().toString();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getFileUri()
    {
        image_name = uname.getText().toString();
        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+File.separator + image_name);
        file_uri = Uri.fromFile(file);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 10 && resultCode == RESULT_OK) {
            file_uri = data.getData();
            final String path = getPathFromURI(file_uri);

            if (path != null) {
                File f = new File(path);
                file_uri = Uri.fromFile(f);
            }
            new Encode_image().execute();
            profilepic.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    private class edit_profile extends AsyncTask<Object,Object,String>{
        String name, lname, phone , date , uname;
        Context contxt;

        public edit_profile(Context context ,String name, String lname,  String date, String phone,String uname)
        {
            this.name = name;
            this.uname = uname;
            this.lname = lname;
            this.phone = phone;
            this.date = date;
            contxt = context;
        }
        @Override
        protected String doInBackground(Object... objects) {
            String res = null;
            try {
                String data = "name=" + URLEncoder.encode(name, "UTF-8")
                        + "&" + "date=" + date + "&" + "lname=" + lname  + "&" + "phone=" + phone+ "&" + "id=" + uname;
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
    private class Encode_image extends AsyncTask<Void,Void,Void>{
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Void doInBackground(Void... voids) {

            bitmap = BitmapFactory.decodeFile(file_uri.getPath());
            System.out.println("fiiiiiiiilllllleeeeee    pppppppaaaaattthhhh   -> "+file_uri.getPath());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);

            byte[] array = stream.toByteArray();
            encoded_string = Base64.encodeToString(array,0);
            //System.out.println("######################################### -> "+encoded_string);

            return null;
        }
        @Override
        protected void onPostExecute(Void v) {
            makeRequest();

        }

        @Override
        protected void onPreExecute() {
        }
    }

    private void makeRequest(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request;
        request = new StringRequest(Request.Method.POST, "http://194.5.195.193/aprofilepic.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("encoded_string",encoded_string);
                image_name = uname.getText().toString();
                map.put("image_name",image_name);

                return  map;
            }
        };
        requestQueue.add(request);

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
                System.out.println("ooooooooohhhhhhhhhhgggggggggggggggg  -> "+user);

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
        protected void onPreExecute() { }
    }

}