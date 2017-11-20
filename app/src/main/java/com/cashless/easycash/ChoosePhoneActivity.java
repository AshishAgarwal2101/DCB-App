
package com.cashless.easycash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ashish on 11/4/2017.
 */

public class ChoosePhoneActivity  extends AppCompatActivity{

    TextView t1,t2;
    RadioButton r1;
    RadioGroup g;
    String s1="Jio 4G";
    Button b;
    Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_phone_layout);
        t1= (TextView) findViewById(R.id.HelloText);
        t2= (TextView) findViewById(R.id.chooseSimText);
        g= (RadioGroup) findViewById(R.id.radioGroup);
        b= (Button) findViewById(R.id.button);
        i= new Intent(ChoosePhoneActivity.this, PhoneVerification.class);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id= g.getCheckedRadioButtonId();
                if(id==-1)
                {
                    Toast.makeText(ChoosePhoneActivity.this,"Select one sim",Toast.LENGTH_SHORT).show();
                }else {
                    r1 = (RadioButton) findViewById(id);
                    i.putExtra("radio", r1.getText());
                    startActivity(i);
                }
            }
        });





    }
}
