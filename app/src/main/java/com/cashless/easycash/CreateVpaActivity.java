package com.cashless.easycash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CreateVpaActivity extends AppCompatActivity {
    private TextView tVpa;
    static int i=0,k=0,t=0;
    String phn="8888877777",bankName="DCB",vpa="534534@ybl",accno="31241441414",name="Ronit",id,branch="Jayanagar",ifsc="ubi00000127",vpaPin="1234",appPin="2345";
Intent i1,i2;
    Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vpa);
        i1=new Intent(this,MainActivity.class);
        i2=new Intent(this,ChoosePhoneActivity.class);
        b1=(Button)findViewById(R.id.button2);
        b2=(Button)findViewById(R.id.newVPA);
        tVpa = (TextView) findViewById(R.id.vpa_field);
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        phn = sharedPreferences.getString("phone"+t, phn);
        String p= phn;
        tVpa.setText(p+"@ybl");
        phn=p;
        vpa=p+"@ybl";
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i1);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i2);
            }
        });
        save();
    }
    public void save() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("phone" + i, phn);
        editor.putString("bankName" + i, bankName);
        editor.putString("vpa" + i, vpa);
        editor.putString("accno" + i, accno);
        editor.putString("name" + i, name);
        editor.putString("branch" + i, branch);
        editor.putString("ifsc" + i, ifsc);
        editor.putString("vpapin" + i, vpaPin);
        editor.putString("apppin" + i, appPin);
        ++i;
        editor.commit();
    }
    public void load() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        phn = sharedPreferences.getString("phone"+k, phn);
        bankName = sharedPreferences.getString("bankName"+k, bankName);
        vpa = sharedPreferences.getString("vpa"+k, vpa);
        accno = sharedPreferences.getString("accno"+k,accno);
        name = sharedPreferences.getString("name"+k, name);
        branch = sharedPreferences.getString("branch"+k,branch);
        ifsc = sharedPreferences.getString("ifsc"+k, ifsc);
        vpaPin = sharedPreferences.getString("vpapin"+k,vpaPin);
        appPin = sharedPreferences.getString("apppin"+k,appPin);
        ++k;
    }
}
