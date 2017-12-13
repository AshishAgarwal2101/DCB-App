package com.cashless.easycash;

import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cashless.easycash.Helpers.SPHelper;

import java.util.ArrayList;

/**
 * Created by Varsha on 13-12-2017.
 */

public class BankAccount extends AppCompatActivity{
    Button deleteAccount;
    static int k=0,currentAccount=0;
    String phn="8888877777",bankName="DCB",vpa="534534@ybl",accno="31241441414",name="Ronit",
            id,branch="Jayanagar",ifsc="ubi00000127",vpaPin="1234",appPin="2345";
    ArrayList<String> accounts = new ArrayList<>();
    TextView mBankName, mUsername, mAccno, mIfsc, mBankBranch, mVpa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_account);

        loadAccountBeingUsed();
        k=currentAccount;

        k=0;
        load();
        while(true){
            if(vpa.equals("534534@ybl") || vpa.equals("none")){
                k=currentAccount;
                load();
                break;
            }
            else {
                accounts.add(accno);
                k++;
                load();
            }
        }

        deleteAccount = (Button)findViewById(R.id.delete_account_button);
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                DialogConfirmAccountDeletion dialog = DialogConfirmAccountDeletion.newInstance(currentAccount,accounts.size());
                dialog.show(manager, "DialogFragment1");
            }
        });

        mBankName = (TextView)findViewById(R.id.bank_name);
        mAccno = (TextView)findViewById(R.id.account_no);
        mBankBranch = (TextView)findViewById(R.id.bank_branch);
        mIfsc = (TextView)findViewById(R.id.bank_ifsc);
        mUsername = (TextView)findViewById(R.id.user_name);
        mVpa = (TextView)findViewById(R.id.bank_vpas);

        mBankName.setText(bankName);
        mAccno.setText(accno);
        mBankBranch.setText(branch);
        mIfsc.setText(ifsc);
        mUsername.setText(name);
        mVpa.setText(vpa);
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