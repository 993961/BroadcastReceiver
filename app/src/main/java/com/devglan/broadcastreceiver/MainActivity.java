package com.devglan.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private boolean isConnectionAvailable;
    public static final String NETWORK_SWITCH_FILTER = "com.devglan.broadcastreceiver.NETWORK_SWITCH_FILTER";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    BroadcastReceiver netSwitchReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            isConnectionAvailable =  intent.getExtras().getBoolean("is_connected");
            if (!isConnectionAvailable) {
                if (NetworkErrorActivity.isOptedToOffline()) {

                } else {
                    Intent netWorkIntent = new Intent(MainActivity.this, NetworkErrorActivity.class);
                    startActivity(netWorkIntent);
                }
            } else {
                NetworkErrorActivity.setOptedToOffline(false);
            }
        }
    };



    @Override
    protected void onResume() {
        super.onResume();
        try {
            registerReceiver(netSwitchReceiver,new IntentFilter(NETWORK_SWITCH_FILTER));
        }
        catch (Exception e){

        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(netSwitchReceiver);
        }
        catch (Exception e){

        }
    }

}
