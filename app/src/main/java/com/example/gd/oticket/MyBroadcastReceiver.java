package com.example.gd.oticket;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by GD on 4/1/2018.
 */

public class MyBroadcastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent != null){
            Bundle bundle = intent.getExtras();

            NotiMsg notiMsg = (NotiMsg) bundle.getSerializable("notiMsg");

            if(notiMsg != null){

//                new MainActivity().showNotiDialog(notiMsg);
            }
        }
    }
}
