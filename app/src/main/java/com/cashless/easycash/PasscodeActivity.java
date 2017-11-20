package com.cashless.easycash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Ashish on 11/4/2017.
 */

public class PasscodeActivity extends AppCompatActivity {
    EditText e;
    Button b;
    String pass;
    Intent i;
    String reason,userid;
    int amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.passcode_layout);
        b=(Button)findViewById(R.id.passButton);
        e=(EditText)findViewById(R.id.e1);
        pass=e.getText().toString();
        i= new Intent(PasscodeActivity.this,SavedPasscode.class);
        reason = getIntent().getExtras().getString("reason");
        userid = getIntent().getExtras().getString("id");
        amount = getIntent().getExtras().getInt("amount");
        if(!reason.equals("sending")){
            TextView tv = (TextView)findViewById(R.id.upi_background);
            tv.setVisibility(View.GONE);
        }
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reason.equals("sending")){
                    Intent intent = new Intent(PasscodeActivity.this, TransactionProcessingActivity.class);
                    intent.putExtra("id", userid);
                    intent.putExtra("amount", amount);
                    startActivity(intent);
                }
                else {
                    startActivity(i);
                }
            }
        });


    }

}
