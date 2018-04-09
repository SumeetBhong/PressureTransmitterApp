package com.orion_instruments.www.pressuretransmitterver11;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class Gauge extends AppCompatActivity {
   public TabHost tabHost;
    TextView sensor,textView26,textView46,textView47,textView48,textView49,textView50,textView51,textView52,textView53,textView54,textView55,textView56,textView57, txtString, txtStringLength;
    Button button11,gaugecancel;
    String string;
    EditText editText20,editText21,editText22,editText23,editText24,editText25,editText26,editText27;
    public Spinner spinner6,spinner7,spinner8,spinner9;

    Handler bluetoothIn;
    final int handlerState = 0;

    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();
    private Gauge.ConnectedThread mConnectedThread;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // String for MAC address
    private static String address;
    // SPP UUID service - this should work for most devices
    public static String EXTRA_ADDRESS = "device_address";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gauge);
        setTitle("Gauge");

        // Font path
        String fontPath = "fonts/arial.ttf";
        textView26=(TextView)findViewById(R.id.textView26);
        textView26.setVisibility(View.INVISIBLE);
        textView46=(TextView)findViewById(R.id.textView46);
        textView47=(TextView)findViewById(R.id.textView47);
        textView48=(TextView)findViewById(R.id.textView48);
        textView49=(TextView)findViewById(R.id.textView49);
        textView50=(TextView)findViewById(R.id.textView50);
        textView51=(TextView)findViewById(R.id.textView51);
        textView52=(TextView)findViewById(R.id.textView52);
        textView53=(TextView)findViewById(R.id.textView53);
        textView54=(TextView)findViewById(R.id.textView54);
        textView55=(TextView)findViewById(R.id.textView55);
        textView56=(TextView)findViewById(R.id.textView56);
        textView57=(TextView)findViewById(R.id.textView57);
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        // Applying font
        textView46.setTypeface(tf);
        textView47.setTypeface(tf);
        textView48.setTypeface(tf);
        textView49.setTypeface(tf);
        textView50.setTypeface(tf);
        textView51.setTypeface(tf);
        textView52.setTypeface(tf);
        textView53.setTypeface(tf);
        textView54.setTypeface(tf);
        textView55.setTypeface(tf);
        textView56.setTypeface(tf);
        textView57.setTypeface(tf);

        editText20=(EditText) findViewById(R.id.editText20);
        editText20.getText().toString();

        editText21=(EditText) findViewById(R.id.editText21);
        editText21.getText().toString();

        editText22=(EditText) findViewById(R.id.editText22);
        editText22.getText().toString();

        editText23=(EditText) findViewById(R.id.editText23);
        editText23.getText().toString();

        editText24=(EditText) findViewById(R.id.editText24);
        editText24.getText().toString();

        editText25=(EditText) findViewById(R.id.editText25);
        editText25.getText().toString();

        editText26=(EditText) findViewById(R.id.editText26);
        editText26.getText().toString();

        editText27=(EditText) findViewById(R.id.editText27);
        editText27.getText().toString();

        // Spinner element

        spinner6=(Spinner) findViewById(R.id.spinner6);
        spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
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
        /////////////////////////////////////////////Spinner 7//////////////////////////////////////
        spinner7=(Spinner) findViewById(R.id.spinner7);
        spinner7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
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
        //////////////////////////////////////Spinner 8//////////////////////////////////////////////////////
        spinner8=(Spinner) findViewById(R.id.spinner8);
        spinner8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
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
        ////////////////////////////////////////Spinner 9///////////////////////////////////////////
        spinner9=(Spinner) findViewById(R.id.spinner9);
        spinner9.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
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
        ////////////////////////////////////////////////////////////////////////////////////////////

        final TabHost tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        //Lets add the first Tab
        TabHost.TabSpec mSpec = tabHost.newTabSpec("Sector 1");
        mSpec.setContent(R.id.sector1);
        mSpec.setIndicator("Sector 1");
        tabHost.addTab(mSpec);

        //Lets add the second Tab
        mSpec = tabHost.newTabSpec("Sector 2");
        mSpec.setContent(R.id.sector2);
        mSpec.setIndicator("Sector 2");
        tabHost.addTab(mSpec);

        //Lets add the third Tab
        mSpec = tabHost.newTabSpec("Sector 3");
        mSpec.setContent(R.id.sector3);
        mSpec.setIndicator("Sector 3");
        tabHost.addTab(mSpec);

        //Lets add the Fourth Tab
        mSpec = tabHost.newTabSpec("Sector 4");
        mSpec.setContent(R.id.sector4);
        mSpec.setIndicator("Sector 4");
        tabHost.addTab(mSpec);



        button11= (Button) findViewById(R.id.button11);

        List<String> color= new ArrayList<String>();

        //Spinner6,7,8,9 drop down elements
        color.add("Green");
        color.add("Orange");
        color.add("Yellow");
        color.add("Red");

        final ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, color);

        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner7.setAdapter(colorAdapter);
        spinner7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public int u;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int colorposition, long id)
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
        ////////////////////////////////////Spinner 8///////////////////////////////////////////////
        spinner8.setAdapter(colorAdapter);
        spinner8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public int u;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int colorposition, long id)
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
        /////////////////////////////Spinner 9///////////////////////////////////////////////////////
        spinner9.setAdapter(colorAdapter);
        spinner9.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public int u;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int colorposition, long id)
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
        /////////////////////////////////Spinner 6//////////////////////////////////////////////////
        spinner6.setAdapter(colorAdapter);
        spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public int u;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int colorposition, long id)
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
        ////////////////////////////////////////////////////////////////////////////////////////////
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener()
        {
            @Override
            public void onTabChanged(String arg0)
            {

            //    Toast.makeText(getBaseContext(), "Im currently in tab with index::" + tabHost.getCurrentTab(), Toast.LENGTH_SHORT).show();


            }
        });

     //////////////////////////////////////////////////////////////////////////////////////////////
        button11.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v)
            {
                if(tabHost.getCurrentTab() == 0)
                {
                    textView26.setText("G1"+","+editText22.getText().toString()+","+editText20.getText().toString()+","+String.valueOf(spinner6.getSelectedItemPosition())+"~");
                }

                if(tabHost.getCurrentTab() == 1)
                {
                    textView26.setText("G2"+","+editText23.getText().toString()+","+editText21.getText().toString()+","+String.valueOf(spinner7.getSelectedItemPosition())+"~");
                }

                if(tabHost.getCurrentTab() == 2)
                {
                    textView26.setText("G3"+","+editText25.getText().toString()+","+editText24.getText().toString()+","+String.valueOf(spinner8.getSelectedItemPosition())+"~");
                }

                if(tabHost.getCurrentTab() == 3)
                {
                    textView26.setText("G4"+","+editText27.getText().toString()+","+editText26.getText().toString()+","+ String.valueOf(spinner9.getSelectedItemPosition())+"~");

                }

     ///////////////////////////////////////////////////////////////////////////////////////////////
              //  gaugecancel=(Button)findViewById(R.id.gaugecancel);
              /*  gaugecancel.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Gauge.this, Settings.class);
                        startActivity(intent);
                        // startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
                    }
                }); */

////////////////////////////////////////////////////////////////////////////////////////////////////
                //
                mConnectedThread.write(textView26.getText().toString());    // Send text via Bluetooth
                //Toast.makeText(getBaseContext(),textView26.getText().toString() + "Data send to device", Toast.LENGTH_SHORT).show();
                Toast.makeText(getBaseContext(), "Data send to device", Toast.LENGTH_LONG).show();
            }
        });

        /////////////////////////////////////code for Rx from arduino//////////////////////////////////////////////////////////

        bluetoothIn = new Handler()
        {

            public void handleMessage(android.os.Message msg)
            {
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
    // /////////////////////////////////////////////////////////////////////////////////////////////
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException
    {

        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
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
        mConnectedThread= new com.orion_instruments.www.pressuretransmitterver11.Gauge.ConnectedThread(btSocket);
        mConnectedThread.start();

        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        mConnectedThread.write("");
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////onPause Started/////////////////////////////////////////////////////

    @Override
    public void onPause()
    {
        super.onPause();
        try
        {
            //Don't leave Bluetooth sockets open when leaving activity
            btSocket.close();
        } catch (IOException e2)
        {
            //insert code to deal with this
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
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
            } catch (IOException e)
            {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }
        ////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////Run method starts/////////////////////////////////////////

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
     ///////////////////////////////////////////////////////////////////////////////////////////////

        //write method
        public void write(String input)
        {
            byte[] msgBuffer = input.getBytes();    //converts entered String into bytes
            try
            {
                mmOutStream.write(msgBuffer);      //write bytes over BT connection via outstream
            }
            catch (IOException e)
            {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
            }
        }
     ///////////////////////////////////////////////////////////////////////////////////////////////
     ///////////////////////////////////////////////////////////////////////////////////////////////
    }
}

