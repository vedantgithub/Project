package com.example.topnews;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.iid.FirebaseInstanceId;

public class GettingDeviceTokenService extends MyFirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {

        String DeviceToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("DEVICE TOKEN:",DeviceToken);
    }
}
