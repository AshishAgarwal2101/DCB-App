package com.cashless.easycash.Helpers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.cashless.easycash.ChoosePhoneActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by jaisa on 12/11/2017.
 */

public class DbUpdateHelper {

    static private DatabaseReference myRef;
    public static String phone,bank,vpa,name,branch,ifsc,pin,accno,passcode;
    static boolean flag;
    static int k=0,currentAccount=0;
    static Context c;
    static ArrayList<String> accounts;
    public static void fetchValues(final String accno)
    {
        myRef =FirebaseDatabase.getInstance().getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                   phone = dataSnapshot.child("vpaValidation").child(accno).child("phone").getValue(String.class);
                    bank = dataSnapshot.child("vpaValidation").child(accno).child("bank").getValue(String.class);
                    vpa = dataSnapshot.child("vpaValidation").child(accno).child("vpa").getValue(String.class);
                    name = dataSnapshot.child("vpaValidation").child(accno).child("name").getValue(String.class);
                    branch = dataSnapshot.child("vpaValidation").child(accno).child("branch").getValue(String.class);
                    ifsc = dataSnapshot.child("vpaValidation").child(accno).child("ifsc").getValue(String.class);
                    pin = dataSnapshot.child("vpaValidation").child(accno).child("pin").getValue(String.class);
                    Log.e(accno,phone+" "+bank+" "+vpa+" "+name+" "+branch+" "+ifsc+" "+pin);
                } catch (Exception e) {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(accno,"cancelled");
            }
        });
        }

    public String getPhone()
    {
        return phone;
    }
    public  String getBank()
    {
        return bank;
    }
    public String getBranch()
    {
        return branch;
    }
    public static String getPin()
    {
        Log.e("pin",pin);
        return pin;
    }
    public  String getVPA()
    {
        return vpa;
    }
    public  String getIFSC()
    {
        return ifsc;
    }
    public String getName()
    {
        return name;
    }
    public String getAccno(){ return  accno;}
    public String getPasscode(){return  passcode;}


   public static void setValues(Context context, String phone, String bank, String vpa, String name, String branch, String ifsc, String pin)
    {
      /*  load();
        k=getTotalAccounts();// k is used for saving new value in shared pref*/
        accounts= new ArrayList<>();
        find();
        c=context;
        String acc_no;
        while(true) {
            acc_no = "" + (1000000000 + (long) (Math.random() * 999999999));
           if (findAcc(acc_no)) {
               Log.e("returned","yes");
               accno=acc_no;
               DbUpdateHelper.bank=bank;
               DbUpdateHelper.vpa=vpa;
               DbUpdateHelper.name=name;
               DbUpdateHelper.branch=branch;
               DbUpdateHelper.ifsc=ifsc;
               DbUpdateHelper.pin=pin;
                myRef = FirebaseDatabase.getInstance().getReference();
                myRef.child("vpaValidation").child(acc_no).child("phone").setValue(phone);
                myRef.child("vpaValidation").child(acc_no).child("bank").setValue(bank);
                myRef.child("vpaValidation").child(acc_no).child("vpa").setValue(vpa);
                myRef.child("vpaValidation").child(acc_no).child("name").setValue(name);
                myRef.child("vpaValidation").child(acc_no).child("branch").setValue(branch);
                myRef.child("vpaValidation").child(acc_no).child("ifsc").setValue(ifsc);
                myRef.child("vpaValidation").child(acc_no).child("pin").setValue(pin);
                Log.e("updated " + acc_no, phone + " " + bank + " " + vpa + " " + name + " " + branch + " " + ifsc + " " + pin);
              break;
            }
       }
       Log.e("outside if","fdghrt");
    }
    static boolean findAcc(String acc)
    {
        for(String st: accounts)
        {
            if(st.equals(acc))
                return false;
        }
        return  true;
    }
    static void find() {

        //Log.e("findddddddddddddddddddd", acc_no);
        myRef = FirebaseDatabase.getInstance().getReference();

        // Read from the database
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.child("vpaValidation").getChildren()) {
                    String id = postSnapshot.getKey();
                    accounts.add(id);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
            });

    }



    public static void load() {
        loadAccountBeingUsed();

        phone = SPHelper.getSP(c,"phone"+currentAccount, "8888877777");
        bank = SPHelper.getSP(c,"bankName"+currentAccount, "DCB");
        vpa = SPHelper.getSP(c,"vpa"+currentAccount, "534534@ybl");
        accno = SPHelper.getSP(c,"accno"+currentAccount,"31241441414");
        name = SPHelper.getSP(c,"name"+currentAccount, "Ronit");
        branch = SPHelper.getSP(c,"branch"+currentAccount,"Jayanagar");
        ifsc = SPHelper.getSP(c,"ifsc"+currentAccount, "ubi00000127+");
        pin = SPHelper.getSP(c,"vpapin"+currentAccount,"1234");
        passcode = SPHelper.getSP(c,"apppin","2345");

    }
    public static void loadAccountBeingUsed(){
        currentAccount = SPHelper.getSP1(c,"currentAccount",currentAccount);
    }
    public static void saveCurrentAccountBeingUsed(){
        SPHelper.setSP1(c,"currentAccount",currentAccount);
    }
    public static void setTotalAccounts(int x)
    {
        SPHelper.setSP1(c,"totalaccounts",x);
    }
    public static int getTotalAccounts()
    {
        return SPHelper.getSP1(c,"totalaccounts",0);
    }

}
