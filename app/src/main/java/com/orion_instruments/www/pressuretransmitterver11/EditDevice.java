package com.orion_instruments.www.pressuretransmitterver11;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.*;


import java.io.InputStream;
import java.nio.Buffer;

import static com.orion_instruments.www.pressuretransmitterver11.R.id.textView34;

public class EditDevice extends AppCompatActivity {

   TextView fileContent;
    EditText editText16;
    EditText editText17;
    EditText editText18;
    EditText editText19;
    EditText editText21;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_device);
        setTitle("Edit Device");

        fileContent = (TextView) findViewById(R.id.textView34);
////////////////////////////////////////////////////////////////////////////////////////////////////
        try {
            PlayWithRawFiles(R.raw.dsettings);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Problems: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }// onCreate

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
        //Toast.makeText(getBaseContext(),buf.toString(), Toast.LENGTH_LONG).show();
        fileContent.setText(buf.toString());

    }

} // FilesDemo4




