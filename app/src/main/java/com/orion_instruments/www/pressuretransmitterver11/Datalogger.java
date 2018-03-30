package com.orion_instruments.www.pressuretransmitterver11;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class Datalogger extends AppCompatActivity {
    // Spinner spinner3;
    String send_to;
    Button logger;
    private Button datebutton,readbutton;
    NumberPicker secondspicker;
    TextView textView23, sensor,datetext;
    TabHost tabHost2;

    static final int DATE_DIALOG_ID = 0;

    Handler bluetoothIn;
    final int handlerState = 0;

    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();
    private Datalogger.ConnectedThread mConnectedThread;
    private int mYear, mMonth, mDay, Hour, Minute;
    private String day,month,year;
    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // String for MAC address
    private static String address;
    // SPP UUID service - this should work for most devices
    public static String EXTRA_ADDRESS = "device_address";



    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datalogger);
        setTitle("Datalogger");

        textView23 = (TextView) findViewById(R.id.textView23);
       // textView23.setVisibility(View.INVISIBLE);

        sensor=(TextView)findViewById(R.id.sensor);
       //sensor.setVisibility(View.INVISIBLE);
        //   spinner3=(Spinner)findViewById(R.id.spinner3);

        //Get MAC address from DeviceListActivity via intent
        Intent intent = getIntent();
        //Get the MAC address from the DeviceListActivty via EXTRA
        address = intent.getStringExtra(EXTRA_ADDRESS);
        //  address = "20:16:01:18:23:43";

        final List<String> hours = new ArrayList<String>();

        secondspicker = (NumberPicker) findViewById(R.id.secondspicker);


        secondspicker.setMinValue(0);
        secondspicker.setMaxValue(59);
        secondspicker.setEnabled(true);
        secondspicker.setWrapSelectorWheel(true);
        ///////////////////////////////////////////////////////////////////////////////////////////////


        tabHost2 = (TabHost) findViewById(R.id.tabHost2);

        tabHost2.setup();
        tabHost2 = getTabHost();

        //Lets add the first Tab
        TabHost.TabSpec mSpec = tabHost2.newTabSpec("Scan");
        mSpec.setContent(R.id.Scan);
        mSpec.setIndicator("Scan");
        tabHost2.addTab(mSpec);

        //Lets add the second Tab
        mSpec = tabHost2.newTabSpec("Data");
        mSpec.setContent(R.id.Datalog);
        mSpec.setIndicator("Data");
        tabHost2.addTab(mSpec);


        /////////////////////////////////////////////////////////////////////////////////////////////

        readbutton=(Button)findViewById(R.id.readbutton);
       datebutton=(Button)findViewById(R.id.datebutton);
        datetext=(TextView)findViewById(R.id.datetext);

        // add a click listener to the button
        datebutton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                showDialog(DATE_DIALOG_ID);
            }
        });

        // get the current date
        final Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);



        ///////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////////////

     ///////////////////////////////////////////////////////////////////////////////////////////////
        logger=(Button)findViewById(R.id.logger);
        logger.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v) {


                textView23.setText("D" +","+secondspicker.getValue()+"~");

                mConnectedThread.write(textView23.getText().toString());    // Send text via Bluetooth
               // Toast.makeText(getBaseContext(),textView23.getText().toString() + "Data send to device", Toast.LENGTH_SHORT).show();
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
                    //int endOfLineIndex = recDataString.indexOf("");                    // determine the end-of-line
                    //if (endOfLineIndex > 0)
                    //{                                           // make sure there data before ~
                        //String sensor0 = recDataString.substring(0, endOfLineIndex);    // extract string
                        textView23.setText(recDataString);    //update the textviews with sensor values
                        //int value=Integer.parseInt(sensor.getText().toString());

                       // sensor0 = " ";
                       // //value = 0;
                    ////}
                    //recDataString.delete(0, recDataString.length());                    //clear all string data
                    // strIncom =" ";
                }
            }
        };
        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();
    }

    private TabHost getTabHost()
    {
        return tabHost2;
    }

    //   Code for Send Receive
//////////////////////////////////////////////////////////////////////////////////////////////////////
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException
    {

        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    // updates the date in the TextView
    private void updateDisplay()
    {

        datetext.setText(getString(R.string.strSelectedDate,
                new StringBuilder()

                        // Month is 0 based so add 1
                        .append(day).append("/")
                        .append(month).append("/")
                        .append(year)));



    }

    // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener()
            {

                public void onDateSet(DatePicker view, int Year,
                                      int monthOfYear, int dayOfMonth)
                {
                    mYear = Year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    if(mDay < 10){
                        day = "0" + Integer.toString(mDay);
                    }
                    else{
                        day = Integer.toString(mDay);
                    }
                    if(mMonth < 10){
                        month = "0" + Integer.toString(mMonth);
                    }
                    else{
                        month = Integer.toString(mMonth);
                    }
                    year = Integer.toString(Year);
                    updateDisplay();
                }
            };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
                        mDay);
        }
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onResume()
    {
        super.onResume();


        //Get MAC address from DeviceListActivity via intent
        Intent intent = getIntent();
        //Get the MAC address from the DeviceListActivty via EXTRA
        address = intent.getStringExtra(EXTRA_ADDRESS);
        //  address = "20:16:01:18:23:43";

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
        mConnectedThread= new com.orion_instruments.www.pressuretransmitterver11.Datalogger.ConnectedThread(btSocket);
        mConnectedThread.start();

        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        readbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConnectedThread.write("F"+","+"DATA_" + day+ month+ year+".txt"+"~");
            }
        });

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
            } catch (IOException e)
            {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
            }
        }


//////////////////////////////////////////////////////////////////////////////////////////////////////

    }
}
