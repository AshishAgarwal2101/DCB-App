package com.cashless.easycash;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.cashless.easycash.Adapters.BankAdapter;
import com.cashless.easycash.Beans.Bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by Ashish on 11/4/2017.
 */

public class SelectBankActivity extends AppCompatActivity{

    Intent i,intent;
    String sim;
    ArrayList<Bank> bankNames;
    public static Boolean selected = false;
    public static String selectedBank;
    String phone;
    private RecyclerView mBankView;
    private RecyclerView.LayoutManager mBankLayoutManager;
    private RecyclerView.Adapter mBankAdapter;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_bank_layout);
        i= getIntent();
        phone=i.getStringExtra("phone");
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
                    intent.putExtra("phone",phone);
                    intent.putExtra("bank",selectedBank);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Select a bank",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_language, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.action_lang_en:
                setLocale("en");
                break;

            case R.id.action_lang_hi:
                setLocale("hi");
                Toast.makeText(this, "Language changed to: Hindi", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_lang_kn:
                setLocale("kn");
                Toast.makeText(this, "Language changed to: Kannada", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_lang_mr:
                setLocale("mr");
                Toast.makeText(this, "Language changed to: Marathi", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setLocale(String lang){
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(myLocale);
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, SelectBankActivity.class);
        startActivity(refresh);
        finish();
    }
}
