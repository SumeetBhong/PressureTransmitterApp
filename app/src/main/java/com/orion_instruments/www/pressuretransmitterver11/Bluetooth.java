package com.orion_instruments.www.pressuretransmitterver11;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import static android.R.attr.name;
import static android.R.attr.path;

public class Bluetooth extends AppCompatActivity {
    public static String EXTRA_ADDRESS = "device_address";
    public ListView devicelist;
    public String info;
    Handler bluetoothIn;
    final int handlerState = 0;

    TextView sensor,txtArduino,txtTest,textView8;
    public String txtFile,txtOK;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();


    //Set<BluetoothDevice> bt;
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public Set<BluetoothDevice> pairedDevices;

    // String for MAC address
    private static String address;
    public BluetoothAdapter myBluetooth = null;
    Button buttonSet;
    private Bluetooth.ConnectedThread mConnectedThread;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        setTitle("Bluetooth");

        devicelist = (ListView) findViewById(R.id.listview);
        txtArduino = (TextView)findViewById(R.id.textView5) ;
        txtTest = (TextView)findViewById(R.id.textView9) ;
        textView8=(TextView)findViewById(R.id.textView8);
        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        buttonSet=(Button)findViewById(R.id.buttonSet);

        buttonSet.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                //  mConnectedThread.write(txtArduino.getText().toString());    // Send text via Bluetooth
                //  Toast.makeText(getBaseContext(),txtArduino.getText().toString() + "Data send to device", Toast.LENGTH_SHORT).show();
                Intent intentOpenBluetoothSettings = new Intent();
                intentOpenBluetoothSettings.setAction(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
                startActivity(intentOpenBluetoothSettings);
            }
        });
////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (myBluetooth == null) {
            //Show a mensag. that the device has no bluetooth adapter
            Toast.makeText(getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();
            //finish apk
            // finish();
        } else if (myBluetooth.isEnabled()) {
        } else {
            //Ask to the user turn the bluetooth on
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon, 1);
        }
        bluetoothIn = new Handler()
        {

            public void handleMessage(android.os.Message msg)
            {


                String dataInPrint;
                if (msg.what == handlerState)
                {                                     //if message is what we want
                    String readMessage = (String) msg.obj;      // msg.arg1 = bytes from connect thread
                    recDataString.append(readMessage);                                      //keep appending to string until ~
                   // int startofLineIndex=recDataString.indexOf("#");
                   // if (startofLineIndex>0){
                   //     txtOK=recDataString.substring(0,startofLineIndex);
                   //     char character= txtOK.charAt(0);
                  //  }
                    int endOfLineIndex = recDataString.indexOf("~");                    // determine the end-of-line
                    if (endOfLineIndex > 0)
                    {                                           // make sure there data before ~
                        txtOK = recDataString.substring(0, endOfLineIndex);    // extract string
                        char character = txtOK.charAt(0);


                        if(txtOK.equals("OK"))
                        {
                            //  String filename = "blesettings";
                            //  FileOutputStream outputStream;
                            //  try {
                            //      outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                            //      outputStream.write(address.getBytes());
                            //      outputStream.close();
                            //display file saved message
                            //      Toast.makeText(getBaseContext(), "File saved successfully!",
                            //              Toast.LENGTH_SHORT).show();

                            //   } catch (Exception e) {
                            //       e.printStackTrace();
                            //       Toast.makeText(getBaseContext(), "Error!",
                            //               Toast.LENGTH_SHORT).show();
                            //   }
                            mConnectedThread.write("Setting~");    // Send text via Bluetooth
                            txtArduino.setText(txtOK);
                            txtOK = "                ";

                        }
                        txtTest.setText(txtOK);
                        if (character == '[')
                        {
                            txtTest.setText(txtOK);
                            txtOK = "               ";
                        }


                    }


                    recDataString.delete(0, recDataString.length());



                }
            }


        };

    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void pairedDevicesList()
    {

        pairedDevices = myBluetooth.getBondedDevices();

        ArrayList<String> list = new ArrayList<>();

        if (pairedDevices.size() > 0)
        {
            for (BluetoothDevice bt : pairedDevices)
            {
                //list.add( bt.getName()); //Get the device's name
                list.add(bt.getName() + "\n" + bt.getAddress()); //Get the device's name and the address
            }
        }
        else
        {
        Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        devicelist.setAdapter(adapter);

        devicelist.setOnItemClickListener(myListClickListener);
        //Method called when the device from the list is clicked


        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener()
    {
        public void onItemClick(AdapterView av, View v, int arg2, long arg3)
        {
            // Get the device MAC address, the last 17 chars in the View
            final TextView abc = (TextView) v;
            info = abc.getText().toString();
            String address = info.substring(info.length() - 17);
            address = info.substring( info.length() - 17);// "20:16:01:18:23:43"; //intent.getStringExtra(DeviceList.EXTRA_ADDRESS);
            //create device and set the MAC address
            BluetoothDevice device = btAdapter.getRemoteDevice(address);

            try {
                btSocket = createBluetoothSocket(device);
            } catch (IOException e) {
                Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
            }
            // Establish the Bluetooth socket connection.
            try
            {
                btSocket.connect();
            } catch (IOException e) {
                try
                {
                    btSocket.close();
                } catch (IOException e2)
                {
                    //insert code to deal with this
                }
            }
            mConnectedThread = new ConnectedThread(btSocket);
            mConnectedThread.start();

             mConnectedThread.write("Orion~");    // Send text via Bluetooth
             Toast.makeText(getBaseContext(), "Orion~", Toast.LENGTH_SHORT).show();

            // Make an intent to start next activity.
            // Intent i = new Intent(Bluetooth.this, MainActivity.class);
            //  Change the activity.
            // i.putExtra("device_address", address); //this will be received at ledControl (class) Activity
            //  startActivity(i);
        }


    };

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException
    {

        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    public void onResume()
    {
        super.onResume();
        pairedDevicesList();



    }
    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
    private void checkBTState()
    {

        if(btAdapter==null) {
            Toast.makeText(getBaseContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (btAdapter.isEnabled()) {
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }
    //create new class for connect thread
    private class ConnectedThread extends Thread
    {
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
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[512];
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








