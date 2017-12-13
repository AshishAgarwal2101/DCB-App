package com.cashless.easycash;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cashless.easycash.Beans.Bank;
import com.cashless.easycash.Helpers.DbUpdateHelper;
import com.cashless.easycash.Helpers.SPHelper;

import java.util.Locale;

public class CreateVpaActivity extends AppCompatActivity {
    private EditText tVpa;
    static int k=0,t=0,currentAccount=0;
   public static String phn="8888877777",bankName="DCB",vpa="534534@ybl",accno="31241441414",name="Ronit",
            id,branch="Jayanagar",ifsc="ubi00000127",vpaPin="1234",appPin="2345";
    Intent i1;
    Button b1;
    EditText username_edittext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vpa);

        //loadAccountBeingUsed();
        //k=currentAccount;
        //load();
        loadNewDetails();//all newly added values from choose phone till here retrieved
        /*phn= getIntent().getStringExtra("phone");
        bankName=getIntent().getStringExtra("bank");
        vpaPin= getIntent().getStringExtra("passcode");*/
        i1=new Intent(this,MainActivity.class);

        b1=(Button)findViewById(R.id.button2);
        username_edittext = (EditText)findViewById(R.id.username_edittext);
        tVpa = (EditText) findViewById(R.id.vpa_field);
        //phn = SPHelper.getSP(getApplicationContext(),"phone"+k, phn);
        String p= phn.substring(3);
        tVpa.setText(p+"@ybl");
        phn=p;
        vpa=p+"@ybl";

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = username_edittext.getText().toString();
                String num = tVpa.getText().toString();
                if(name1 == ""){
                    Toast.makeText(CreateVpaActivity.this, "Please enter your Name", Toast.LENGTH_SHORT).show();
                }
                else {
                    name = name1;
                    vpa=num;
                    addToDB();
                    //i1.putExtra("accno",new DbUpdateHelper().getAccno());
                    accno=new DbUpdateHelper().getAccno();
                    save();
                    startActivity(i1);
                }
            }
        });

    }

    void addToDB()
    {
        DbUpdateHelper db= new DbUpdateHelper();
        //Toast.makeText(this,bankName,Toast.LENGTH_SHORT).show();
        db.setValues(this,phn,bankName,vpa,name,branch,ifsc,vpaPin);
    }
    public void save() {
        k=getTotalAccounts();
       /* SPHelper.setSP(getApplicationContext(),"phone"+k, phn);
        SPHelper.setSP(getApplicationContext(),"bankName"+k, bankName);*/
        SPHelper.setSP(getApplicationContext(),"vpa"+k, vpa);
       SPHelper.setSP(getApplicationContext(),"accno"+k,accno);
        SPHelper.setSP(getApplicationContext(),"name"+k, name);
        /*SPHelper.setSP(getApplicationContext(),"branch"+k,branch);
        SPHelper.setSP(getApplicationContext(),"ifsc"+k,ifsc);
        SPHelper.setSP(getApplicationContext(),"vpapin"+k,vpaPin);
        SPHelper.setSP(getApplicationContext(),"apppin"+k,appPin);*/
        //EVERYTHING SAVED.. INCREMENT THE TOTAL NO. OF ACCOUNTS NOW
        setTotalAccounts(k+1);//k were there earlier
        Log.e("vpa+name+accno+k",vpa+" "+name+" "+accno+" "+getTotalAccounts());
    }

    public void load() {
        phn = SPHelper.getSP(getApplicationContext(),"phone"+k, "8888877777");
        bankName = SPHelper.getSP(getApplicationContext(),"bankName"+k,"DCB");
        vpa = SPHelper.getSP(getApplicationContext(),"vpa"+k, "534534@ybl");
        accno = SPHelper.getSP(getApplicationContext(),"accno"+k,"31241441414");
        name = SPHelper.getSP(getApplicationContext(),"name"+k, "Ronit");
        branch = SPHelper.getSP(getApplicationContext(),"branch"+k,"Jayanagar");
        ifsc = SPHelper.getSP(getApplicationContext(),"ifsc"+k, "ubi00000127+");
        vpaPin = SPHelper.getSP(getApplicationContext(),"vpapin"+k,"1234");
        appPin = SPHelper.getSP(getApplicationContext(),"apppin","2345");
        //++k;
    }
    public void loadNewDetails() {
        k=getTotalAccounts();//currently added account=k
        phn = SPHelper.getSP(getApplicationContext(),"phone"+k, "8888877777");
        bankName = SPHelper.getSP(getApplicationContext(),"bankName"+k,"DCB");
       // vpa = SPHelper.getSP(getApplicationContext(),"vpa"+k, "534534@ybl");
       // accno = SPHelper.getSP(getApplicationContext(),"accno"+k,"31241441414");
        //name = SPHelper.getSP(getApplicationContext(),"name"+k, "Ronit");
        branch = SPHelper.getSP(getApplicationContext(),"branch"+k,"Jayanagar");
        ifsc = SPHelper.getSP(getApplicationContext(),"ifsc"+k, "ubi00000127+");
        vpaPin = SPHelper.getSP(getApplicationContext(),"vpapin"+k,"1234");
        appPin = SPHelper.getSP(getApplicationContext(),"apppin","2345");
        //++k;
    }
    public void loadAccountBeingUsed(){
        currentAccount = SPHelper.getSP1(getApplicationContext(),"currentAccount",currentAccount);
    }
    public  void setTotalAccounts(int x)
    {
        SPHelper.setSP1(this,"totalaccounts",x);
    }
    public  int getTotalAccounts()
    {
        return SPHelper.getSP1(this,"totalaccounts",0);
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
        Intent refresh = new Intent(this, CreateVpaActivity.class);
        startActivity(refresh);
        finish();
    }
}
