package com.cashless.easycash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.matesnetwork.Cognalys.VerifyMobile;
import java.util.ArrayList;

public class PhoneVerification extends AppCompatActivity {

    String sim,s1="Jio 4G",s2="Airtel",num1="+917355785212",num2="+918971435313",num="";
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);
        i= getIntent();
        num= i.getStringExtra("number");
        //Toast.makeText(this,sim,Toast.LENGTH_SHORT).show();

      /*  if(sim.equals(s1))
        {
            num=num1;
        }
        else if(sim.equals(s2))
        {
            num=num2;
        }*/

        Intent in = new Intent(PhoneVerification.this, VerifyMobile.class);

        in.putExtra("app_id", "02df9a5c3ba445afa9a94e7");
        in.putExtra("access_token",
                "4e3b2977fb1cf940be46552255c323788c57d60b");
        in.putExtra("mobile", num);

        if (CheckNetworkConnection.isConnectionAvailable(getApplicationContext())) {
            startActivityForResult(in, VerifyMobile.REQUEST_CODE);
        } else {
            Toast.makeText(getApplicationContext(), "no internet connection", Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        // TODO Auto-generated method stub
        super.onActivityResult(arg0, arg1, arg2);

        if (arg0 == VerifyMobile.REQUEST_CODE) {
            String message = arg2.getStringExtra("message");
            int result = arg2.getIntExtra("result", 0);

            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(), "" + result,Toast.LENGTH_SHORT).show();

            Intent in= new Intent(PhoneVerification.this,ShowVerifiedActivity.class);
            in.putExtra("phone",num);
            startActivity(in);
        }
    }

}