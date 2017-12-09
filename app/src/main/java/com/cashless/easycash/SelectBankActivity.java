package com.cashless.easycash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cashless.easycash.Adapters.BankAdapter;
import com.cashless.easycash.Beans.Bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Ashish on 11/4/2017.
 */

public class SelectBankActivity extends AppCompatActivity {

    Intent i,intent;
    String sim;
    ArrayList<Bank> bankNames;
    private RecyclerView mBankView;
    private RecyclerView.LayoutManager mBankLayoutManager;
    private RecyclerView.Adapter mBankAdapter;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_bank_layout);
        i= getIntent();
        if(i != null) {
            sim = i.getStringExtra("radio");
           // Toast.makeText(this,sim,Toast.LENGTH_SHORT).show();
        }

        bankNames = new ArrayList<>();
        bankNames.add(new Bank(R.drawable.dcb_logo, "DCB Bank", false));
        bankNames.add(new Bank(R.drawable.allahabad_logo, "Allahabad Bank", false));
        bankNames.add(new Bank(R.drawable.bob_logo, "Bank of Baroda", false));

        //To sort the ArrayList
        Collections.sort(bankNames, new Comparator<Bank>() {
            @Override
            public int compare(Bank lhs, Bank rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });

        mBankView = (RecyclerView) findViewById(R.id.bank_view);
        mBankView.setHasFixedSize(true);
        mBankLayoutManager = new LinearLayoutManager(this);
        mBankView.setLayoutManager(mBankLayoutManager);
        mBankAdapter = new BankAdapter(this, bankNames);
        mBankView.setAdapter(mBankAdapter);
        b= (Button)findViewById(R.id.proceed_after_bank);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(SelectBankActivity.this,CheckShowActivity.class);
                startActivity(intent);
            }
        });


    }
}
