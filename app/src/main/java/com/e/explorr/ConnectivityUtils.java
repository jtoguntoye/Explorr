package com.e.explorr;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Objects;

public class ConnectivityUtils {


    public boolean checkConnectivity(Context ctx) {


        ConnectivityManager connectivityMgr =(ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = Objects.requireNonNull(connectivityMgr).getActiveNetworkInfo();
        return  activeNetwork !=null && activeNetwork.isConnectedOrConnecting();

    }
}
