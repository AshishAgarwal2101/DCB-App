package com.cashless.easycash;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cashless.easycash.Helpers.SPHelper;

import java.util.ArrayList;

/**
 * Created by Ashish on 12/13/2017.
 */

public class DialogConfirmAccountDeletion extends DialogFragment{
    Button deleteAccountYes,getDeleteAccountNo;
    int k=0,currentAccount,numberOfAccountsAvailable;
    String phn="8888877777",bankName="DCB",vpa="534534@ybl",accno="31241441414",name="Ronit",
            id,branch="Jayanagar",ifsc="ubi00000127",vpaPin="1234",appPin="2345";
    ArrayList<String> accounts = new ArrayList<>();

    public static DialogConfirmAccountDeletion newInstance(int currentAccount, int numberOfAccountsAvailable){
        Bundle args=new Bundle();
        args.putInt("currentAccount",currentAccount);
        args.putInt("numberOfAccountsAvailable", numberOfAccountsAvailable);
        DialogConfirmAccountDeletion fragment=new DialogConfirmAccountDeletion();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_confirm_account_deletion, null);

        currentAccount = (int)getArguments().getInt("currentAccount");
        numberOfAccountsAvailable = (int)getArguments().getInt("numberOfAccountsAvailable");

        deleteAccountYes = (Button)v.findViewById(R.id.delete_account_yes);
        getDeleteAccountNo = (Button)v.findViewById(R.id.delete_account_no);

        deleteAccountYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete the current account
                Toast.makeText(getActivity(),"Account Deleted Successfully", Toast.LENGTH_SHORT).show();

                int i;
                for(i=currentAccount; i<numberOfAccountsAvailable-1; i++){
                    k=i+1;
                    load();
                    k=i;
                    save();
                }
                k=numberOfAccountsAvailable-1;  //make last account values as null
                phn="8888877777";
                bankName="DCB";
                vpa="534534@ybl";
                accno="31241441414";
                name="Ronit";
                branch="Jayanagar";
                ifsc="ubi00000127";
                vpaPin="1234";
                appPin="2345";
                save();
                currentAccount=0;
                saveCurrentAccountBeingUsed();
                Intent intent = new Intent(getActivity(), ChoosePhoneActivity.class);
                startActivity(intent);
            }
        });
        getDeleteAccountNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return new AlertDialog.Builder(getActivity()).setView(v).create();
    }

    public void save() {
        SPHelper.setSP(getActivity().getApplicationContext(),"phone"+k, phn);
        SPHelper.setSP(getActivity().getApplicationContext(),"bankName"+k, bankName);
        SPHelper.setSP(getActivity().getApplicationContext(),"vpa"+k, vpa);
        SPHelper.setSP(getActivity().getApplicationContext(),"accno"+k,accno);
        SPHelper.setSP(getActivity().getApplicationContext(),"name"+k, name);
        SPHelper.setSP(getActivity().getApplicationContext(),"branch"+k,branch);
        SPHelper.setSP(getActivity().getApplicationContext(),"ifsc"+k,ifsc);
        SPHelper.setSP(getActivity().getApplicationContext(),"vpapin"+k,vpaPin);
        SPHelper.setSP(getActivity().getApplicationContext(),"apppin"+k,appPin);
        //++i;
    }

    public void load() {
        phn = SPHelper.getSP(getActivity().getApplicationContext(),"phone"+k, "8888877777");
        bankName = SPHelper.getSP(getActivity().getApplicationContext(),"bankName"+k, "DCB");
        vpa = SPHelper.getSP(getActivity().getApplicationContext(),"vpa"+k, "534534@ybl");
        accno = SPHelper.getSP(getActivity().getApplicationContext(),"accno"+k,"31241441414");
        name = SPHelper.getSP(getActivity().getApplicationContext(),"name"+k, "Ronit");
        branch = SPHelper.getSP(getActivity().getApplicationContext(),"branch"+k,"Jayanagar");
        ifsc = SPHelper.getSP(getActivity().getApplicationContext(),"ifsc"+k, "ubi00000127+");
        vpaPin = SPHelper.getSP(getActivity().getApplicationContext(),"vpapin"+k,"1234");
        appPin = SPHelper.getSP(getActivity().getApplicationContext(),"apppin"+k,"2345");
        //++k;
    }
    public void loadAccountBeingUsed(){
        currentAccount = SPHelper.getSP1(getActivity().getApplicationContext(),"currentAccount",currentAccount);
    }
    public void saveCurrentAccountBeingUsed(){
        SPHelper.setSP1(getActivity().getApplicationContext(),"currentAccount",currentAccount);
    }
}
