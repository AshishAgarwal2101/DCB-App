package com.cashless.easycash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity{

    ImageView bSend, bReceive, bTransaction, bInvest, bFAQs, bAddNewNumber;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bSend = (ImageView) findViewById(R.id.icon_send);
        bReceive = (ImageView) findViewById(R.id.icon_receive);
        bTransaction = (ImageView) findViewById(R.id.icon_trans);
        bInvest = (ImageView) findViewById(R.id.icon_invest);
        bFAQs = (ImageView)findViewById(R.id.icon_faqs);
        bAddNewNumber=(ImageView)findViewById(R.id.icon_num);
        i= new Intent(this, ChoosePhoneActivity.class);
        i.putExtra("new","newNumber");

        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SendActivity.class));
            }
        });
        bReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ReceiveActivity.class));
            }
        });
        bTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Transactions.class));
            }
        });
        bInvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InvestActivity.class));
            }
        });
        bFAQs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FAQs.class));
            }
        });
        bAddNewNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });
    }
}
