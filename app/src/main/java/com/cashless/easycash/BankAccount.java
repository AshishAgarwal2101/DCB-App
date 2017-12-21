package com.cashless.easycash;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cashless.easycash.Helpers.SPHelper;

import java.util.ArrayList;

/**
 * Created by Varsha on 13-12-2017.
 */

public class BankAccount extends AppCompatActivity{
    Button deleteAccount;
    static int k=0,currentAccount=0;
    String phn="8888877777",bankName="DCB",vpa="534534@ybl",accno="31241441414",name="Ronit",
            id,branch="Jayanagar",ifsc="ubi00000127",vpaPin="1234",appPin="2345", vpa2="Not defined!", myvpadata;
    ArrayList<String> accounts = new ArrayList<>();
    TextView mBankName, mUsername, mAccno, mIfsc, mBankBranch, mVpa1, mVpa2;

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
        mVpa1 = (TextView)findViewById(R.id.bank_vpa_1);
        mVpa2 = (TextView)findViewById(R.id.bank_vpa_2);

        mBankName.setText(bankName);
        mAccno.setText(accno);
        mBankBranch.setText(branch);
        mIfsc.setText(ifsc);
        mUsername.setText(name);
        mVpa1.setText(vpa);
        mVpa2.setText(vpa2);

        mVpa1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myvpadata = showChangeLangDialog();
                mVpa1.setText(myvpadata);
            }
        });

        mVpa2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myvpadata = showChangeLangDialog();
                mVpa2.setText(myvpadata);
            }
        });
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

    public String showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog_account, null);
        dialogBuilder.setView(dialogView);

        final EditText editText = (EditText) dialogView.findViewById(R.id.vpa_name);

        dialogBuilder.setTitle("Enter desired VPA");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with editText.getText().toString();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass

            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();

        return editText.getText().toString();

    }
}