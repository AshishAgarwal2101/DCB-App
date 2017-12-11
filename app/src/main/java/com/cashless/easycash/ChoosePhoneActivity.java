
package com.cashless.easycash;

import android.*;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cashless.easycash.Helpers.SPHelper;

import java.security.Permission;

/**
 * Created by Ashish on 11/4/2017.
 */

public class
ChoosePhoneActivity  extends AppCompatActivity{

    TextView t1,t2;
    RadioButton r1;
    RadioGroup g;
    EditText e;
    String s1="Jio 4G";
    Button b;
    Intent i;
    Boolean permissionsGranted=false;
    String phn="8888877777",bankName="DCB",vpa="534534@ybl",accno="31241441414",name="Varsha",
            id,branch="Jayanagar",ifsc="ubi00000127",vpaPin="1234",appPin="2345",number = null;
    int k=0;
    Intent newNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_phone_layout);
        callLogPermissionDialog();
        newNum= new Intent();
        newNum= getIntent();
        load();
        if(newNum.hasExtra("new"))
        {

        }
        else {
            if(!(vpa.equals("534534@ybl"))) {
                Intent intent = new Intent(ChoosePhoneActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }

        t1= (TextView) findViewById(R.id.HelloText);
        t2= (TextView) findViewById(R.id.enterNumberText);
        e= (EditText)findViewById(R.id.enterNumber);
        b= (Button) findViewById(R.id.button);
        i= new Intent(ChoosePhoneActivity.this, PhoneVerification.class);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number= e.getText().toString();
                if(!number.equals("") && permissionsGranted) {
                    if(!((number.substring(0,3)).equals("+91")))
                        number = "+91" + number ;
                    if(number.length() != 13)
                        Toast.makeText(getApplicationContext(),"Enter the correct number",Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(getApplicationContext(), number, Toast.LENGTH_SHORT).show();
                        i.putExtra("number", number);
                        startActivity(i);
                    }
                }
                else if(!permissionsGranted)
                {
                    Toast.makeText(getApplicationContext(),"Provide permissions to continue!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Enter the number",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void load() {
        phn = SPHelper.getSP(getApplicationContext(),"phone"+k, phn);
        bankName = SPHelper.getSP(getApplicationContext(),"bankName"+k, bankName);
        vpa = SPHelper.getSP(getApplicationContext(),"vpa"+k, vpa);
        accno = SPHelper.getSP(getApplicationContext(),"accno"+k,accno);
        name = SPHelper.getSP(getApplicationContext(),"name"+k, name);
        branch = SPHelper.getSP(getApplicationContext(),"branch"+k,branch);
        ifsc = SPHelper.getSP(getApplicationContext(),"ifsc"+k, ifsc);
        vpaPin = SPHelper.getSP(getApplicationContext(),"vpapin"+k,vpaPin);
        appPin = SPHelper.getSP(getApplicationContext(),"apppin"+k,appPin);
    }

    void callLogPermissionDialog()
    {
        if(Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.READ_CALL_LOG)
                    != PackageManager.PERMISSION_GRANTED) {


                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_CALL_LOG)) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Call Log access needed");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("please confirm Call Log access");//TODO put real question
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {Manifest.permission.READ_CALL_LOG}
                                    , 0);
                        }
                    });
                    builder.show();

                } else {

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_CALL_LOG},
                            0);
                }
            }
            else//permission granted
            {
                locationPermissionDialog();
            }
        }
    }
    void locationPermissionDialog()
    {
        if(Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {


                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Location access needed");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("Please confirm Location access");//TODO put real question
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {Manifest.permission.ACCESS_FINE_LOCATION}
                                    , 1);
                        }
                    });
                    builder.show();

                } else {

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            1);
                }
            }
            else
            {
                contactsPermissionDialog();
            }
        }
    }
    void contactsPermissionDialog()
    {

        if(Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {


                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_CONTACTS)) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Location access needed");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("Please confirm Location access");//TODO put real question
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {Manifest.permission.READ_CONTACTS}
                                    , 2);
                        }
                    });
                    builder.show();

                } else {

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            2);
                }
            }
            else//permission granted
            {
               permissionsGranted = true;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode==0) {
            if ((grantResults[0] == PackageManager.PERMISSION_GRANTED))//call logs
            {
                locationPermissionDialog();
            } else {
                Toast.makeText(getApplicationContext(), "Call logs permission needs to be granted to proceed!", Toast.LENGTH_SHORT).show();
            }
        }
           else if(requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)//location
            {
                contactsPermissionDialog();
            } else {
                Toast.makeText(getApplicationContext(), "Contacts permission needs to be granted to proceed!", Toast.LENGTH_SHORT).show();
            }
        }
            else if(requestCode == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)//location
            {
                permissionsGranted = true;
            } else {
                Toast.makeText(getApplicationContext(), "Location permission needs to be granted to proceed!", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
