package com.orion_instruments.www.pressuretransmitterver11;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.UUID;


public class MainActivity extends AppCompatActivity {
    Handler bluetoothIn;
    final int handlerState = 0;
    public static String EXTRA_ADDRESS = "device_address";
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();
  //   private MainActivity.ConnectedThread mConnectedThread;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // String for MAC address
    private static String address;
    GridView gridView;
    TextView textView29;

    String[] gridViewString = {
            "Bluetooth", "Settings", "Live", "Edit Device"
    };
    int[] gridViewImageId = {
            R.drawable.blelogo, R.drawable.settingslogo,
            R.drawable.livestatus, R.drawable.editdevicelogo,

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Pressure Transmitter");

        CustomGridViewActivity customGridViewActivity = new CustomGridViewActivity(MainActivity.this, gridViewString, gridViewImageId);

        textView29 = (TextView) findViewById(R.id.textView29);
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(customGridViewActivity);
        textView29.setText(address);
        textView29.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        //Get the MAC address from the DeviceListActivty via EXTRA
        address = intent.getStringExtra(EXTRA_ADDRESS);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {


                    Intent j = new Intent(MainActivity.this, Bluetooth.class);
                   //  j.putExtra(EXTRA_ADDRESS, address); //this will be received at ledControl (class) Activity
                    startActivity(j);
                }
                if (i == 1) {

                    //create device and set the MAC address
                    Intent j = new Intent(MainActivity.this, Settings.class);
                    j.putExtra(EXTRA_ADDRESS, address); //this will be received at ledControl (class) Activity
                    startActivity(j);
                }
                if (i == 2) {
                    Intent j = new Intent(MainActivity.this, Live.class);
                    j.putExtra(EXTRA_ADDRESS, address);
                    startActivity(j);
                }
                if (i == 3) {
                    Intent j = new Intent(MainActivity.this, EditDevice.class);
                    j.putExtra(EXTRA_ADDRESS, address);
                    startActivity(j);
                }
                // Toast.makeText(MainActivity.this,"Selected: "+gridViewString[+i], Toast.LENGTH_LONG).show();

            }
        });
    }

    //  public void onResume()
    //  {
    //      super.onResume();
    //   }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    }




