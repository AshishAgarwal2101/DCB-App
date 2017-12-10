package com.cashless.easycash;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageView bSend, bReceive, bTransaction, bInvest, bFAQs, bAddNewNumber;
    Intent intent;
    static int i=0,k=0,currentAccount;
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
        menu.add(0,100,0,"Add Account").setIcon(R.drawable.plus); //add account option with id=100
        menu.getItem(currentAccount).setChecked(true);
        //Toast.makeText(this, "Using Account 1: xxxx xxxx xxxx 1414", Toast.LENGTH_SHORT).show();

        bSend = (ImageView) findViewById(R.id.icon_send);
        bReceive = (ImageView) findViewById(R.id.icon_receive);
        bTransaction = (ImageView) findViewById(R.id.icon_trans);
        bInvest = (ImageView) findViewById(R.id.icon_invest);
        bFAQs = (ImageView)findViewById(R.id.icon_faqs);
        bAddNewNumber=(ImageView)findViewById(R.id.icon_num);
        intent= new Intent(this, ChoosePhoneActivity.class);
        intent.putExtra("new","newNumber");


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
                startActivity(intent);
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
        SPHelper.setSP(getApplicationContext(),"apppin"+k,appPin);
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
        appPin = SPHelper.getSP(getApplicationContext(),"apppin"+k,"2345");
        //++k;
    }
    public void loadAccountBeingUsed(){
        currentAccount = SPHelper.getSP1(getApplicationContext(),"currentAccount",currentAccount);
    }
    public void saveCurrentAccountBeingUsed(){
        SPHelper.setSP1(getApplicationContext(),"currentAccount",currentAccount);
    }
}
