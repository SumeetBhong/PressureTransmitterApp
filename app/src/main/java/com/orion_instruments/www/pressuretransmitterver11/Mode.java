package com.orion_instruments.www.pressuretransmitterver11;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.R.attr.id;
import static com.orion_instruments.www.pressuretransmitterver11.R.id.parent;

public class Mode extends AppCompatActivity {
    TextView sensor,txtArduino, txtString, txtStringLength;
   // EditText editText6,editText7,editText10,editText11,editText12,editText13,editText14,editText15;
   // TabHost tabHost;
    Button buttonUpdate;
    Handler bluetoothIn;
    final int handlerState = 0;


    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();
    private Mode.ConnectedThread mConnectedThread;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // String for MAC address
    private static String address;
    public Spinner spinner2,spinner,spinner7,spinner8,spinner9,spinner10;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
        setTitle("Mode");

        // Spinner element
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);


        txtArduino = (TextView) findViewById((R.id.textView14)); //text view for referance
        //Assign id to Tabhost.
       // TabHost tabHost = (TabHost)findViewById(R.id.tabHost);
       // tabHost.setup();

        //Lets add the first Tab
     //   TabHost.TabSpec mSpec = tabHost.newTabSpec("Sector 1");
     //   mSpec.setContent(R.id.sector1);
     //   mSpec.setIndicator("Sector 1");
     //   tabHost.addTab(mSpec);

        //Lets add the second Tab
     //   mSpec = tabHost.newTabSpec("Sector 2");
     //   mSpec.setContent(R.id.sector2);
     //   mSpec.setIndicator("Sector 2");
     //   tabHost.addTab(mSpec);

        //Lets add the third Tab
     //   mSpec = tabHost.newTabSpec("Sector 3");
     //   mSpec.setContent(R.id.sector3);
     //   mSpec.setIndicator("Sector 3");
     //   tabHost.addTab(mSpec);

        //Lets add the Fourth Tab
     //   mSpec = tabHost.newTabSpec("Sector 4");
     //   mSpec.setContent(R.id.sector4);
     //   mSpec.setIndicator("Sector 4");
     //   tabHost.addTab(mSpec);
/////////////////////////////////////////////////////////////////////////////////////////////////////

        buttonUpdate = (Button) findViewById(R.id.button); //button declaration



/////////////////////////////////////////////////////////////////////////////////////////////////////

        // Spinner click listener
        //spinner.setOnItemSelectedListener((OnItemSelectedListener) this);
        //spinner2.setOnItemSelectedListener((OnItemSelectedListener) this);
        final List<String> mode = new ArrayList<String>();  //list for modes
       // final List<String> unit = new ArrayList<String>();
       // List<String> colour= new ArrayList<String>();


        // Spinner Drop down elements
        mode.add("Pressure");
        mode.add("Force");
        mode.add("Level");
        mode.add("Differential Pressure");
        mode.add("Flow");
        mode.add("Temperature");



        //Spinner7,8,9,10 drop down elements
       // colour.add("Red");
       // colour.add("Green");
       // colour.add("Yellow");
       // colour.add("Orange");

////////////////////////////////////////////////////////////////////////////////////////////////////
        // Creating adapter for spinner
        final ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mode);
        //final ArrayAdapter<String> unitAdapter =new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, unit);
      //  final ArrayAdapter<String> colourAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colour);

        // Drop down layout style - list view with radio button
        modeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //colourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        // attaching data adapter to spinner
        spinner.setAdapter(modeAdapter);
        //spinner2.setAdapter(unitAdapter);

        //spinner7.setAdapter(colourAdapter);
       // spinner8.setAdapter(colourAdapter);
       // spinner9.setAdapter(colourAdapter);
       // spinner10.setAdapter(colourAdapter);
        //spinner2.setAdapter(unitAdapter);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public int u;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int unitposition, long id)
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

/////////////////////////////////////////////////////////////////////////////////////////////////////

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() //spinner initialisation
        {
            public int u;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                //txtArduino.setText("M" + position);
                // TODO Auto-generated method stub
                String sp1 = String.valueOf(spinner.getSelectedItem());
                //Toast.makeText(this, spinner, Toast.LENGTH_SHORT).show();
                if (sp1.contentEquals("Pressure"))
                {
                    List<String> unit = new ArrayList<String>();
                    //add dropdown items for pressure units
                    unit.add("Bar");
                    unit.add("Psi");
                    unit.add("Kg/m2");
                    unit.add("Mpa");

                    ArrayAdapter<String> unitAdapter =new ArrayAdapter<String>(Mode.this,android.R.layout.simple_spinner_item,unit);
                    unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    unitAdapter.notifyDataSetChanged();
                    spinner2.setAdapter(unitAdapter);
                }
                if (sp1.contentEquals("Force"))
                {
                    List<String> unit = new ArrayList<String>();
                    //add dropdown items for force units
                    unit.add("Newton");

                    ArrayAdapter<String> unitAdapter =new ArrayAdapter<String>(Mode.this, android.R.layout.simple_spinner_item, unit);
                    unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    unitAdapter.notifyDataSetChanged();
                    spinner2.setAdapter(unitAdapter);
                }
                if (sp1.contentEquals("Level"))
                {
                    List<String> unit = new ArrayList<String>();
                    //add dropdown items for level units
                    unit.add("mm");
                    unit.add("cm");

                    ArrayAdapter<String> unitAdapter =new ArrayAdapter<String>(Mode.this, android.R.layout.simple_spinner_item, unit);
                    unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    unitAdapter.notifyDataSetChanged();
                    spinner2.setAdapter(unitAdapter);
                }
                if (sp1.contentEquals("Differential Pressure"))
                {
                    List<String> unit = new ArrayList<String>();
                    //add dropdown items for Differential pressure units
                    unit.add("Bar");
                    unit.add("Psi");
                    unit.add("Kg/m2");
                    unit.add("Mpa");

                    ArrayAdapter<String> unitAdapter =new ArrayAdapter<String>(Mode.this, android.R.layout.simple_spinner_item, unit);
                    unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    unitAdapter.notifyDataSetChanged();
                    spinner2.setAdapter(unitAdapter);
                }
                if (sp1.contentEquals("Flow"))
                {
                    List<String> unit = new ArrayList<String>();
                    //add dropdown items for flow units
                    unit.add("Litres/sec");
                    unit.add("Litres/min");
                    unit.add("Litres/hr");



                    ArrayAdapter<String> unitAdapter =new ArrayAdapter<String>(Mode.this, android.R.layout.simple_spinner_item, unit);
                    unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    unitAdapter.notifyDataSetChanged();
                    spinner2.setAdapter(unitAdapter);
                }
                if (sp1.contentEquals("Temperature"))
                {
                    List<String> unit = new ArrayList<String>();
                    //add dropdown items for temperature units
                    unit.add("Degree Celcious");
                    unit.add("Farenhite");
                    unit.add("Kelvin");


                    ArrayAdapter<String> unitAdapter =new ArrayAdapter<String>(Mode.this, android.R.layout.simple_spinner_item, unit);
                    unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    unitAdapter.notifyDataSetChanged();
                    spinner2.setAdapter(unitAdapter);

                }
            }

            @Override
        public void onNothingSelected(AdapterView<?> parent)
        {

        }

        });


     ///////////////////////////////////////////////////////////////////////////////////////////////
        buttonUpdate.setOnClickListener(new View.OnClickListener() //update values through button
        {

            public void onClick(View v) {

                    //send value via text view
                txtArduino.setText("M" + String.valueOf(spinner.getSelectedItemPosition()) + String.valueOf(spinner2.getSelectedItemPosition())+"~");
                //
                 mConnectedThread.write(txtArduino.getText().toString());    // Send text via Bluetooth
                Toast.makeText(getBaseContext(),txtArduino.getText().toString() + "Data send to device", Toast.LENGTH_SHORT).show();
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
        mConnectedThread= new com.orion_instruments.www.pressuretransmitterver11.Mode.ConnectedThread(btSocket);
        mConnectedThread.start();

        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        mConnectedThread.write("x");
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
    ////////////////////////////////////////////////////////////////////////////////////////////////
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
    ////////////////////////////////////////////////////////////////////////////////////////////////

    }
}
