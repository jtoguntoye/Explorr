package com.example.explorr;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.core.content.ContextCompat;

import java.util.Objects;

public class ConnectivityUtils {


    public boolean checkConnectivity(Context ctx) {


        ConnectivityManager connectivityMgr =(ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = Objects.requireNonNull(connectivityMgr).getActiveNetworkInfo();
        return  activeNetwork !=null && activeNetwork.isConnectedOrConnecting();

    }
}
