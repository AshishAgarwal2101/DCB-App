package com.cashless.easycash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.cashless.easycash.Adapters.BankAdapter;
import com.cashless.easycash.Beans.Bank;
import com.cashless.easycash.Helpers.SPHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Ashish on 11/4/2017.
 */

public class SelectBankActivity extends AppCompatActivity{

    Intent i,intent;
    String sim;
    ArrayList<Bank> bankNames;
    public static Boolean selected = false;
    public static String selectedBank, selectedBankIFSC="DCB0987",selectedBankBranch="Lal Bangla";
    String phone;
    int currentAccount=0,k=0;
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

        b = (Button)findViewById(R.id.proceed_after_bank);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected) {
                    intent = new Intent(SelectBankActivity.this, CheckShowActivity.class);
                    save();//saving bank name
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Select a bank",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public void save()
    {
        k=getTotalAccounts();
        SPHelper.setSP(this,"bankName"+k,selectedBank);
        SPHelper.setSP(this,"ifsc"+k,selectedBankIFSC);
        SPHelper.setSP(this,"branch"+k,selectedBankBranch);//don't increment k or total accounts now
        Log.e("selectedbank+k",selectedBank+" "+k);
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
}
