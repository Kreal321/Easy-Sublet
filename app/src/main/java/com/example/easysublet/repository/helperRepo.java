package com.example.easysublet.repository;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class helperRepo {

    //NOTE: to check network status and return true if connected,
    // else return false and prompt if no connected
    public static boolean isConnected(Application application){
        ConnectivityManager connectivityManager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =connectivityManager.getActiveNetworkInfo();
        if(networkInfo!= null && networkInfo.isConnected()){
            return true;
        }else{
            Toast.makeText(application, "No network, Please Check Network Connection", Toast.LENGTH_LONG).show();
            return false;
        }
    }


}
