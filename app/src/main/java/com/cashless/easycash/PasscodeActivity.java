package com.cashless.easycash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cashless.easycash.Helpers.DbUpdateHelper;
import com.cashless.easycash.Helpers.SPHelper;

import static com.cashless.easycash.Helpers.DbUpdateHelper.fetchValues;
import static com.cashless.easycash.Helpers.DbUpdateHelper.getPin;

/**
 * Created by Ashish on 11/4/2017.
 */

public class PasscodeActivity extends AppCompatActivity {
    EditText e;
    Button b;
    String pass;
    Intent i;
    String reason,userid,accno,pin;
    int currentAccount=0,k=0;
    int amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.passcode_layout);
        b=(Button)findViewById(R.id.passButton);
        e=(EditText)findViewById(R.id.e1);

        i= new Intent(PasscodeActivity.this,SavedPasscode.class);
      /*  i.putExtra("phone",getIntent().getStringExtra("phone"));
        i.putExtra("bank",getIntent().getStringExtra("bank"));*/
        reason = getIntent().getExtras().getString("reason");
        userid = getIntent().getExtras().getString("id");
        amount = getIntent().getExtras().getInt("amount");
      //  accno = getIntent().getExtras().getString("accno");
        accno= loadAcc();
        if(!reason.equals("sending")){
            TextView tv = (TextView)findViewById(R.id.upi_background);
            tv.setVisibility(View.GONE);
        }
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass=e.getText().toString();
                if(!pass.equals("")) {
                    if (reason.equals("sending")) {
                        loadAccountBeingUsed();
                        loadPin();
                        //fetchValues(accno);
                        Log.e("pass",pass);
                        if(pass.equals(pin)) {
                            Intent intent = new Intent(PasscodeActivity.this, TransactionProcessingActivity.class);
                            intent.putExtra("id", userid);
                            intent.putExtra("amount", amount);
                            intent.putExtra("accno",accno);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Enter the correct VPA Pin!",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        save();
                        //i.putExtra("passcode",pass);
                        startActivity(i);
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Enter the Passcode!",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    boolean verify(String pass)
    {
     String pin=getPin();
        Log.e("VPA pin",pass+"  "+pin);
        if(pass.equals(pin))
        {
            return true;
        }

        else
            return false;
    }
    public void save()
    {
        k=getTotalAccounts();
        SPHelper.setSP(this,"vpapin"+k,pass);//don't increment k or total accounts now
        Log.e("vpaPin+k",pass+" "+k);
    }
    public String loadAcc()
    {
        return SPHelper.getSP(this,"accno"+currentAccount,"31241441414");
    }
    public  void loadAccountBeingUsed(){
        currentAccount = SPHelper.getSP1(this,"currentAccount",currentAccount);
    }
    public  void setTotalAccounts(int x)
    {
        SPHelper.setSP1(this,"totalaccounts",x);
    }
    public  int getTotalAccounts()
    {
        return SPHelper.getSP1(this,"totalaccounts",0);
    }

    public void loadPin() {

        pin = SPHelper.getSP(getApplicationContext(),"vpapin"+currentAccount,"1234");

    }
}
