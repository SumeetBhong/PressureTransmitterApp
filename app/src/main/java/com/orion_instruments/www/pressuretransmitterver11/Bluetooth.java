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
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class Bluetooth extends AppCompatActivity {
    /////////////////////////////////Bluetooth parameters //////////////////////////////////////////
    final int handlerState = 0;
    Handler bluetoothIn;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    //  private final InputStream mmInStream;
    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Variables General
    public static String EXTRA_ADDRESS = "device_address";
    public ListView devicelist;
    public String info;
    //   public String txtOK;
    private StringBuilder recDataString = new StringBuilder();
    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Widgets

    SimpleCursorAdapter adapter;
    Button buttonSet;
    TextView sensor, txtArduino, txtTest, textView8, textView;
    //i  private Bluetooth.ConnectedThread mConnectedThread;
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Variables for Bluetooth
    //  Handler bluetoothIn;
    //  final int handlerState = 0;
    public String address;
    // private BluetoothAdapter btAdapter = null;
    //  private BluetoothSocket btSocket = null;
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public Set<BluetoothDevice> pairedDevices;
    public BluetoothAdapter myBluetooth = null;
    //   private Bluetooth.ConnectedThread mConnectedThread;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// On create Function
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ////////////////////////////////////////////////////////////////////////////////////////////
        // Initializations of the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        setTitle("Bluetooth");
        ////////////////////////////////////////////////////////////////////////////////////////////
        /// Initialize variables and widgets
        devicelist = (ListView) findViewById(R.id.listview);
        txtArduino = (TextView) findViewById(R.id.textView5);
        txtTest = (TextView) findViewById(R.id.textView9);
        textView8 = (TextView) findViewById(R.id.textView8);
        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        buttonSet = (Button) findViewById(R.id.buttonSet);
        textView = (TextView) findViewById(R.id.textView);
        //  textView.setVisibility(View.INVISIBLE);
        ///////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////Initialisation of DB/////////////////////////////////////////////


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
        ///////////////////////////////////////////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////////////////////////////////////////////
        /// On Click Listner for Bluetooth Settings
        buttonSet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentOpenBluetoothSettings = new Intent();
                intentOpenBluetoothSettings.setAction(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
                startActivity(intentOpenBluetoothSettings);
            }
        });


    }////////////////////////// End On Create

    /////////////////////////////////////////////////////////////////////////////////////////////
    /// On Resume Function
    /////////////////////////////////////////////////////////////////////////////////////////////
    public void onResume() {
        super.onResume();
        pairedDevicesList();
        // BluetoothDevice device = btAdapter.getRemoteDevice(address);

    }


/*    /////////////////////////////ON PAUSE STARTED///////////////////////////////////////////////////
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
 */
    // / // ////////////////////////////ON PAUSED ENDED///////////////////////////////////////////////////

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    /// Get the paired device list on list view
    /////////////////////////////////////////////////////////////////////////////////////////////
    private void pairedDevicesList() {

        pairedDevices = myBluetooth.getBondedDevices();
        ArrayList<String> list = new ArrayList<>();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice bt : pairedDevices) {
                list.add(bt.getName() + "\n" + bt.getAddress()); //Get the device's name and the address
            }
        } else {
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        devicelist.setAdapter(adapter);
        devicelist.setOnItemClickListener(myListClickListener);
        myBluetooth = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        //  checkBTState();
    }/// END PAIRED DEVICE LIST FUNCTION

    /////////////////////////////////////////////////////////////////////////////////////////////
    /// On click listner for paired device list
    /////////////////////////////////////////////////////////////////////////////////////////////
    public AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener() {
        @SuppressLint("HandlerLeak")
        public void onItemClick(AdapterView av, View v, int arg2, long arg3) {
            // Get the device MAC address, the last 17 chars in the View
            final TextView abc = (TextView) v;
            info = abc.getText().toString();
            address = info.substring(info.length() - 17);
            // This method will be executed once the timer is over
            // Start your app main activity
            Intent intent = new Intent(Bluetooth.this, MainActivity.class);
            //  Change the activity.
            intent.putExtra(EXTRA_ADDRESS, address); //this will be received at every (class) Activity)
            startActivity(intent);


            // close this activity
            BluetoothDevice device = myBluetooth.getRemoteDevice(address);
            ///////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////
/*

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

                }
            }
            mConnectedThread = new Bluetooth.ConnectedThread(btSocket);
            mConnectedThread.start();


            //I send a character when resuming.beginning transmission to check device is connected
            //If it is not an exception will be thrown in the write method and finish() will be called
            mConnectedThread.write("Setting~");
            mConnectedThread.run();
/*

            ///////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////
            bluetoothIn = new Handler() {

                public void handleMessage(android.os.Message msg) {
                    String dataInPrint;
                    //if message is what we want
                    String readMessage = (String) msg.obj;      // msg.arg1 = bytes from connect thread
                    Log.v("Bluetooth", readMessage);
                    //send value via text view
                    String messageCount;
                    // textView.append("\nMessage " + messageCount + ": " + readMessage);
                    textView.append(readMessage);
                    Intent intent = new Intent(Bluetooth.this, MainActivity.class);
                    intent.putExtra("Bluetooth", readMessage);
                    startActivity(intent);


                }


            };
        }
    };
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////CONNECT THREAD//////////////////////////////////////////////////////////
 /*   //create new class for connect thread
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
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////RUN PROCESS START///////////////////////////////////////////////

        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            // Keep looping to listen for received messages
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);            //read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);

                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, -1, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }
    */
            ////////////////////////////RUN PROCESS ENDED//////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////////////////////
            //////////////////////////WRITE METHOD STARTS///////////////////////////////////////////////
   /*     //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);//write bytes over BT connection via outstream
                // Share the sent message back to the UI Activity
                //   bluetoothIn.obtainMessage(handlerState, -1, -1, msgBuffer).sendToTarget();
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
            }
        }
    }*/
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////// END CLASS CONNECTED THREAD
            ///////////////////////////////////////////////////////////////////////////////////////////////////

        }/// ENd ACTIVITY BLUETOOTH
    };
}











