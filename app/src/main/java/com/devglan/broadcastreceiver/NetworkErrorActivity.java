package com.devglan.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class NetworkErrorActivity extends AppCompatActivity {
    private static boolean optedToOffline = false;

    public static final String NETWORK_SWITCH_FILTER = "com.devglan.broadcastreceiver.NETWORK_SWITCH_FILTER";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.network_error);

    }

    public static boolean isOptedToOffline() {
        return optedToOffline;
    }
    public static void setOptedToOffline(boolean optedToOffline){
        NetworkErrorActivity.optedToOffline = optedToOffline;
    }

    BroadcastReceiver netSwitchReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isConnectionAvailable =  intent.getExtras().getBoolean("is_connected");
            if (isConnectionAvailable) {
                optedToOffline = false;

                finish();
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
