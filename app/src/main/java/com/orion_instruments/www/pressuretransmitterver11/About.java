package com.orion_instruments.www.pressuretransmitterver11;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class About extends AppCompatActivity {
    EditText fileContent;
    EditText editText16;
    EditText editText17;
    EditText editText18;
    EditText editText19;
    EditText editText21;

    final String Serialnumber = "Serial number";
    final String Range = "Range";
    final String Firmwareversion = "Firmware version";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle("About");



      //  try {
        //PlayWithRawFiles(R.id.blesettings);
      //  } catch (IOException e) {
      //      Toast.makeText(getApplicationContext(),
      //              "Problems: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
   // }// onCreate

    public void PlayWithRawFiles(int resource) throws IOException {
        String str="";
        StringBuffer buf = new StringBuffer();
        InputStream is = this.getResources().openRawResource(resource);

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        if (is!=null) {
            while ((str = reader.readLine()) != null) {
                buf.append(str + "\n" );
            }
        }
        is.close();
        Toast.makeText(getBaseContext(),
                buf.toString(), Toast.LENGTH_LONG).show();


    }

} // FilesDemo4





