package com.orion_instruments.www.pressuretransmitterver11;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class WiFI extends AppCompatActivity {

    Switch wifiswitch;
   String switchOn="ON";
   String switchOff="OFF";

<<<<<<< HEAD
    public TextView sensor, txtString, txtStringLength, textView33,wifistatus;
=======
    TextView sensor, txtString, txtStringLength, textView33,wifistatus;
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92
    private EditText editText9, editText3;
    private CheckBox checkBox;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();
    private WiFI.ConnectedThread mConnectedThread;

    //private Mode.ConnectedThread mConnectedThread;
    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // String for MAC address
    private static String address;

    // SPP UUID service - this should work for most devices
    public static String EXTRA_ADDRESS = "device_address";

    // Tag for logging
    private final String TAG = getClass().getSimpleName();

    Button button4,wificancel;
    Handler bluetoothIn;
    final int handlerState = 0;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wi_fi);
        setTitle("Wifi");

<<<<<<< HEAD
=======

>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92
        //Get MAC address from DeviceListActivity via intent
        Intent intent = getIntent();
        //Get the MAC address from the DeviceListActivty via EXTRA
        address = intent.getStringExtra(EXTRA_ADDRESS);
        //  address = "20:16:01:18:23:43";

        textView33=(TextView)findViewById(R.id.textView33);
        textView33.setVisibility(View.INVISIBLE);


        // get the password EditText
        editText9 = (EditText) findViewById(R.id.editText9);
        editText3 = (EditText) findViewById(R.id.editText3);



        // get the show/hide password Checkbox
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        button4 = (Button) findViewById(R.id.button4);
     ///////////////////////////////////////////////////////////////////////////////////////////////
      /*  wificancel=(Button)findViewById(R.id.wificancel);
        wificancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WiFI.this, Settings.class);
                startActivity(intent);
                // startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
            }
        }); */
////////////////////////////////////////////////////////////////////////////////////////////////////
        // get switch declaration

        wifiswitch=(Switch)findViewById(R.id.wifiswitch) ;
        wifistatus=(TextView)findViewById(R.id.wifistatus);
        wifistatus.setVisibility(View.INVISIBLE);
<<<<<<< HEAD
        wifistatus.setText("0");
=======
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92


        // add onCheckedListener on checkbox
        // when user clicks on this checkbox, this is the handler.
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    editText9.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    editText9.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

<<<<<<< HEAD
////////////////////////////////////////////////////////////////////////////////////////////////////
     ///////////////////////////////////////////////////////////////////////////////////////////////
=======
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92
        //Set a CheckedChange Listener for Switch Button
        wifiswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton cb, boolean on){
                if(on)
                {
                    //Do something when Switch button is on/checked
<<<<<<< HEAD
                    wifistatus.setText("1");
=======
                    wifistatus.setText("WIFI is on.....");
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92
                }
                else
                {
                    //Do something when Switch is off/unchecked
<<<<<<< HEAD
                    wifistatus.setText("0");
=======
                    wifistatus.setText("WIFI is off.....");
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92
                }
            }
        });


        ////////////////////////////////////////////////////////////////////////////////////////////

        bluetoothIn = new Handler() {

            public void handleMessage(android.os.Message msg) {
                String dataInPrint;
                   if (msg.what == handlerState) {                                     //if message is what we want
                    String readMessage = (String) msg.obj;                                                                // msg.arg1 = bytes from connect thread
                    recDataString.append(readMessage);                                      //keep appending to string until ~
                    int endOfLineIndex = recDataString.indexOf("~");                    // determine the end-of-line
                    if (endOfLineIndex > 0) {                                           // make sure there data before ~
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
        };
        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();
        ////////////////////////////set up on click listeners//////////////////////////////////////////////////////////////

        button4.setOnClickListener(new View.OnClickListener() {
            public int I;

<<<<<<< HEAD

            public void onClick(View v) {
                if(editText3==null){
                    editText3.setText("0");
                }
                if(editText9==null){
                    editText9.setText("0");
                }

                mConnectedThread.write("W" + "," + editText3.getText().toString() + ","+ editText9.getText().toString() + "," +wifistatus.getText().toString()+"~");    // Send text via Bluetooth
=======
                textView33.setText("W"+","+str+","+str1+","+wifistatus+"~");
                mConnectedThread.write(textView33.getText().toString());    // Send text via Bluetooth
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92
               // Toast.makeText(getBaseContext(), textView33.getText().toString() + "Data send to device", Toast.LENGTH_SHORT).show();
                Toast.makeText(getBaseContext(), "Data send to device", Toast.LENGTH_LONG).show();
            }
        });
    }

        //   Code for Send Receive
//////////////////////////////////////////////////////////////////////////////////////////////////////
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onResume() {
        super.onResume();


//Get MAC address from DeviceListActivity via intent
        Intent intent = getIntent();
        //Get the MAC address from the DeviceListActivty via EXTRA
        address = intent.getStringExtra(EXTRA_ADDRESS);
        //  address = "20:16:01:18:23:43";

        //create device and set the MAC address
        BluetoothDevice device = btAdapter.getRemoteDevice(address);
        //sensor.setText();            /*

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
        }
        // Establish the Bluetooth socket connection.
        try {
            btSocket.connect();
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
                //insert code to deal with this
            }
        }
        mConnectedThread = new com.orion_instruments.www.pressuretransmitterver11 .WiFI.ConnectedThread(btSocket);
        mConnectedThread.start();

        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        mConnectedThread.write("");
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            //Don't leave Bluetooth sockets open when leaving activity
            btSocket.close();
        } catch (IOException e2) {
            //insert code to deal with this
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
    private void checkBTState() {

        if (btAdapter == null) {
            Toast.makeText(getBaseContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (btAdapter.isEnabled()) {
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
//create new class for connect thread
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;


        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];
            int bytes;

            // Keep looping to listen for received messages
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);            //read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
            }
        }

    }

}

