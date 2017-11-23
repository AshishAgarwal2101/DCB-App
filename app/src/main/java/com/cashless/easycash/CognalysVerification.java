package com.cashless.easycash;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CognalysVerification extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        String mobile = intent.getStringExtra("mobilenumber");
        String app_user_id = intent.getStringExtra("app_user_id");
        // Toast.makeText(context,"Verified Mobile : "+ mobile, Toast.LENGTH_SHORT).show();
        //Toast.makeText(context, "Verified App User ID : "+app_user_id, Toast.LENGTH_SHORT).show();
        Intent i= new Intent();
        i.setClassName("com.cashless.easycash", "com.cashless.easycash.ShowVerifiedActivity");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

}