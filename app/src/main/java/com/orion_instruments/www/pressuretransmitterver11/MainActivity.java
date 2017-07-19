package com.orion_instruments.www.pressuretransmitterver11;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.R.attr.path;

public class MainActivity extends AppCompatActivity {
    GridView gridView;

    String[] gridViewString={
           "Bluetooth","Settings","Live","Edit Device"
    };
    int[] gridViewImageId={
            R.drawable.bluetooth,R.drawable.settings,
            R.drawable.live,R.drawable.editdevice,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Pressure Transmitter");

        CustomGridViewActivity customGridViewActivity=new CustomGridViewActivity(MainActivity.this,gridViewString,gridViewImageId);

        gridView=(GridView)findViewById(R.id.gridView);
        gridView.setAdapter(customGridViewActivity);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    Intent intent = new Intent(MainActivity.this, Bluetooth.class);
                    startActivity(intent);
                }
                if(i==1){
                    Intent intent = new Intent(MainActivity.this, Settings.class);
                    startActivity(intent);
                }
                if(i==2){
                    Intent intent = new Intent(MainActivity.this, Status.class);
                    startActivity(intent);
                }
                if(i==3){
                    Intent intent = new Intent(MainActivity.this, EditDevice.class);
                    startActivity(intent);
                }
               // Toast.makeText(MainActivity.this,"Selected: "+gridViewString[+i], Toast.LENGTH_LONG).show();
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////



    }
    }

