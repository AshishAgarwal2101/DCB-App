
package com.cashless.easycash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cashless.easycash.Helpers.SPHelper;

/**
 * Created by Ashish on 11/4/2017.
 */

public class ChoosePhoneActivity  extends AppCompatActivity{

    TextView t1,t2;
    RadioButton r1;
    RadioGroup g;
    EditText e;
    String s1="Jio 4G";
    Button b;
    Intent i;
    String phn="8888877777",bankName="DCB",vpa="534534@ybl",accno="31241441414",name="Varsha",
            id,branch="Jayanagar",ifsc="ubi00000127",vpaPin="1234",appPin="2345";
    int k=0;
    Intent newNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_phone_layout);
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
                String number= e.getText().toString();
                i.putExtra("number",number);
                startActivity(i);
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
}
