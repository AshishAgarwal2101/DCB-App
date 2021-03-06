package com.cashless.easycash;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuInflater;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.cashless.easycash.Helpers.SPHelper;
import com.google.firebase.database.Transaction;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageView bSend, bReceive, bTransaction, bInvest, bFAQs, bAddNewNumber;
    Intent intent,i1,i2,i3,i4,i5,i6;
    static int k=0,currentAccount,maxNumberOfAccounts=4;
    String phn="8888877777",bankName="DCB",vpa="534534@ybl",accno="31241441414",name="Ronit",
            id,branch="Jayanagar",ifsc="ubi00000127",vpaPin="1234",appPin="2345";
    ArrayList<String> accounts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Easy Cash");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //load for k=currentAccount and check if values for it exists(account logged in). If doesn't exists, then go to sign up page
        loadAccountBeingUsed();

        //create a list of accounts
        k=0;
        load();
        while(true){
            if(vpa.equals("534534@ybl") || vpa.equals("none")){
                Log.i("hello", "Entered if");
                k=currentAccount;
                load();
                break;
            }
            else {
                Log.i("hello", "Entered else "+k+" Acc no.: "+accno+" vpa: "+vpa);
                accounts.add(accno);
                k++;
                load();
            }
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();
        int u=0;
        for(String acc:accounts){
            menu.add(0,u,0,acc);
            u++;
        }
        if(accounts.size() < maxNumberOfAccounts) {
            menu.add(0, 100, 0, "Add Account").setIcon(R.drawable.plus); //add account option with id=100
        }
        menu.getItem(currentAccount).setChecked(true);
        //Toast.makeText(this, "Using Account 1: xxxx xxxx xxxx 1414", Toast.LENGTH_SHORT).show();

        bSend = (ImageView) findViewById(R.id.icon_send);
        bReceive = (ImageView) findViewById(R.id.icon_receive);
        bTransaction = (ImageView) findViewById(R.id.icon_trans);
        bInvest = (ImageView) findViewById(R.id.icon_invest);
        bFAQs = (ImageView)findViewById(R.id.icon_faqs);
        bAddNewNumber=(ImageView)findViewById(R.id.icon_num);
        intent= new Intent(this, BankAccount.class);
        //intent.putExtra("accno",getIntent().getStringExtra("accno"));
        intent.putExtra("new","newNumber");


        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i1=new Intent(MainActivity.this, SendActivity.class);
               // i1.putExtra("accno",getIntent().getStringExtra("accno"));
                startActivity(i1);
            }
        });
        bReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i2=new Intent(MainActivity.this, ReceiveActivity.class);
               // i2.putExtra("accno",getIntent().getStringExtra("accno"));
                startActivity(i2);
            }
        });
        bTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i3=new Intent(MainActivity.this, Transactions.class);
                //i3.putExtra("accno",getIntent().getStringExtra("accno"));
                startActivity(i3);
            }
        });
        bInvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i4=new Intent(MainActivity.this, InvestActivity.class);
              //  i4.putExtra("accno",getIntent().getStringExtra("accno"));
                startActivity(i4);
            }
        });
        bFAQs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i5=new Intent(MainActivity.this, FAQs.class);
              //  i5.putExtra("accno",getIntent().getStringExtra("accno"));
                startActivity(i5);
            }
        });
        bAddNewNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BankAccount.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == 100){
            currentAccount=accounts.size();
            saveCurrentAccountBeingUsed();
            Intent intent = new Intent(MainActivity.this,ChoosePhoneActivity.class);
            startActivity(intent);
        }
        /*else if (id == R.id.nav_account_1) {
            Toast.makeText(this, "Using Account 1: xxxx xxxx xxxx 1414", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_account_2) {
            Toast.makeText(this, "Using Account 2: xxxx xxxx xxxx 2745", Toast.LENGTH_SHORT).show();
        }*/
        else {
            currentAccount=id;
            saveCurrentAccountBeingUsed();
            k=currentAccount;
            load();
            Toast.makeText(this, "Using Name: "+name+" Using VPA: "+vpa, Toast.LENGTH_SHORT).show();
        }
        int u=0;
        for(String acc:accounts){
            menu.getItem(u).setChecked(false);
            u++;
        }
        menu.getItem(currentAccount).setChecked(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void save() {
        SPHelper.setSP(getApplicationContext(),"phone"+k, phn);
        SPHelper.setSP(getApplicationContext(),"bankName"+k, bankName);
        SPHelper.setSP(getApplicationContext(),"vpa"+k, vpa);
        SPHelper.setSP(getApplicationContext(),"accno"+k,accno);
        SPHelper.setSP(getApplicationContext(),"name"+k, name);
        SPHelper.setSP(getApplicationContext(),"branch"+k,branch);
        SPHelper.setSP(getApplicationContext(),"ifsc"+k,ifsc);
        SPHelper.setSP(getApplicationContext(),"vpapin"+k,vpaPin);
        SPHelper.setSP(getApplicationContext(),"apppin",appPin);
        //++i;
    }

    public void load() {
        phn = SPHelper.getSP(getApplicationContext(),"phone"+k, "8888877777");
        bankName = SPHelper.getSP(getApplicationContext(),"bankName"+k, "DCB");
        vpa = SPHelper.getSP(getApplicationContext(),"vpa"+k, "534534@ybl");
        accno = SPHelper.getSP(getApplicationContext(),"accno"+k,"31241441414");
        name = SPHelper.getSP(getApplicationContext(),"name"+k, "Ronit");
        branch = SPHelper.getSP(getApplicationContext(),"branch"+k,"Jayanagar");
        ifsc = SPHelper.getSP(getApplicationContext(),"ifsc"+k, "ubi00000127+");
        vpaPin = SPHelper.getSP(getApplicationContext(),"vpapin"+k,"1234");
        appPin = SPHelper.getSP(getApplicationContext(),"apppin","2345");
        //++k;
    }
    public void loadAccountBeingUsed(){
        currentAccount = SPHelper.getSP1(getApplicationContext(),"currentAccount",currentAccount);
    }
    public void saveCurrentAccountBeingUsed(){
        SPHelper.setSP1(getApplicationContext(),"currentAccount",currentAccount);
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
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }
}
