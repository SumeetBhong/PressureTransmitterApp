package com.orion_instruments.www.pressuretransmitterver11;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class Bluetooth extends AppCompatActivity
{
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static String EXTRA_ADDRESS = "device_address";
    public ListView devicelist;
    public String info;
    Handler bluetoothIn;
    final int handlerState = 0;

    TextView txtArduino,txtTest,textView;
    Button buttonSet;
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
    private Bluetooth.ConnectedThread mConnectedThread;
////////////////////////////////////////////////////////////////////////////////////////////////////

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        setTitle("Bluetooth");

        devicelist = (ListView) findViewById(R.id.listview);
        txtArduino = (TextView)findViewById(R.id.textView5) ;
        txtTest = (TextView)findViewById(R.id.sensor) ;
        buttonSet=(Button)findViewById(R.id.buttonSet);
        myBluetooth = BluetoothAdapter.getDefaultAdapter();

        if (myBluetooth == null) {
            //Show a mensag. that the device has no bluetooth adapter
            Toast.makeText(getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();
            //finish apk
            // finish();
        } else if (myBluetooth.isEnabled())
        {
        }
        else
        {
            //Ask to the user turn the bluetooth on
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon, 1);
        }

        buttonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentOpenBluetoothSettings = new Intent();
                intentOpenBluetoothSettings.setAction(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
                startActivity(intentOpenBluetoothSettings);

            }
        });

        bluetoothIn = new Handler()
        {

            public void handleMessage(android.os.Message msg)
            {
                String dataInPrint;
                if (msg.what == handlerState)
                {                                     //if message is what we want
                    String readMessage = (String) msg.obj;      // msg.arg1 = bytes from connect thread
                    recDataString.append(readMessage);                                      //keep appending to string until ~
                    txtOK = recDataString.toString();
                    //String tempString = txtOK.substring(0,3);
                    txtTest.setText(txtOK);
                    if(txtOK.trim().equalsIgnoreCase("#OK~"))
                    {
                        recDataString.delete(0, recDataString.length());
                        mConnectedThread.write("Setting~");

                    }
                    if( txtOK.charAt(0) == 'K'){
                        try
                        {
                            btSocket.close();
                        } catch (IOException e2)
                        {
                            //insert code to deal with this
                        }
                        // Make an intent to start next activity.
                         Intent i = new Intent(Bluetooth.this, MainActivity.class);
                        // Change the activity.
                         i.putExtra("device_address", address); //this will be received at ledControl (class) Activity
                         startActivity(i);

                    }

                    //recDataString.delete(0, recDataString.length());


                }
            }


        };
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
    /// On Click Listner for Bluetooth Settings


    ////////////////////////////////////////////////////////////////////////////////////////////////

    @SuppressLint("HandlerLeak")
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

     ///////////////////////////////////////////////////////////////////////////////////////////////
     ///////////////////////////////////////////////////////////////////////////////////////////////

        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////




    /////////////////////////////////////////////////////////////////////////////////////////////////
    public AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener()
    {
        public void onItemClick(AdapterView av, View v, int arg2, long arg3)
        {
            // Get the device MAC address, the last 17 chars in the View
            final TextView abc = (TextView) v;
            info = abc.getText().toString();
            address = info.substring( info.length() - 17);// "20:16:01:18:23:43"; //intent.getStringExtra(DeviceList.EXTRA_ADDRESS);
            //create device and set the MAC address
            BluetoothDevice device = btAdapter.getRemoteDevice(address);

            try
            {
                btSocket = createBluetoothSocket(device);
            }
            catch (IOException e)
            {
                Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
            }
            // Establish the Bluetooth socket connection.
            try
            {
                btSocket.connect();
            }
            catch (IOException e)
            {
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
            Toast.makeText(getBaseContext(), "Orion", Toast.LENGTH_SHORT).show();
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
////////////////////////////////////////////////////////////////////////////////////////////////////
@Override
public void onPause()
{
    super.onPause();
    try
    {
        //Don't leave Bluetooth sockets open when leaving activity
        btSocket.close();
    }
    catch (IOException e2)
    {
        //insert code to deal with this
    }
}
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
    private void checkBTState()
    {

        if(btAdapter==null)
        {
            Toast.makeText(getBaseContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
        }
        else
            {
            if (btAdapter.isEnabled())
            {
            }
            else
            {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
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
            }
            catch (IOException e)
            {

            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
        public void run()
        {
            byte[] buffer = new byte[1024];
            int bytes;

            // Keep looping to listen for received messages
            while (true)
            {
                try
                {
                    bytes = mmInStream.read(buffer);            //read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, -1, -1, readMessage).sendToTarget();
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
            }
            catch (IOException e)
            {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();

            }
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////***END***//////////////////////////////////////////////////////

}