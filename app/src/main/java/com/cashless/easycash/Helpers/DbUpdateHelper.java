package com.cashless.easycash.Helpers;

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

    private DatabaseReference myRef;
    public String phone,bank,vpa,name,branch,ifsc,pin;
    boolean flag;
    ArrayList<String> accounts;
    void fetchValues(final String accno)
    {
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

    String getPhone()
    {
        return phone;
    }
    String getBank()
    {
        return bank;
    }
    String getBranch()
    {
        return branch;
    }
    String getPin()
    {
        return pin;
    }
    String getVPA()
    {
        return vpa;
    }
    String getIFSC()
    {
        return ifsc;
    }
    String getName()
    {
        return name;
    }


   public void setValues(String phone, String bank, String vpa, String name, String branch, String ifsc, String pin)
    {
        accounts= new ArrayList<>();
        find();

        String acc_no;
        while(true) {
            acc_no = "" + 1000000000 + (long) (Math.random() * 999999999);
           if (findAcc(acc_no)) {
               Log.e("returned","yes");
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
    boolean findAcc(String acc)
    {
        for(String st: accounts)
        {
            if(st.equals(acc))
                return false;
        }
        return  true;
    }
    void find() {

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

}
