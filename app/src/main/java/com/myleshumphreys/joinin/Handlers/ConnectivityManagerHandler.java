package com.myleshumphreys.joinin.Handlers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityManagerHandler {

    public boolean hasInternetConnection(Context context) {
        ConnectivityManager connectivityManagerHandler = (android.net.ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo NetworkInfo = connectivityManagerHandler.getActiveNetworkInfo();
        if (NetworkInfo != null && NetworkInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
}