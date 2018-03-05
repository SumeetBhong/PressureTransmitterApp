package com.orion_instruments.www.pressuretransmitterver11;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class EditDevice extends AppCompatActivity {

    TextView fileContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_device);
        setTitle("Edit Device");


        fileContent = (TextView) findViewById(R.id.textView34);
////////////////////////////////////////////////////////////////////////////////////////////////////


    }
}




