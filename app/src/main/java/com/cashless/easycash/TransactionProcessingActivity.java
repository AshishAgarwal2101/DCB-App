package com.cashless.easycash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cashless.easycash.Helpers.SPHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class TransactionProcessingActivity extends AppCompatActivity {
    private DatabaseReference myRef;
    TextView transactionId, processingTextView;
    LinearLayout transactionDetails;
    Button transactionHomeButton;
    private ImageView imageView;
    String userid;
    int amount;
    int k=0,currentAccount,numberOfTransactions=0,amountInTransaction=0;
    String phn="8888877777",bankName="DCB",vpa="534534@ybl",accno="31241441414",name="Ronit",
            id,branch="Jayanagar",ifsc="ubi00000127",vpaPin="1234",appPin="2345";
    String date="none",with="none",transactionSentOrReceived="Sent",transactionId1="none";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_processing);

        loadAccountBeingUsed();
        loadNumberOfTransactions();
        k=currentAccount;
        load();

        int randomNum = 10000000 + (int)(Math.random() * 99999999);
        final String transactionIdRandom = "ABC"+randomNum;
        transactionId1 = transactionIdRandom;

        userid = getIntent().getExtras().getString("id");
        amount = getIntent().getExtras().getInt("amount");
        //Toast.makeText(TransactionProcessingActivity.this, userid+" "+amount, Toast.LENGTH_SHORT).show();

        imageView = (GifImageView) findViewById(R.id.show_check_gif);
        transactionId = (TextView)findViewById(R.id.transaction_id);
        processingTextView = (TextView)findViewById(R.id.processingTextView);
        transactionDetails = (LinearLayout)findViewById(R.id.transaction_details);
        transactionHomeButton = (Button)findViewById(R.id.transaction_home_button);

        GifDrawable gifDrawable = null;
        try {
            gifDrawable = new GifDrawable(getResources(), R.drawable.processing_payment);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageView.setImageDrawable(gifDrawable);

        String from = name+"("+accno.substring(0,4)+"XXXXXXXX"+accno.substring(accno.length()-4, accno.length())+")";
        myRef = FirebaseDatabase.getInstance().getReference();
        //myRef.child("sending").child("id").setValue(userid);
        myRef.child("sending").child(userid).child("amount").setValue(amount);
        myRef.child("sending").child(userid).child("transactionId").setValue(transactionId1);
        myRef.child("sending").child(userid).child("from").setValue(from);

        transactionId.setText(transactionId1);
        transactionDetails.setVisibility(View.GONE);
        transactionHomeButton.setVisibility(View.GONE);

        //transaction successful + reconcilation part
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    String status = dataSnapshot.child("sending").child(userid).child("status").getValue(String.class);
                    int amountReceived = dataSnapshot.child("sending").child(userid).child("amountReceived").getValue(Integer.class);

                    if(status.equals("success")){
                        amountInTransaction = dataSnapshot.child("transactions").child(transactionId1).child("amount").getValue(Integer.class);

                        //Reconcilation
                        if(amountInTransaction == amountReceived) {
                            date = dataSnapshot.child("transactions").child(transactionId1).child("date").getValue(String.class);
                            with = dataSnapshot.child("transactions").child(transactionId1).child("to").getValue(String.class);
                            transactionSentOrReceived = "Sent";
                            k=numberOfTransactions;
                            saveTransactionDetails();
                            k=currentAccount;
                            numberOfTransactions++;
                            saveNumberOfTransactions();

                            processingTextView.setVisibility(View.GONE);
                            transactionDetails.setVisibility(View.VISIBLE);
                            transactionHomeButton.setVisibility(View.VISIBLE);
                            GifDrawable gifDrawable = null;
                            try {
                                gifDrawable = new GifDrawable(getResources(), R.drawable.checkmark);
                                gifDrawable.setLoopCount(1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            imageView.setImageDrawable(gifDrawable);
                            myRef.removeEventListener(this);
                        }
                        else {
                            //false amount received
                        }
                    }
                } catch (Exception e) {
                    Log.e("TransacProcessActivity", e.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("hello", "Failed to read value.", databaseError.toException());
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!(transactionDetails.getVisibility() == View.VISIBLE)){
                    processingTextView.setText("Transaction Failed");
                    transactionHomeButton.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.GONE);
                }

            }
        }, 10000);

        transactionHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("sending").removeValue();
                Intent intent = new Intent(TransactionProcessingActivity.this, MainActivity.class);
                startActivity(intent);
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
    public void loadNumberOfTransactions(){
        numberOfTransactions = SPHelper.getSP1(getApplicationContext(), currentAccount+"numberOfTransactions", numberOfTransactions);
    }
    public void saveNumberOfTransactions(){
        SPHelper.setSP1(getApplicationContext(), currentAccount+"numberOfTransactions", numberOfTransactions);
    }
    public void saveTransactionDetails(){
        SPHelper.setSP(getApplicationContext(),currentAccount+"transactionId"+k, transactionId1);
        SPHelper.setSP(getApplicationContext(),currentAccount+"date"+k, date);
        SPHelper.setSP(getApplicationContext(), currentAccount+"to"+k, with);
        SPHelper.setSP(getApplicationContext(), currentAccount+"transactionSentOrReceived"+k, transactionSentOrReceived);
        SPHelper.setSP1(getApplicationContext(), currentAccount+"amount"+k, amountInTransaction);
    }
    public void loadTransactionDetails(){
        transactionId1 = SPHelper.getSP(getApplicationContext(),currentAccount+"transactionId"+k, transactionId1);
        date = SPHelper.getSP(getApplicationContext(),currentAccount+"date"+k, date);
        with = SPHelper.getSP(getApplicationContext(), currentAccount+"to"+k, with);
        transactionSentOrReceived = SPHelper.getSP(getApplicationContext(), currentAccount+"transactionSentOrReceived"+k, transactionSentOrReceived);
        amountInTransaction = SPHelper.getSP1(getApplicationContext(), currentAccount+"amount"+k, amountInTransaction);
    }

    @Override
    protected void onPause() {
        super.onPause();
        myRef.child("sending").removeValue();
    }
}
