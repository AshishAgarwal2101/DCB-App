package com.cashless.easycash;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cashless.easycash.Helpers.SPHelper;

public class CreateVpaActivity extends AppCompatActivity {
    private TextView tVpa;
    static int i=0,k=0,t=0;
    String phn="8888877777",bankName="DCB",vpa="534534@ybl",accno="31241441414",name="Ronit",
            id,branch="Jayanagar",ifsc="ubi00000127",vpaPin="1234",appPin="2345";
    Intent i1,i2;
    Button b1,b2;
    EditText username_edittext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vpa);
        i1=new Intent(this,MainActivity.class);
        i2=new Intent(this,ChoosePhoneActivity.class);
        b1=(Button)findViewById(R.id.button2);
        b2=(Button)findViewById(R.id.newVPA);
        username_edittext = (EditText)findViewById(R.id.username_edittext);
        tVpa = (TextView) findViewById(R.id.vpa_field);
        phn = SPHelper.getSP(getApplicationContext(),"phone"+t, phn);
        String p= phn;
        tVpa.setText(p+"@ybl");
        phn=p;
        vpa=p+"@ybl";

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = username_edittext.getText().toString();
                if(name1 == ""){
                    Toast.makeText(CreateVpaActivity.this, "Please enter your Name", Toast.LENGTH_SHORT).show();
                }
                else {
                    name = name1;
                    save();
                    startActivity(i1);
                }
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
        SPHelper.setSP(getApplicationContext(),"phone" + i, phn);
        SPHelper.setSP(getApplicationContext(),"bankName" + i, bankName);
        SPHelper.setSP(getApplicationContext(),"vpa" + i, vpa);
        SPHelper.setSP(getApplicationContext(),"accno" + i, accno);
        SPHelper.setSP(getApplicationContext(),"name" + i, name);
        SPHelper.setSP(getApplicationContext(),"branch" + i, branch);
        SPHelper.setSP(getApplicationContext(),"ifsc" + i, ifsc);
        SPHelper.setSP(getApplicationContext(),"vpapin" + i, vpaPin);
        SPHelper.setSP(getApplicationContext(),"apppin" + i, appPin);
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
        //++k;
    }
}
