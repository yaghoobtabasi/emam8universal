package com.emam8.emam8_universal.services;


import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector {
    Context context;
    public ConnectionDetector(Context context) {
        this.context=context;
    }

    public boolean is_connected(){
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Service.CONNECTIVITY_SERVICE);
        if(connectivityManager!=null){
            NetworkInfo info=connectivityManager.getActiveNetworkInfo();
            if(info!=null){
                if(info.getState()==NetworkInfo.State.CONNECTED){
                    return true;
                }
            }

        }
        return false;
    }
}
