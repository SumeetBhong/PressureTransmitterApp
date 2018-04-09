package com.orion_instruments.www.pressuretransmitterver11;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;


public class Relay extends AppCompatActivity {

    TabHost tabRelay;
    Button button9,relaycancel;
    NumberPicker numberPicker2,numberPicker3,numberPicker4,numberPicker5,numberPicker6,numberPicker7,numberPicker8,numberPicker9,
    numberPicker10,numberPicker11,numberPicker12,numberPicker13,numberPicker14,numberPicker15,numberPicker16,numberPicker17;
    Switch switch19,switch20,switch21,switch22;
    TextView sensor,textRelay,textView52,textView53,textView54,textView55,textView58, txtString, txtStringLength;



    Handler bluetoothIn;
    final int handlerState = 0;

    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();
    private Relay.ConnectedThread mConnectedThread;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // String for MAC address
    private static String address;
    // SPP UUID service - this should work for most devices
    public static String EXTRA_ADDRESS = "device_address";
    public TabHost mytabs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relay);
        setTitle("Relay");

        button9=(Button)findViewById(R.id.button9);
<<<<<<< HEAD
     //   relaycancel=(Button)findViewById(R.id.relaycancel);

////////////////////////////////////////////////////////////////////////////////////////////////////
       /* relaycancel.setOnClickListener(new View.OnClickListener()
=======
        relaycancel=(Button)findViewById(R.id.relaycancel);

////////////////////////////////////////////////////////////////////////////////////////////////////
        relaycancel.setOnClickListener(new View.OnClickListener()
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Relay.this, Settings.class);
                startActivity(intent);
                // startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
            }
<<<<<<< HEAD
        });*/
=======
        });
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92
     ///////////////////////////////////////////////////////////////////////////////////////////////

        // Font path
        String fontPath = "fonts/arial.ttf";
        textView52=(TextView)findViewById(R.id.textView52);
        textView53=(TextView)findViewById(R.id.textView53);
        textView54=(TextView)findViewById(R.id.textView54);
        textView55=(TextView)findViewById(R.id.textView55);
        textView58=(TextView)findViewById(R.id.textView58);
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        // Applying font
        textView52.setTypeface(tf);
        textView53.setTypeface(tf);
        textView54.setTypeface(tf);
        textView55.setTypeface(tf);
        textView58.setTypeface(tf);

        textRelay=(TextView)findViewById(R.id.textRelay);
        textRelay.setVisibility(View.INVISIBLE);

        tabRelay = (TabHost)findViewById(R.id.tabRelay);

        tabRelay.setup();
       tabRelay = getTabHost();




        //Lets add the first Tab
        TabHost.TabSpec mSpec = tabRelay.newTabSpec("Relay 1");
        mSpec.setContent(R.id.relay1);
        mSpec.setIndicator("Relay 1");
        tabRelay.addTab(mSpec);

        //Lets add the second Tab
        mSpec = tabRelay.newTabSpec("Relay 2");
        mSpec.setContent(R.id.relay2);
        mSpec.setIndicator("Relay 2");
        tabRelay.addTab(mSpec);

        //Lets add the third Tab
        mSpec = tabRelay.newTabSpec("Relay 3");
        mSpec.setContent(R.id.relay3);
        mSpec.setIndicator("Relay 3");
        tabRelay.addTab(mSpec);

        //Lets add the Fourth Tab
        mSpec = tabRelay.newTabSpec("Relay 4");
        mSpec.setContent(R.id.relay4);
        mSpec.setIndicator("Relay 4");
        tabRelay.addTab(mSpec);

        //////// // ////////////////////////settings for number picker///////////////////////////////////////////
        numberPicker2=(NumberPicker)findViewById(R.id.numberPicker2);
        numberPicker3=(NumberPicker)findViewById(R.id.numberPicker3);
        numberPicker4=(NumberPicker)findViewById(R.id.numberPicker4);
        numberPicker5=(NumberPicker)findViewById(R.id.numberPicker5);

        numberPicker6=(NumberPicker)findViewById(R.id.numberPicker6);
        numberPicker7=(NumberPicker)findViewById(R.id.numberPicker7);
        numberPicker8=(NumberPicker)findViewById(R.id.numberPicker8);
        numberPicker9=(NumberPicker)findViewById(R.id.numberPicker9);

        numberPicker10=(NumberPicker)findViewById(R.id.numberPicker10);
        numberPicker11=(NumberPicker)findViewById(R.id.numberPicker11);
        numberPicker12=(NumberPicker)findViewById(R.id.numberPicker12);
        numberPicker13=(NumberPicker)findViewById(R.id.numberPicker13);

        numberPicker14=(NumberPicker)findViewById(R.id.numberPicker14);
        numberPicker15=(NumberPicker)findViewById(R.id.numberPicker15);
        numberPicker16=(NumberPicker)findViewById(R.id.numberPicker16);
        numberPicker17=(NumberPicker)findViewById(R.id.numberPicker17);



        numberPicker2.setMinValue(0);
        numberPicker2.setMaxValue(600);
        numberPicker2.setEnabled(true);
        numberPicker2.setWrapSelectorWheel(true);
<<<<<<< HEAD


    ////////////////////////////////////////////////////////////////////////////////////////////////
=======


<<<<<<< HEAD
    ////////////////////////////////////////////////////////////////////////////////////////////////
=======
////////////////////////////////////////////////////////////////////////////////////////////////////
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92
>>>>>>> 97b1299e9e0c5564bce661f69e8d1bd38e77aefa
        numberPicker3.setMinValue(0);
        numberPicker3.setMaxValue(600);
        numberPicker3.setEnabled(true);
        numberPicker3.setWrapSelectorWheel(true);

        ////////////////////////////////////////////////////////////////////////////////////////////

        numberPicker4.setMinValue(1);
        numberPicker4.setMaxValue(99);
        numberPicker4.setEnabled(true);
        numberPicker4.setWrapSelectorWheel(true);


     ///////////////////////////////////////////////////////////////////////////////////////////////

        numberPicker5.setMinValue(1);
        numberPicker5.setMaxValue(99);
        numberPicker5.setEnabled(true);
        numberPicker5.setWrapSelectorWheel(true);

     ///////////////////////////////////////////////////////////////////////////////////////////////

        numberPicker6.setMinValue(0);
        numberPicker6.setMaxValue(600);
        numberPicker6.setEnabled(true);
        numberPicker6.setWrapSelectorWheel(true);

     ///////////////////////////////////////////////////////////////////////////////////////////////

        numberPicker7.setMinValue(0);
        numberPicker7.setMaxValue(600);
        numberPicker7.setEnabled(true);
        numberPicker7.setWrapSelectorWheel(true);

     ///////////////////////////////////////////////////////////////////////////////////////////////

        numberPicker8.setMinValue(1);
        numberPicker8.setMaxValue(99);
        numberPicker8.setEnabled(true);
        numberPicker8.setWrapSelectorWheel(true);

     //////////////////////////////////////////////////////////////////////////////////////////////

        numberPicker9.setMinValue(1);
        numberPicker9.setMaxValue(99);
        numberPicker9.setEnabled(true);
        numberPicker9.setWrapSelectorWheel(true);

     ///////////////////////////////////////////////////////////////////////////////////////////////

        numberPicker10.setMinValue(0);
        numberPicker10.setMaxValue(600);
        numberPicker10.setEnabled(true);
        numberPicker10.setWrapSelectorWheel(true);

     ///////////////////////////////////////////////////////////////////////////////////////////////

        numberPicker11.setMinValue(0);
        numberPicker11.setMaxValue(600);
        numberPicker11.setEnabled(true);
        numberPicker11.setWrapSelectorWheel(true);

     ///////////////////////////////////////////////////////////////////////////////////////////////

        numberPicker12.setMinValue(1);
        numberPicker12.setMaxValue(99);
        numberPicker12.setEnabled(true);
        numberPicker12.setWrapSelectorWheel(true);

     ///////////////////////////////////////////////////////////////////////////////////////////////

        numberPicker13.setMinValue(1);
        numberPicker13.setMaxValue(99);
        numberPicker13.setEnabled(true);
        numberPicker13.setWrapSelectorWheel(true);

    ////////////////////////////////////////////////////////////////////////////////////////////////

        numberPicker14.setMinValue(0);
        numberPicker14.setMaxValue(600);
        numberPicker14.setEnabled(true);
        numberPicker14.setWrapSelectorWheel(true);

     ///////////////////////////////////////////////////////////////////////////////////////////////

        numberPicker15.setMinValue(0);
        numberPicker15.setMaxValue(600);
        numberPicker15.setEnabled(true);
        numberPicker15.setWrapSelectorWheel(true);

     ///////////////////////////////////////////////////////////////////////////////////////////////


        numberPicker16.setMinValue(1);
        numberPicker16.setMaxValue(99);
        numberPicker16.setEnabled(true);
        numberPicker16.setWrapSelectorWheel(true);

     ///////////////////////////////////////////////////////////////////////////////////////////////


        numberPicker17.setMinValue(1);
        numberPicker17.setMaxValue(99);
        numberPicker17.setEnabled(true);
        numberPicker17.setWrapSelectorWheel(true);


////////////////////////////////////////////////////////////////////////////////////////////////////


        ///////////////////////settings of switch///////////////////////////////////////////////////
        switch19=(Switch)findViewById(R.id.switch22);
        switch19.setChecked(false);
        switch19.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked) {
                    // The switch is disabled
                } else {
                    // The switch is enabled
                }
            }
        });
    ////////////////////////////////////////////////////////////////////////////////////////////////
        switch20=(Switch)findViewById(R.id.switch22);
        switch20.setChecked(false);
        switch20.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {



                if (isChecked) {
                    // The switch is disabled
                } else {
                    // The switch is enabled
                }
            }
        });

        switch21=(Switch)findViewById(R.id.switch22);
        switch21.setChecked(false);
        switch21.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {



                if (isChecked) {
                    // The switch is disabled
                } else {
                    // The switch is enabled
                }
            }
        });

        switch22=(Switch)findViewById(R.id.switch22);
        switch22.setChecked(false);
        switch22.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {



                if (isChecked) {
                    // The switch is disabled
                } else {
                    // The switch is enabled
                }
            }
        });
        ///////////////////////////////Tab auto selection/////////////////////////////////////////////////////////////
        tabRelay.setOnTabChangedListener(new TabHost.OnTabChangeListener()
        {
            @Override
            public void onTabChanged(String arg0)
            {

<<<<<<< HEAD
              //  Toast.makeText(getBaseContext(), "Im currently in tab with index::" + tabRelay.getCurrentTab(), Toast.LENGTH_SHORT).show();
=======
<<<<<<< HEAD
              //  Toast.makeText(getBaseContext(), "Im currently in tab with index::" + tabRelay.getCurrentTab(), Toast.LENGTH_SHORT).show();
=======
                Toast.makeText(getBaseContext(), "Im currently in tab with index::" + tabRelay.getCurrentTab(), Toast.LENGTH_SHORT).show();
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92
>>>>>>> 97b1299e9e0c5564bce661f69e8d1bd38e77aefa


            }
        });
<<<<<<< HEAD
     ///////////////////////////////////////////////////////////////////////////////////////////////

=======
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92
     /////////////////////////////////////////UPDATE THE DATA///////////////////////////////////////////////////////////
       button9.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v) {
                if(tabRelay.getCurrentTab() == 0)
                {
                    textRelay.setText("R1"+","+numberPicker2.getValue()+","+numberPicker3.getValue()+","+numberPicker4.getValue()+","+numberPicker5.getValue()+"~");

                }
                if(tabRelay.getCurrentTab() == 1)
                {
                    textRelay.setText("R2"+","+numberPicker7.getValue()+","+numberPicker6.getValue()+","+numberPicker8.getValue()+","+numberPicker9.getValue()+"~");

                }
                if(tabRelay.getCurrentTab() == 2)
                {
                    textRelay.setText("R3"+","+numberPicker11.getValue()+","+ numberPicker10.getValue()+","+numberPicker12.getValue()+","+numberPicker13.getValue()+"~");
                }
                if(tabRelay.getCurrentTab() == 3)
                {
                    textRelay.setText("R4"+","+numberPicker15.getValue()+","+numberPicker14.getValue()+","+ numberPicker16.getValue()+","+numberPicker17.getValue()+"~");
                }


                /*  (
                        numberPicker6.getValue()+","+numberPicker7.getValue()+","+numberPicker8.getValue()+","+numberPicker9.getValue()+","+numberPicker10.getValue()+","+
                        numberPicker11.getValue()+","+numberPicker12.getValue()+","+numberPicker13.getValue()+","+numberPicker14.getValue()+","+numberPicker15.getValue()+","+
                        numberPicker16.getValue()+","+numberPicker17.getValue());*/
                mConnectedThread.write(textRelay.getText().toString());    // Send text via Bluetooth
              //  Toast.makeText(getBaseContext(),textRelay.getText().toString() + "Data send to device", Toast.LENGTH_SHORT).show();
                Toast.makeText(getBaseContext(), "Data send to device", Toast.LENGTH_LONG).show();
            }
        });
        /////////////////////////////////////code for Rx from arduino//////////////////////////////////////////////////////////

       /* bluetoothIn = new Handler() {

            public void handleMessage(android.os.Message msg) {
                String dataInPrint;
                if (msg.what == handlerState)
                {                                     //if message is what we want
                    String readMessage = (String) msg.obj;                                                                // msg.arg1 = bytes from connect thread
                    recDataString.append(readMessage);                                      //keep appending to string until ~
                    int endOfLineIndex = recDataString.indexOf("~");                    // determine the end-of-line
                    if (endOfLineIndex > 0)
                    {                                           // make sure there data before ~
                        String sensor0 = recDataString.substring(0, endOfLineIndex);    // extract string
                        sensor.setText(sensor0);    //update the textviews with sensor values
                        //int value=Integer.parseInt(sensor.getText().toString());

                        sensor0 = " ";
                        //value = 0;
                    }
                    recDataString.delete(0, recDataString.length());                    //clear all string data
                    // strIncom =" ";
                }
            }
        };*/
        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();
    }

    //   Code for Send Receive
//////////////////////////////////////////////////////////////////////////////////////////////////////
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException
    {

        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onResume()
    {
        super.onResume();


        //Get MAC address from DeviceListActivity via intent
        Intent intent = getIntent();
        //Get the MAC address from the DeviceListActivty via EXTRA
        address = intent.getStringExtra(EXTRA_ADDRESS);
      // address = "20:16:01:18:23:43";
        //create device and set the MAC address
        BluetoothDevice device = btAdapter.getRemoteDevice(address);
        //sensor.setText();            /*

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e)
        {
            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
        }
        // Establish the Bluetooth socket connection.
        try
        {
            btSocket.connect();
        } catch (IOException e)
        {
            try
            {
                btSocket.close();
            } catch (IOException e2)
            {
                //insert code to deal with this
            }
        }
        mConnectedThread= new com.orion_instruments.www.pressuretransmitterver11.Relay.ConnectedThread(btSocket);
        mConnectedThread.start();

        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        mConnectedThread.write("");
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onPause()
    {
        super.onPause();
        try
        {
            //Don't leave Bluetooth sockets open when leaving activity
            btSocket.close();
        } catch (IOException e2) {
            //insert code to deal with this
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
    private void checkBTState()
    {

        if(btAdapter==null)
        {
            Toast.makeText(getBaseContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
        } else
        {
            if (btAdapter.isEnabled())
            {
            } else
            {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    public TabHost getTabHost() {
        return tabRelay;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //create new class for connect thread
    private class ConnectedThread extends Thread
    {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;


        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket)
        {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try
            {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e)
            { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }
     ///////////////////////////////////////////////////////////////////////////////////////////////
     ///////////////////////////////////////////////////////////////////////////////////////////////

        public void run()
        {
            byte[] buffer = new byte[256];
            int bytes;

            // Keep looping to listen for received messages
            while (true)
            {
                try
                {
                    bytes = mmInStream.read(buffer);            //read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e)
                {
                    break;
                }
            }
        }
     ///////////////////////////////////////////////////////////////////////////////////////////////
     ///////////////////////////////////////////////////////////////////////////////////////////////
        //write method
        public void write(String input)
        {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try
            {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e)
            {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
            }
        }


    }
}
