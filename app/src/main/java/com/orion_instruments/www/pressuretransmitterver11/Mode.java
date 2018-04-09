package com.orion_instruments.www.pressuretransmitterver11;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
<<<<<<< HEAD
import android.widget.NumberPicker;
=======
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

<<<<<<< HEAD
import static com.orion_instruments.www.pressuretransmitterver11.MyCustomPagerAdapter.positionImage;


public class Mode extends AppCompatActivity {

=======

<<<<<<< HEAD

public class Mode extends AppCompatActivity {
=======
public class Mode extends AppCompatActivity {
    TextView sensor,txtArduino, txtString, txtStringLength;
   // EditText editText6,editText7,editText10,editText11,editText12,editText13,editText14,editText15;
   // TabHost tabHost;
    Button buttonUpdate;
    Handler bluetoothIn;
    final int handlerState = 0;
 final String sdf = "Hello World!!";
>>>>>>> e6f43371b663c95fa1cbb4c8dc075a77fb33660d

>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92
    // String for MAC address
   // private static String address;
    ////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////BLUETOOTH PARAMETERS
    ////////////////////////////////////////////////////////////////////////////////////////////////
    final int handlerState = 0;
    Handler bluetoothIn;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;

   // public BluetoothAdapter myBluetooth = null;

    private StringBuilder recDataString = new StringBuilder();
    private Mode.ConnectedThread mConnectedThread;
    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // String for MAC address
    private static String address;
    // SPP UUID service - this should work for most devices
    public static String EXTRA_ADDRESS = "device_address";

    //////////////////////////////WIDGETS DECLARATION///////////////////////////////////////////////

    public Spinner spinner2, spinner;
    TextView sensor, textView14, txtString, txtStringLength;
    Button button,modecancel;
<<<<<<< HEAD
    NumberPicker highrangepicker,lowrangepicker;
    int position;

    //////////////////////////////////OnCreate//////////////////////////////////////////////////////
    @SuppressLint("HandlerLeak")
=======
    int position;

    //////////////////////////////////OnCreate//////////////////////////////////////////////////////
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
        setTitle("Mode");


        textView14 = (TextView) findViewById(R.id.textView14);
        textView14.setVisibility(View.INVISIBLE);
        button = (Button) findViewById(R.id.button);
     //   modecancel=(Button)findViewById(R.id.modecancel);
<<<<<<< HEAD
     ///////////////////////////////////////////////////////////////////////////////////////////////
        highrangepicker= (NumberPicker)findViewById(R.id.highrangepicker);
        lowrangepicker= (NumberPicker)findViewById(R.id.lowrangepicker);

        highrangepicker.setMinValue(0);
        highrangepicker.setMaxValue(600);
        highrangepicker.setEnabled(true);
        highrangepicker.setWrapSelectorWheel(true);

        lowrangepicker.setMinValue(0);
        lowrangepicker.setMaxValue(600);
        lowrangepicker.setEnabled(true);
        lowrangepicker.setWrapSelectorWheel(true);


    ////////////////////////////////////////////////////////////////////////////////////////////////
     /*   modecancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mode.this, Settings.class);
                startActivity(intent);
                // startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
            }
        }); */
     //////////////////////////////////////////////////////////////////////////////////////////////////

        //Get MAC address from DeviceListActivity via intent
        Intent intent = getIntent();
        //Get the MAC address from the DeviceListActivty via EXTRA
        address = intent.getStringExtra(EXTRA_ADDRESS);
        //address="20:16:01:18:23:43";
        textView14.setText(address);

=======

    ////////////////////////////////////////////////////////////////////////////////////////////////
     /*   modecancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mode.this, Settings.class);
                startActivity(intent);
                // startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
            }
        }); */
     //////////////////////////////////////////////////////////////////////////////////////////////////

        //Get MAC address from DeviceListActivity via intent
        Intent intent = getIntent();
        //Get the MAC address from the DeviceListActivty via EXTRA
        address = intent.getStringExtra(EXTRA_ADDRESS);
        //address="20:16:01:18:23:43";
        textView14.setText(address);



////////////////////////////////////////////////////////////////////////////////////////////////////
        final ViewPager viewPager;
        final int images[] = {R.drawable.screen2mode,R.drawable.screen2, R.drawable.screen1mode};
        final MyCustomPagerAdapter myCustomPagerAdapter;

        viewPager = (ViewPager)findViewById(R.id.viewPager);
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92

        myCustomPagerAdapter = new MyCustomPagerAdapter(Mode.this, images);
        viewPager.setAdapter(myCustomPagerAdapter);

////////////////////////////////////////////////////////////////////////////////////////////////////
        final ViewPager viewPager;
        final int images[] = {R.drawable.screen2mode,R.drawable.screen2, R.drawable.screen1mode};
        final MyCustomPagerAdapter myCustomPagerAdapter;

        viewPager = (ViewPager)findViewById(R.id.viewPager);

        myCustomPagerAdapter = new MyCustomPagerAdapter(Mode.this, images);
        viewPager.setAdapter(myCustomPagerAdapter);

<<<<<<< HEAD
=======
        Intent intent2 = getIntent();
       position = intent2.getExtras().getInt("id");
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92


        ///////////////////////////////////////////////////////////////////////////////////////////////

<<<<<<< HEAD
        ///////////////////////////////////////////////////////////////////////////////////////////////

=======
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92
        //////////////////////////////////SPINNER DECLARATION//////////////////////////////////////////
        // Spinner element
        spinner = (Spinner) findViewById(R.id.spinner);
        final List<String> mode = new ArrayList<String>();  //list for modes
        // final List<String> unit = new ArrayList<String>();
        // Spinner Drop down elements
        mode.add("Pressure");
        mode.add("Force");
        mode.add("Level");
        mode.add("Differential Pressure");
        mode.add("Flow");
        mode.add("Temperature");
        spinner2 = (Spinner) findViewById(R.id.spinner2);

        // Creating adapter for spinner
        final ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mode);// Drop down layout style - list view with radio button
        modeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(modeAdapter);
        ////////////////////////////////////////////SPINNER 2///////////////////////////////////////

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });/////////////////////////////END OF SPINNER SELECTION////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() //spinner initialisation
        {
            ///////////////////////////////////SELECTION OF SPINNER ITEMS///////////////////////////
            ////////////////////////////////////////////////////////////////////////////////////////
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                String sp1 = String.valueOf(spinner.getSelectedItem());
                //Toast.makeText(this, spinner, Toast.LENGTH_SHORT).show();
                if (sp1.contentEquals("Pressure")) {
                    List<String> unit = new ArrayList<String>();
                    //add dropdown items for pressure units
                    unit.add("Bar");
                    unit.add("Psi");
                    unit.add("Kg/m2");
                    unit.add("Mpa");

                    ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(Mode.this, android.R.layout.simple_spinner_item, unit);
                    unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    unitAdapter.notifyDataSetChanged();
                    spinner2.setAdapter(unitAdapter);
                }
                if (sp1.contentEquals("Force")) {
                    List<String> unit = new ArrayList<String>();
                    //add dropdown items for force units
                    unit.add("Newton");

                    ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(Mode.this, android.R.layout.simple_spinner_item, unit);
                    unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    unitAdapter.notifyDataSetChanged();
                    spinner2.setAdapter(unitAdapter);
                }
                if (sp1.contentEquals("Level")) {
                    List<String> unit = new ArrayList<String>();
                    //add dropdown items for level units
                    unit.add("mm");
                    unit.add("cm");

                    ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(Mode.this, android.R.layout.simple_spinner_item, unit);
                    unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    unitAdapter.notifyDataSetChanged();
                    spinner2.setAdapter(unitAdapter);
                }
                if (sp1.contentEquals("Differential Pressure")) {
                    List<String> unit = new ArrayList<String>();
                    //add dropdown items for Differential pressure units
                    unit.add("Bar");
                    unit.add("Psi");
                    unit.add("Kg/cm2");
                    unit.add("Mpa");

                    ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(Mode.this, android.R.layout.simple_spinner_item, unit);
                    unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    unitAdapter.notifyDataSetChanged();
                    spinner2.setAdapter(unitAdapter);
                }
                if (sp1.contentEquals("Flow")) {
                    List<String> unit = new ArrayList<String>();
                    //add dropdown items for flow units
                    unit.add("Litres/sec");
                    unit.add("Litres/min");
                    unit.add("Litres/hr");


                    ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(Mode.this, android.R.layout.simple_spinner_item, unit);
                    unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    unitAdapter.notifyDataSetChanged();
                    spinner2.setAdapter(unitAdapter);
                }
                if (sp1.contentEquals("Temperature")) {
                    List<String> unit = new ArrayList<String>();
                    //add dropdown items for temperature units
                    unit.add("Degree Celcious");
                    unit.add("Farenhite");
                    unit.add("Kelvin");


                    ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(Mode.this, android.R.layout.simple_spinner_item, unit);
                    unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    unitAdapter.notifyDataSetChanged();
                    spinner2.setAdapter(unitAdapter);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
     ///////////////////////////////////////////////////////////////////////////////////////////////
     ///////////////////////////////////////////////////////////////////////////////////////////////


////////////////// Code for rx of data from arduino  ///////////////////////////////////////////////
       bluetoothIn = new Handler()
        {

            public void handleMessage(android.os.Message msg)
            {

            }


        };
        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();
///////////////////////////// Set up onClick listeners for buttons to send data to Bluetooth
        button.setOnClickListener(new View.OnClickListener()
        {
            @SuppressLint("SetTextI18n")
            public void onClick(View v)
            {
<<<<<<< HEAD
               positionImage = viewPager.getCurrentItem();
                //send value via text view
                textView14.setText("M"+"," + String.valueOf(spinner.getSelectedItemPosition())+"," +
                        String.valueOf(spinner2.getSelectedItemPosition())+","+positionImage
                        +highrangepicker.getValue()+","+lowrangepicker.getValue()+"~");
=======

                //send value via text view
                textView14.setText("M"+"," + String.valueOf(spinner.getSelectedItemPosition())+"," + String.valueOf(spinner2.getSelectedItemPosition())+","+ position   +"~");
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92

                mConnectedThread.write(textView14.getText().toString());    // Send text via Bluetooth
                //Toast.makeText(getBaseContext(), textView14.getText().toString() + "Data send to device", Toast.LENGTH_SHORT).show();
                Toast.makeText(getBaseContext(), "Data send to device", Toast.LENGTH_LONG).show();
            }
        });

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
///   Code for Send Receive
///////////////////////////////BLUETOOTH SOCKET/////////////////////////////////////////////////////
   private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException
    {

       return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }
    /////////////////////////////////////ON RESUME//////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onResume()
    {
        super.onResume();

        //Get MAC address from DeviceListActivity via intent
      //  Intent intent = getIntent();
        //Get the MAC address from the DeviceListActivty via EXTRA
     //   address = intent.getStringExtra(EXTRA_ADDRESS);
        //address="20:16:01:18:23:43";
        //create device and set the MAC address
        BluetoothDevice device = btAdapter.getRemoteDevice(address);


      try
        {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e)
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

            }
        }
       mConnectedThread = new Mode.ConnectedThread(btSocket);
        mConnectedThread.start();


        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        mConnectedThread.write("");

    }///////////////////////////////ON RESUME ENDED/////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////ON PAUSE STARTED///////////////////////////////////////////////////
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
     // / // ////////////////////////////ON PAUSED ENDED///////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////CHECK BT STATE////////////////////////////////////////////////////////////
    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
    private void checkBTState() {

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
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////CONNECT THREAD//////////////////////////////////////////////////////////
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
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////RUN PROCESS START///////////////////////////////////////////////

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
        }////////////////////////////RUN PROCESS ENDED//////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////WRITE METHOD STARTS///////////////////////////////////////////////
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

