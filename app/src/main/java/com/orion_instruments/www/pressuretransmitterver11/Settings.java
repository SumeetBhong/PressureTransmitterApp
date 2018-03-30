package com.orion_instruments.www.pressuretransmitterver11;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.UUID;

public class Settings extends AppCompatActivity {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////BLUETOOTH PARAMETERS
    ////////////////////////////////////////////////////////////////////////////////////////////////
    final int handlerState = 0;
    Handler bluetoothIn;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    // public BluetoothAdapter myBluetooth = null;

    private StringBuilder recDataString = new StringBuilder();
  //   private Settings.ConnectedThread mConnectedThread;
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // SPP UUID service - this should work for most devices
    public static String EXTRA_ADDRESS = "device_address";


    // Button buttonUpdate;
    ProgressDialog progressDialog;
    TextView sensor, testText;
    private static String address;
    GridView gridView1;

    String[] gridViewString = {
            "Mode", "Switch", "Sector", "Transmitter", "Device calibration","Calibration","Data logger","WiFi","About", "Device settings"
    };
    int[] gridViewImageId = {
            R.drawable.modelogo, R.drawable.switchlogo,
            R.drawable.guagelogo, R.drawable.transmission,
            R.drawable.calibration, R.drawable.calibrationlogo,
            R.drawable.logger, R.drawable.wifilogo,
            R.drawable.aboutlogo, R.drawable.editdevicelogo

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings");
        CustomGridViewActivity customGridViewActivity = new CustomGridViewActivity(Settings.this, gridViewString, gridViewImageId);


        testText = (TextView) findViewById(R.id.textView30);
        gridView1 = (GridView) findViewById(R.id.gridView);
        gridView1.setAdapter(customGridViewActivity);
        testText.setText(address);
        testText.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        //Get the MAC address from the DeviceListActivty via EXTRA
        address = intent.getStringExtra(EXTRA_ADDRESS);


        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    progressDialog = new ProgressDialog(Settings.this);
                    progressDialog.setMessage("Loading..."); // Setting Message
                    // progressDialog.setTitle("ProgressDialog"); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    // Set the progress dialog background color
                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));
                    progressDialog.show(); // Display Progress Dialog
                    progressDialog.setCancelable(false);
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(100);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                        }
                    }).start();

                    Intent intent = new Intent(Settings.this, Mode.class);
                    intent.putExtra(EXTRA_ADDRESS, address);
                    //  Intent  = new Intent(Settings.this, Mode.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(intent);
                    overridePendingTransition(0, 0);

                }
                if (i == 1) {
                    progressDialog = new ProgressDialog(Settings.this);
                    progressDialog.setMessage("Loading..."); // Setting Message
                    // progressDialog.setTitle("ProgressDialog"); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    // Set the progress dialog background color
                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));
                    progressDialog.show(); // Display Progress Dialog
                    progressDialog.setCancelable(false);
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(100);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                        }
                    }).start();

                    Intent intent = new Intent(Settings.this, Relay.class);
                    intent.putExtra(EXTRA_ADDRESS, address);
                    startActivity(intent);

                }
                if (i == 2) {
                    progressDialog = new ProgressDialog(Settings.this);
                    progressDialog.setMessage("Loading..."); // Setting Message
                    // progressDialog.setTitle("ProgressDialog"); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    // Set the progress dialog background color
                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));
                    progressDialog.show(); // Display Progress Dialog
                    progressDialog.setCancelable(false);
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(100);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                        }
                    }).start();

                    Intent intent = new Intent(Settings.this, Gauge.class);
                    intent.putExtra(EXTRA_ADDRESS,address);
                    startActivity(intent);

                }

                if (i == 3) {
                    progressDialog = new ProgressDialog(Settings.this);
                    progressDialog.setMessage("Loading..."); // Setting Message
                    // progressDialog.setTitle("ProgressDialog"); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    // Set the progress dialog background color
                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));
                    progressDialog.show(); // Display Progress Dialog
                    progressDialog.setCancelable(false);
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(100);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                        }
                    }).start();

                    Intent intent = new Intent(Settings.this, Transmitter.class);
                    intent.putExtra(EXTRA_ADDRESS,address);
                    startActivity(intent);
                }
                if (i == 4) {
                    progressDialog = new ProgressDialog(Settings.this);
                    progressDialog.setMessage("Loading..."); // Setting Message
                    // progressDialog.setTitle("ProgressDialog"); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    // Set the progress dialog background color
                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));
                    progressDialog.show(); // Display Progress Dialog
                    progressDialog.setCancelable(false);
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(100);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                        }
                    }).start();

                    Intent intent = new Intent(Settings.this, devicecalibration.class);
                    intent.putExtra(EXTRA_ADDRESS,address);
                    startActivity(intent);
                }

                if (i == 5) {
                    progressDialog = new ProgressDialog(Settings.this);
                    progressDialog.setMessage("Loading..."); // Setting Message
                    // progressDialog.setTitle("ProgressDialog"); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    // Set the progress dialog background color
                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));
                    progressDialog.show(); // Display Progress Dialog
                    progressDialog.setCancelable(false);
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(100);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                        }
                    }).start();

                    Intent intent = new Intent(Settings.this, Calibration.class);
                    intent.putExtra(EXTRA_ADDRESS,address);
                    startActivity(intent);
                }
                if (i == 6) {
                    progressDialog = new ProgressDialog(Settings.this);
                    progressDialog.setMessage("Loading..."); // Setting Message
                    // progressDialog.setTitle("ProgressDialog"); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    // Set the progress dialog background color
                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));
                    progressDialog.show(); // Display Progress Dialog
                    progressDialog.setCancelable(false);
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(100);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                        }
                    }).start();

                    Intent intent = new Intent(Settings.this, Datalogger.class);
                    intent.putExtra(EXTRA_ADDRESS,address);
                    startActivity(intent);
                }
                if (i == 7) {
                    progressDialog = new ProgressDialog(Settings.this);
                    progressDialog.setMessage("Loading..."); // Setting Message
                    // progressDialog.setTitle("ProgressDialog"); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    // Set the progress dialog background color
                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));
                    progressDialog.show(); // Display Progress Dialog
                    progressDialog.setCancelable(false);
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(100);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                        }
                    }).start();

                    Intent intent = new Intent(Settings.this, WiFI.class);
                    intent.putExtra(EXTRA_ADDRESS,address);
                    startActivity(intent);
                }
                if (i == 8) {
                    progressDialog = new ProgressDialog(Settings.this);
                    progressDialog.setMessage("Loading..."); // Setting Message
                    // progressDialog.setTitle("ProgressDialog"); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    // Set the progress dialog background color
                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));
                    progressDialog.show(); // Display Progress Dialog
                    progressDialog.setCancelable(false);
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(100);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                        }
                    }).start();

                    Intent intent = new Intent(Settings.this, About.class);
                    intent.putExtra(EXTRA_ADDRESS,address);
                    startActivity(intent);
                }
                if (i == 9) {
                    progressDialog = new ProgressDialog(Settings.this);
                    progressDialog.setMessage("Loading..."); // Setting Message
                    // progressDialog.setTitle("ProgressDialog"); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    // Set the progress dialog background color
                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));
                    progressDialog.show(); // Display Progress Dialog
                    progressDialog.setCancelable(false);
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(100);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                        }
                    }).start();

                    Intent intent = new Intent(Settings.this,DeviceSettings.class);
                    intent.putExtra(EXTRA_ADDRESS,address);
                    startActivity(intent);
                }
                // Toast.makeText(MainActivity.this,"Selected: "+gridViewString[+i], Toast.LENGTH_LONG).show();
            }
        });


/////////////////////////////////////////////////////////////////////////////////////////////////////
        //   Code for Send Receive
//////////////////////////////////////////////////////////////////////////////////////////////////////
   /*  @Override
    public void onResume()
    {
        super.onResume();


        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called

    }

    @Override
    public void onPause()
    {
        super.onPause();

    }*/
////////////////////////////////////////////////////////////////////////////////////////////////////
    }
}



