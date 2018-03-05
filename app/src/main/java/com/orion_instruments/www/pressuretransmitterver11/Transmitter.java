package com.orion_instruments.www.pressuretransmitterver11;

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
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Transmitter extends AppCompatActivity {

    Button button5,transmitcancel;
    Spinner spinner12,spinner13;
    TextView sensor,textView19, txtString, txtStringLength;
   // EditText editText,editText2,editText4,editText5;
    NumberPicker voltagepicker,currentpicker,voltspanpicker,currentspanpicker;

    Handler bluetoothIn;
    final int handlerState = 0;

    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();
    private Transmitter.ConnectedThread mConnectedThread;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // String for MAC address
    private static String address;
    // SPP UUID service - this should work for most devices
    public static String EXTRA_ADDRESS = "device_address";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transmitter);
        setTitle("Transmitter");


        spinner12=(Spinner) findViewById(R.id.spinner12);
        spinner13=(Spinner) findViewById(R.id.spinner13);

       /* editText=(EditText)findViewById(R.id.editText);
        editText2=(EditText)findViewById(R.id.editText2);
        editText4=(EditText)findViewById(R.id.editText4);
        editText5=(EditText)findViewById(R.id.editText5);*/
     ///////////////////////////////////////////////////////////////////////////////////////////////
     ///////////////////////////////////////////////////////////////////////////////////////////////

       voltagepicker=(NumberPicker)findViewById(R.id.voltagepicker);
       voltspanpicker=(NumberPicker)findViewById(R.id.voltspanpicker);
       currentpicker=(NumberPicker)findViewById(R.id.currentpicker);
       currentspanpicker=(NumberPicker)findViewById(R.id.currentspanpicker);

        voltagepicker.setMinValue(0);
        voltagepicker.setMaxValue(600);
        voltagepicker.setEnabled(true);
        voltagepicker.setWrapSelectorWheel(true);

     ///////////////////////////////////////////////////////////////////////////////////////////////

        voltspanpicker.setMinValue(0);
        voltspanpicker.setMaxValue(600);
        voltspanpicker.setEnabled(true);
        voltspanpicker.setWrapSelectorWheel(true);

     ///////////////////////////////////////////////////////////////////////////////////////////////

        currentpicker.setMinValue(0);
        currentpicker.setMaxValue(600);
        currentpicker.setEnabled(true);
        currentpicker.setWrapSelectorWheel(true);


     ///////////////////////////////////////////////////////////////////////////////////////////////
        currentspanpicker.setMinValue(0);
        currentspanpicker.setMaxValue(600);
        currentspanpicker.setEnabled(true);
        currentspanpicker.setWrapSelectorWheel(true);

     ///////////////////////////////////////////////////////////////////////////////////////////////
     ///////////////////////////////////////////////////////////////////////////////////////////////
        textView19 = (TextView) findViewById(R.id.textView19);
        textView19.setVisibility(View.INVISIBLE);

        //spinner4,12 elements
        final List<String> current = new ArrayList<String>();
        final List<String> voltage = new ArrayList<String>();
        //spinner 4
        current.add("0 mA-20 mA");
        current.add("4 mA-20 mA");
        current.add("20 mA-0 mA");
        current.add("20 mA-4 mA");



        //spinner12
        voltage.add("0-5 VDC");
        voltage.add("0-10 VDC");
        voltage.add("5-0 VDC");
        voltage.add("10-0 VDC");

        // Creating adapter for spinner
        final ArrayAdapter<String> currentAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, current);

        final ArrayAdapter<String> voltageAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, voltage);

        // Drop down layout style - list view with radio button
        currentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        voltageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //attaching data adapter to spinner
        spinner12.setAdapter(currentAdapter);
        spinner13.setAdapter(voltageAdapter);


        spinner12.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public int u;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                //String tempString =  String.valueOf(spinner.getSelectedItemPosition());
                //txtArduino.setText("M" + tempString + unitposition+"~");
                // TODO Auto-generated method stub

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }

        });
        /////////////////////////////////////////////////////////////////////////////////////////////
        spinner13.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                // TODO Auto-generated method stub

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }

        });

        ///////////////////////////////////////////////////////////////////////////////////////////////
      /*  transmitcancel=(Button)findViewById(R.id.transmitcancel);
        transmitcancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Transmitter.this, Settings.class);
                startActivity(intent);
                // startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
            }
        });  */
     ///////////////////////////////////////////////////////////////////////////////////////////////

        button5=(Button)findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                textView19.setText("T"+","+String.valueOf(spinner12.getSelectedItemPosition())+","+currentpicker.getValue()+","+currentspanpicker.getValue()+","+String.valueOf(spinner13.getSelectedItemPosition())+","+voltagepicker.getValue()+","+voltspanpicker.getValue()+"~");
                mConnectedThread.write(textView19.getText().toString());    // Send text via Bluetooth
              // Toast.makeText(getBaseContext(),textView19.getText().toString() + "Data send to device", Toast.LENGTH_SHORT).show();
                Toast.makeText(getBaseContext(), "Data send to device", Toast.LENGTH_LONG).show();
            }
        });

//////////////////////////////////////////////////////////////////////////////////////////////////////

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
        mConnectedThread= new com.orion_instruments.www.pressuretransmitterver11.Transmitter.ConnectedThread(btSocket);
        mConnectedThread.start();

        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        mConnectedThread.write("");
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
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e)
                {
                    break;
                }
            }
        }
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