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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class DeviceSettings extends AppCompatActivity {
    Button button7,devicecancel;
    TextView sensor,textDevice,datetext,timetext;
    EditText editName,editTag;
    Spinner locationspinner,languagespinner;

    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();
    private DeviceSettings.ConnectedThread mConnectedThread;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // String for MAC address
    private static String address;

    Handler bluetoothIn;
    final int handlerState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_settings);
        setTitle("Device Settings");


        editName=(EditText)findViewById(R.id.editName);
        editName.getText().toString();

        editTag=(EditText)findViewById(R.id.editTag);
        editTag.getText().toString();

        timetext=(TextView)findViewById(R.id.timetext);
        datetext=(TextView)findViewById(R.id.datetext);


        Calendar c = Calendar.getInstance();

        int seconds = c.get(Calendar.SECOND);
        int minutes = c.get(Calendar.MINUTE);
        int hour = c.get(Calendar.HOUR);
        String time = hour + ":" + minutes + ":" + seconds;


        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        String date = day + "/" + month + "/" + year;

// Assuming that you need date and time in a separate
// textview named txt_date and txt_time.

        datetext.setText(date);
        timetext.setText(time);

     /////////////////////////////////////////Spinner Settings//////////////////////////////////////////////////////
        locationspinner=(Spinner)findViewById(R.id.locationspinner);
        final List<String>location= new ArrayList<>();
        location.add("India");
        location.add("China");
        location.add("Japan");
        location.add("UK");
        location.add("USA");
        // Creating adapter for spinner
        final ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, location);

        // Drop down layout style - list view with radio button
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        locationspinner.setAdapter(locationAdapter);

        locationspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

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
        ////////////////////////////////////////////////////////////////////////////////////////////

        languagespinner=(Spinner)findViewById(R.id.languagespinner);
        final List<String>language= new ArrayList<>();
        language.add("English");
        language.add("Chinese");
        language.add("Japanise");
        language.add("UK");
        language.add("Latin English");

        // Creating adapter for spinner
        final ArrayAdapter<String> languageAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, language);

        // Drop down layout style - list view with radio button
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        languagespinner.setAdapter(languageAdapter);

        languagespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
          { @Override
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

     ///////////////////////////////////////////////////////////////////////////////////////////////
        textDevice=(TextView)findViewById(R.id.textDevice);
        textDevice.setVisibility(View.INVISIBLE);

     ///////////////////////////////////////////////////////////////////////////////////////////////
       /* devicecancel=(Button)findViewById(R.id.devicecancel);
        devicecancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeviceSettings.this, Settings.class);
                startActivity(intent);
                // startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
            }
        });  */
     ///////////////////////////////////////////////////////////////////////////////////////////////

       button7=(Button)findViewById(R.id.button7);

        button7.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v) {
                textDevice.setText("D" +","+timetext.getText().toString()+","+datetext.getText().toString()+","+String.valueOf(locationspinner.getSelectedItemPosition())+","+String.valueOf(languagespinner.getSelectedItemPosition())+
                      "," +editName.getText().toString()+","+editTag.getText().toString()+","+ "~");
                mConnectedThread.write(textDevice.getText().toString());    // Send text via Bluetooth
               // Toast.makeText(getBaseContext(),textDevice.getText().toString() + "Data send to device", Toast.LENGTH_SHORT).show();
                Toast.makeText(getBaseContext(), "Data send to device", Toast.LENGTH_LONG).show();
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
        //address = intent.getStringExtra(Startup.EXTRA_ADDRESS);
        address = "20:16:01:18:23:43";
        //create device and set the MAC address
        BluetoothDevice device = btAdapter.getRemoteDevice(address);
        //sensor.setText();

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
        mConnectedThread= new com.orion_instruments.www.pressuretransmitterver11.DeviceSettings.ConnectedThread(btSocket);
        mConnectedThread.start();

        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        mConnectedThread.write("");
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
////////////////////////////////////////////////////////////////////////////////////////////////////
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
