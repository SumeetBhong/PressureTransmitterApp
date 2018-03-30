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
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class devicecalibration extends AppCompatActivity {

    int minteger,ninteger,ointeger,pinteger=0;

    Button zeroset,spanset,deviceupdate,zeroup,zerofineup,zerodown,zerofinedown,spanup,spanfineup,spandown,spanfinedown;
    TextView devicetext,zerotext,spantext,zerofinetext,zero;
    EditText title;
    NumberPicker zeropicker,spanpicker;
//    GridView coarsegrid,finegrid,spancoarsegrid,spanfinegrid;



    Handler bluetoothIn;
    final int handlerState = 0;

    public int count=600;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();
    private devicecalibration.ConnectedThread mConnectedThread;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // String for MAC address
    private static String address;
    // SPP UUID service - this should work for most devices
    public static String EXTRA_ADDRESS = "device_address";
 //   private ListAdapter customGridViewActivity;




    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devicecalibration);
        this.setTitle("Device calibration");

      //  coarsegrid=(GridView)findViewById(R.id.coarsegrid);
      //  finegrid=(GridView)findViewById(R.id.finegrid);
      //  spancoarsegrid=(GridView)findViewById(R.id.spancoarsegrid);
      //  spanfinegrid=(GridView)findViewById(R.id.spanfinegrid);


        zero = (TextView) findViewById(R.id.zero) ;
        zeroset = (Button) findViewById(R.id.zeroset);
        zeroup=(Button)findViewById(R.id.zeroup);
        zerodown=(Button)findViewById(R.id.zerodown);
        zerofineup=(Button)findViewById(R.id.zerofineup);
        zerofinedown=(Button)findViewById(R.id.zerofinedown);


        deviceupdate = (Button) findViewById(R.id.deviceupdate);
        zerotext=(TextView)findViewById(R.id.zerotext);
      //  zerotext.setVisibility(View.INVISIBLE);

        zerofinetext=(TextView)findViewById(R.id.zerofinetext);


        spantext=(TextView)findViewById(R.id.spantext) ;
              ////////////////////////////////////////////////////////////////////////////////////////////

     /// / // //////////////////////////////////////////////////////////////////////////////////////


            zeroup.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                //    zerotext.setText("");
                    mConnectedThread.write("C");
                }
            });


            zerodown.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                 //   zerotext.setText();
                    mConnectedThread.write("c");
                }
            });


            zerofineup.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                //    zerofinetext.setText("");
                    mConnectedThread.write("F");

                }
            });


            zerofinedown.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                 //   zerofinetext.setText("");
                    mConnectedThread.write("f");
                }
            });

        ////////////////////////////////////////////////////////////////////////////////////////////



        ////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////

        zeroset.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                   zero.setText("Span");
                   mConnectedThread.write("s");    // Send text via Bluetooth
               zeroset.setVisibility(View.INVISIBLE);
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////


        deviceupdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               // devicetext.setText("C" + "," + zerotext.getText() + ","  + spantext.getText() +","+devicetext.getText()+ "~");
            mConnectedThread.write("u");    // Send text via Bluetooth
                // Toast.makeText(getBaseContext(),textView19.getText().toString() + "Data send to device", Toast.LENGTH_SHORT).show();
           //     Toast.makeText(getBaseContext(), "Data send to device", Toast.LENGTH_LONG).show();
            }
        });

        /////////////////////////////////////code for Rx from arduino//////////////////////////////////////////////////////////

        bluetoothIn = new Handler() {

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
                      //  sensor.setText(sensor0);    //update the textviews with sensor values
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
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
 /*   public void increaseInteger(View view)
    {

            minteger = minteger + 1;
            display(minteger);

            ninteger = ninteger + 1;
            display(ninteger);

        ointeger = ointeger + 1;
        display(ointeger);

        pinteger = pinteger + 1;
        display(pinteger);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void decreaseInteger(View view)
    {
        minteger = minteger - 1;
        ninteger=ninteger-1;
       ointeger=ointeger-1;
        pinteger=pinteger-1;

        display(minteger);
        display(ninteger);
        display(ointeger);
        display(pinteger);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void display(int number)
    {

        if(zeroup.isPressed()||zerodown.isPressed())
        {
            zerotext.setText("" + number);
        }
        else
        {
            zerofinetext.setText("" + number);
        }
        if(spanup.isPressed()|| spandown.isPressed())
        {
            spantext.setText(""+number);

        }
        else
        {
            devicetext.setText(""+number);
        }
    }
    */
///////////////////////////////////////////////////////////////////////////////////////////////////
    //   Code for Send Receive
////////////////////////////////////////////////////////////////////////////////////////////////////
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
        //  address = "20:16:01:18:23:43";

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
        mConnectedThread= new com.orion_instruments.www.pressuretransmitterver11.devicecalibration.ConnectedThread(btSocket);
        mConnectedThread.start();

        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        mConnectedThread.write("U");
    }

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
                    bluetoothIn.obtainMessage(handlerState, -1, -1, readMessage).sendToTarget();
                } catch (IOException e)
                {
                    break;
                }
            }
        }
     ////////////////////////////////////////////////////////////////////////////////////////////////
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

