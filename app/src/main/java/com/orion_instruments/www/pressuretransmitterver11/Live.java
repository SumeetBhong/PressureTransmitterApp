package com.orion_instruments.www.pressuretransmitterver11;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
<<<<<<< HEAD
import android.view.View;
=======
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import static com.orion_instruments.www.pressuretransmitterver11.MainActivity.EXTRA_ADDRESS;

public class Live extends AppCompatActivity {
<<<<<<< HEAD
    TextView sensor,textaddress,txtArduino, txtString, txtStringLength;
=======
    TextView sensor, textView4, txtArduino, txtString, txtStringLength;

>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92
    Handler bluetoothIn;
    final int handlerState = 0;                        //used to identify handler message
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();
    private ConnectedThread mConnectedThread;
    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // String for MAC address
    private static String address;
    // SPP UUID service - this should work for most devices
    public static String EXTRA_ADDRESS = "device_address";
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////GRAPH VIEW PARAMETERS/////////////////////////////////


    private static final Random RANDOM = new Random();
    //private PointsGraphSeries<DataPoint> series;
    private LineGraphSeries<DataPoint> series;
    private int lastX = 0;

<<<<<<< HEAD



    @SuppressLint("HandlerLeak")
=======
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        setTitle("Live");
<<<<<<< HEAD
=======

        ////////////////////////////////////////////////////////////////////////////////////////////////
        //Get MAC address from DeviceListActivity via intent
        Intent intent = getIntent();
        //Get the MAC address from the DeviceListActivty via EXTRA
        address = intent.getStringExtra(EXTRA_ADDRESS);
        // address="20:16:01:18:23:43";
        ///////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92

        // send = (Button)  findViewById(R.id.button2);
        GraphView graph = (GraphView) findViewById(R.id.graph);
        // data
        //series = new PointsGraphSeries<DataPoint>();
        series = new LineGraphSeries<DataPoint>();

        graph.addSeries(series);
        // customize a little bit viewport
        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);

        viewport.setScalable(true);
        viewport.setMinY(0);
        viewport.setMaxY(600.00);
        viewport.setXAxisBoundsManual(true);
        //viewport.setMinX(0);
        //viewport.setMaxX(1000);
        viewport.scrollToEnd();
        lastX = 0;
        //txtString = (TextView) findViewById(R.id.editText);
        sensor = (TextView) findViewById(R.id.textView4);
<<<<<<< HEAD
        textaddress=(TextView)findViewById(R.id.textaddress);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        //Get MAC address from DeviceListActivity via intent
        Intent intent = getIntent();
        //Get the MAC address from the DeviceListActivty via EXTRA
        address = intent.getStringExtra(EXTRA_ADDRESS);
        //address="20:16:01:18:23:43";
        textaddress.setText(address);
        textaddress.setVisibility(View.INVISIBLE);


=======
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92

        ////////////////// Code for rx of data from arduino  ////////////////////////////////////////////////////
        bluetoothIn = new Handler() {

            public void handleMessage(android.os.Message msg) {
                String dataInPrint;
                if (msg.what == handlerState) {                                     //if message is what we want
                    String readMessage = (String) msg.obj;      // msg.arg1 = bytes from connect thread

                    recDataString.append(readMessage);                                      //keep appending to string until ~
                    int endOfLineIndex = recDataString.indexOf("~");                    // determine the end-of-line
<<<<<<< HEAD
                    if (endOfLineIndex > 0)
                    {                                           // make sure there data before ~
                        if(recDataString.charAt(0) == '#')
                        {
                            String sensor0 = recDataString.substring(1, endOfLineIndex);    // extract string;
                            sensor.append(sensor0);    //update the textviews with sensor values
                           // float value=Integer.parseInt(sensor.getText().toString());
                            float value = Float.parseFloat(sensor0);
                            series.appendData(new DataPoint(lastX++, value), true, 1000);
                            sensor0 = " ";
                           //value = 0;
                        }
=======
                    if (endOfLineIndex > 0) {                                           // make sure there data before ~
                        String sensor0 = recDataString.substring(0, endOfLineIndex);    // extract string
                        sensor.setText(sensor0);    //update the textviews with sensor values
                        //int value=Integer.parseInt(sensor.getText().toString());

                        sensor0 = " ";
                        //value = 0;
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92
                    }
                    recDataString.delete(0, recDataString.length());                    //clear all string data
                    // strIncom =" ";
                }

              /*  String dataInPrint;
                //if message is what we want
                String readMessage = (String) msg.obj;      // msg.arg1 = bytes from connect thread
                Log.v("Bluetooth", readMessage);
                //send value via text view
                String messageCount;
                // textView.append("\nMessage " + messageCount + ": " + readMessage);
                sensor.append(readMessage);
                // Intent intent = new Intent(Bluetooth.this, DbAdapter.class);
                // intent.putExtra("Bluetooth", readMessage);
                //  startActivity(intent);*/

            }


        };
     ///////////////////////////////////////////////////////////////////////////////////////////////
     ////////////////////////////////////////////////////////////////////////////////////////////////
        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////
///   Code for Send Receive
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
        //  Intent intent = getIntent();
        //Get the MAC address from the DeviceListActivty via EXTRA
<<<<<<< HEAD
        //   address = intent.getStringExtra(EXTRA_ADDRESS);
        //address="20:16:01:18:23:43";
        //create device and set the MAC address
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

=======
        address = intent.getStringExtra(EXTRA_ADDRESS);
        //create device and set the MAC address
        BluetoothDevice device = btAdapter.getRemoteDevice(address);
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
        }
        // Establish the Bluetooth socket connection.
        try {
            btSocket.connect();
<<<<<<< HEAD
        }

        catch (IOException e)
        {
            try
            {
                btSocket.close();
            } catch (IOException e2)
            {

            }
        }
        mConnectedThread = new Live.ConnectedThread(btSocket);
=======
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
                //insert code to deal with this
            }
        }
        mConnectedThread = new ConnectedThread(btSocket);
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92
        mConnectedThread.start();


        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
<<<<<<< HEAD
        new Timer().scheduleAtFixedRate(new TimerTask() {
                                            @Override
                                            public void run() {
                                                   mConnectedThread.write("L~");
                                            }
                                        }, 0, 1000);
        //mConnectedThread.write("L~");


    }
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
=======
        mConnectedThread.write("x");
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92
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

<<<<<<< HEAD
/////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        public void run()
        {
=======
        public void run() {
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92
            byte[] buffer = new byte[256];
            int bytes;

            // Keep looping to listen for received messages
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);            //read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, -1, -1, readMessage).sendToTarget();
<<<<<<< HEAD
                } catch (IOException e)
                {
=======
                } catch (IOException e) {
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92
                    break;
                }
            }
        }
     ///////////////////////////////////////////////////////////////////////////////////////////////
        //write method

        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
                finish();

            }
            /////////////////////////////////////////////////////////////////////////////////////////
            //////////////////////////////////THE END////////////////////////////////////////////////


        }
    }
}